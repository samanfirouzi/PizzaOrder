package nakisa.saman.pizzaorder.controller.order;

import nakisa.saman.pizzaorder.exceprions.EmptyResultException;
import nakisa.saman.pizzaorder.model.OrderModel;
import nakisa.saman.pizzaorder.model.UserOrderDataModel;
import nakisa.saman.pizzaorder.model.entry.OrderItem;
import nakisa.saman.pizzaorder.model.entry.UserOrder;
import nakisa.saman.pizzaorder.security.UserHelper;
import nakisa.saman.pizzaorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @PostMapping("addItem/{destination}")
    public UserOrderDataModel addOrderItem(@PathVariable String destination, @RequestBody OrderItem orderItem, @AuthenticationPrincipal UserHelper user) {
        UserOrderDataModel order = null;
        if (destination.equals("Cart"))
            order = orderService.addOrderItemToActiveOrder(orderItem, user.getUserId());
        else if (destination.equals("Inactive")) {
            orderService.addOrderItemToSavedOrder(orderItem, user.getUserId());
            order = orderService.getOrderDetailByUserId(user.getUserId());
        }
        return order;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @PostMapping("removeItem")
    public UserOrderDataModel removeOrderItem(@RequestBody OrderItem orderItem, @AuthenticationPrincipal UserHelper user) {
        UserOrderDataModel order = orderService.removeOrderItem(orderItem, user.getUserId());
        return order;
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("/{orderId}")
    public UserOrder getOrderDetailById(@PathVariable Long orderId) {
        UserOrder order = orderService.getOrderDetailById(orderId);
        return order;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.delete(orderId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("lunch/{orderId}")
    public void lunchOrder(@PathVariable Long orderId) {
        orderService.lunchOrder(orderId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("saveForLater")
    public UserOrderDataModel saveOrder(@AuthenticationPrincipal UserHelper user) {
        return orderService.saveForLaterOrder(user.getUserId());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("active")
    public OrderModel getActiveOrderDetailByUserId(@AuthenticationPrincipal UserHelper user) {
        OrderModel order = orderService.getActiveOrderDetailByUserId(user.getUserId());
        return order;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("savedToActive")
    public UserOrderDataModel savedToActive(@AuthenticationPrincipal UserHelper user) {
        UserOrderDataModel order = orderService.savedToActive(user.getUserId());
        return order;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("orderData")
    public UserOrderDataModel getOrderDetailByUserId(@AuthenticationPrincipal UserHelper user) {
        UserOrderDataModel order = orderService.getOrderDetailByUserId(user.getUserId());
        return order;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("addFavorite")
    public UserOrderDataModel moveOrderToFavorite(@AuthenticationPrincipal UserHelper user) {
        UserOrderDataModel order = orderService.moveToFavorite(user.getUserId());
        return order;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("deleteFavorite")
    public UserOrderDataModel deleteFavorite(@AuthenticationPrincipal UserHelper user, @RequestParam Long favoriteId) {
        orderService.delete(favoriteId);
        UserOrderDataModel order = orderService.getOrderDetailByUserId(user.getUserId());
        return order;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("activeFavorite")
    public UserOrderDataModel activeFavorite(@AuthenticationPrincipal UserHelper user, @RequestParam Long favoriteId) {
        UserOrderDataModel order = orderService.replaceActiveOrderByFavorite(user.getUserId(), favoriteId);
        return order;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("activeHistory")
    public UserOrderDataModel activeHistory(@AuthenticationPrincipal UserHelper user, @RequestParam Long historyId) {
        UserOrderDataModel order = orderService.replaceActiveOrderByHistory(user.getUserId(), historyId);
        return order;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("search")
    public Page<UserOrder> searchOrder(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                       @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                       @RequestParam(required = false) Long orderId, @RequestParam(required = false) Integer status) {
        Page<UserOrder> order = orderService.findByTypeAndStatus(pageNo, pageSize, orderId, status);
        return order;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("payCart")
    public UserOrderDataModel payCart(@AuthenticationPrincipal UserHelper user) throws EmptyResultException {
        UserOrderDataModel userOrders = orderService.payCart(user.getUserId());
        return userOrders;
    }

}
