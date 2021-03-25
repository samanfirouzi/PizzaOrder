package nakisa.saman.pizzaorder.service;

import nakisa.saman.pizzaorder.constants.OrderEnum;
import nakisa.saman.pizzaorder.constants.OrderItemEnum;
import nakisa.saman.pizzaorder.exceprions.EmptyResultException;
import nakisa.saman.pizzaorder.model.OrderItemModel;
import nakisa.saman.pizzaorder.model.OrderModel;
import nakisa.saman.pizzaorder.model.UserOrderDataModel;
import nakisa.saman.pizzaorder.model.entry.*;
import nakisa.saman.pizzaorder.repository.order.OrderItemJpaRepository;
import nakisa.saman.pizzaorder.repository.order.OrderJpaRepository;
import nakisa.saman.pizzaorder.repository.product.BaseProductJpaRepository;
import nakisa.saman.pizzaorder.repository.product.ProductRepositoryCRUD;
import nakisa.saman.pizzaorder.repository.promotion.PromotionRepositoryCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderService {

    @Autowired
    BaseProductJpaRepository baseProductJpaRepository;
    @Autowired
    ProductRepositoryCRUD productRepositoryCRUD;
    @Autowired
    PromotionRepositoryCRUD promotionRepositoryCRUD;
    @Autowired
    OrderJpaRepository orderJpaRepository;
    @Autowired
    OrderItemJpaRepository orderItemJpaRepository;

    public UserOrderDataModel addOrderItemToActiveOrder(OrderItem orderItem, Long userId) {
        UserOrder order = addOrGetOrder(userId, OrderEnum.ACTIVE_ORDER);
        addOrderItem(orderItem, order);
        UserOrderDataModel userOrderDataModel = getOrderDetailByUserId(userId);
        return userOrderDataModel;
    }

    public void addOrderItemToSavedOrder(OrderItem orderItem, Long userId) {
        UserOrder order = addOrGetOrder(userId, OrderEnum.SAVED_FOR_LATER);
        addOrderItem(orderItem, order);
    }

    public void addOrderItem(OrderItem orderItem, UserOrder order) {
        orderItem.setOrderId(order.getId());
        AtomicBoolean itsUpdateItem = new AtomicBoolean(false);

        order.getOrderItems().forEach(item -> {
            //I should change it to breakable loop
            boolean isSame = false;
            if (orderItem.getType().equals(OrderItemEnum.PRODUCT.getValue()) && item.getType().equals(OrderItemEnum.PRODUCT.getValue())
                    && item.getProductId().equals(orderItem.getProductId()))
                isSame = true;
            else if (orderItem.getType().equals(OrderItemEnum.PROMOTION.getValue()) && item.getType().equals(OrderItemEnum.PROMOTION.getValue())
                    && item.getPromotionId().equals(orderItem.getPromotionId()))
                isSame = true;

            if (isSame) {
                item.setQuantity(1 + item.getQuantity());

                orderItemJpaRepository.save(item);
                itsUpdateItem.set(true);


            }
        });

        if (!itsUpdateItem.get()) {
            OrderItem save = orderItemJpaRepository.save(orderItem);
            order.getOrderItems().add(save);
        }

    }

    public UserOrderDataModel removeOrderItem(OrderItem orderItem, Long userId) {

        UserOrder order = orderJpaRepository.findById(orderItem.getOrderId()).get();
        if (order.getUserId().equals(userId)) {
            OrderItem orderItemDB = orderItemJpaRepository.findById(orderItem.getId()).get();

            if (orderItemDB.getQuantity() < 2)
                orderItemJpaRepository.deleteById(orderItemDB.getId());
            else {
                orderItemDB.setQuantity(orderItem.getQuantity() - 1);

                orderItemJpaRepository.save(orderItemDB);
            }
        }

        order = orderJpaRepository.findById(orderItem.getOrderId()).get();

        if (order.getOrderItems().size() == 0) {
            delete(order.getId());
        }
        return getOrderDetailByUserId(userId);
    }

    private UserOrder addOrGetOrder(Long userId, OrderEnum orderEnum) {
        UserOrder order = new UserOrder();
        order.setUserId(userId);
        order.setState(orderEnum.getValue());
        Example<UserOrder> example = Example.of(order);
        Optional<UserOrder> userOrder = orderJpaRepository.findOne(example);
        if (userOrder.isEmpty()) {
            order.setDate(new Date().toString());
            return orderJpaRepository.save(order);
        } else return userOrder.get();
    }

    public UserOrderDataModel getOrderDetailByUserId(Long userId) {
        UserOrderDataModel userOrderDataModel = new UserOrderDataModel();
        userOrderDataModel.setCurrent(getActiveOrderDetailByUserId(userId));
        userOrderDataModel.setSaved(getSavedOrderDetailByUserId(userId));
        userOrderDataModel.setFavorite(getAllFavoriteOrderDetailByUserId(userId));
        userOrderDataModel.setHistory(getAllHistoryOrderDetailByUserId(userId));
        return userOrderDataModel;
    }

    public UserOrderDataModel moveToFavorite(Long userId) {
        Optional<UserOrder> userOrderOptional = getUserOrderByType(userId, OrderEnum.ACTIVE_ORDER);
        if (userOrderOptional.isEmpty())
            return null;

        UserOrder userOrder = userOrderOptional.get();
        userOrder.setState(OrderEnum.FAVORITE.getValue());

        orderJpaRepository.save(userOrder);

        return getOrderDetailByUserId(userId);
    }

    private Optional<UserOrder> getUserOrderByType(Long userId, OrderEnum activeOrder) {
        return orderJpaRepository.findOne(getUserOrderExample(userId, activeOrder));
    }

    private List<UserOrder> getAllUserOrderType(Long userId, OrderEnum activeOrder) {
        return orderJpaRepository.findAll(getUserOrderExample(userId, activeOrder));
    }

    private Example<UserOrder> getUserOrderExample(Long userId, OrderEnum activeOrder) {
        UserOrder order = new UserOrder();
        order.setUserId(userId);
        order.setState(activeOrder.getValue());
        return Example.of(order);
    }

    public OrderModel getActiveOrderDetailByUserId(Long userId) {
        Optional<UserOrder> userOrder = getUserOrderByType(userId, OrderEnum.ACTIVE_ORDER);
        if (userOrder.isEmpty())
            return null;

        return getOrderModel(userOrder.get());
    }

    public OrderModel getSavedOrderDetailByUserId(Long userId) {
        Optional<UserOrder> userOrder = getUserOrderByType(userId, OrderEnum.SAVED_FOR_LATER);
        if (userOrder.isEmpty())
            return null;

        return getOrderModel(userOrder.get());
    }

    public List<OrderModel> getAllFavoriteOrderDetailByUserId(Long userId) {
        List<UserOrder> userOrder = getAllUserOrderType(userId, OrderEnum.FAVORITE);
        if (userOrder.isEmpty())
            return null;

        return getOrderModel(userOrder);
    }
    public List<OrderModel> getAllHistoryOrderDetailByUserId(Long userId) {
        List<UserOrder> userOrder = getAllUserOrderType(userId, OrderEnum.LUNCHED);
        if (userOrder.isEmpty())
            return null;

        return getOrderModel(userOrder);
    }

    private List<OrderModel> getOrderModel(List<UserOrder> userOrders) {
        List<OrderModel> orderModels = new ArrayList<>();
        for (UserOrder order : userOrders) {
            orderModels.add(getOrderModel(order));
        }
        return orderModels;
    }

    private OrderModel getOrderModel(UserOrder userOrder) {
        OrderModel orderModel = new OrderModel();
        orderModel.setUserOrder(userOrder);
        orderModel.setState(userOrder.getState());
        orderModel.setId(userOrder.getId());
        orderModel.setDate(userOrder.getDate());
        orderModel.setItemlist(new ArrayList<>());
        for (OrderItem orderItem : userOrder.getOrderItems()) {
            OrderItemModel oItemModel = new OrderItemModel();
            oItemModel.setOrderItem(orderItem);
//            oItemModel.setId(orderItem.getId());
//            oItemModel.setQuantity(orderItem.getQuantity());

            if (orderItem.getType().equals(OrderItemEnum.PRODUCT.getValue())) {
                Optional<Product> productOptional = productRepositoryCRUD.findById(orderItem.getProductId());
                if (productOptional.isPresent()) {
                    Optional<BaseProduct> baseProductOptional = baseProductJpaRepository.findById(productOptional.get().getBaseProductId());
                    if (baseProductOptional.isPresent()) {
                        oItemModel.setName(baseProductOptional.get().getName());
                        oItemModel.setImage(baseProductOptional.get().getImage());
                    }
                    oItemModel.setSize(productOptional.get().getSize());
                    oItemModel.setPrice(productOptional.get().getPrice());
                }
            } else if (orderItem.getType().equals(OrderItemEnum.PROMOTION.getValue())) {
                Optional<Promotion> promotionOptional = promotionRepositoryCRUD.findById(orderItem.getPromotionId());
                if (promotionOptional.isPresent()) {
                    oItemModel.setName(promotionOptional.get().getName());
                    oItemModel.setImage(promotionOptional.get().getImage());

                    int sum = 0;
                    for (PromotionItem promotionItem :
                            promotionOptional.get().getPromotionItems()) {
                        sum += promotionItem.getProduct().getPrice() * promotionItem.getQuantity();
                    }
                    oItemModel.setPrice(sum * (100 - promotionOptional.get().getDiscount()) / 100);
                }

            }

            orderModel.getItemlist().add(oItemModel);
        }
        return orderModel;
    }

    public UserOrder getOrderDetailById(Long orderId) {
        return orderJpaRepository.findById(orderId).get();
    }

    public void delete(Long orderId) {
        orderJpaRepository.deleteById(orderId);
    }

    public void lunchOrder(Long orderId) {
        UserOrder order = getOrderDetailById(orderId);
        order.setState(OrderEnum.LUNCHED.getValue());
        orderJpaRepository.save(order);
    }

    public UserOrderDataModel saveForLaterOrder(Long userId) {
        UserOrderDataModel orderDataModel = getOrderDetailByUserId(userId);
        if (orderDataModel.getSaved() == null) {
            orderDataModel.getCurrent().getUserOrder().setState(OrderEnum.SAVED_FOR_LATER.getValue());
            orderJpaRepository.save(orderDataModel.getCurrent().getUserOrder());
        } else if (orderDataModel.getCurrent() != null && orderDataModel.getCurrent().getItemlist() != null) {
            delete(orderDataModel.getCurrent().getUserOrder().getId());

            for (OrderItemModel orderItemModel :
                    orderDataModel.getCurrent().getItemlist()) {
                addOrderItemToSavedOrder(orderItemModel.getOrderItem(), userId);
            }
        }
        return getOrderDetailByUserId(userId);
    }

    public UserOrderDataModel savedToActive(Long userId) {
        UserOrderDataModel orderDataModel = getOrderDetailByUserId(userId);
        if (orderDataModel.getCurrent() == null && orderDataModel.getSaved() != null) {
            orderDataModel.getSaved().getUserOrder().setState(OrderEnum.ACTIVE_ORDER.getValue());
            orderJpaRepository.save(orderDataModel.getSaved().getUserOrder());
        } else if (orderDataModel.getSaved() != null && orderDataModel.getSaved().getItemlist() != null) {
            delete(orderDataModel.getSaved().getUserOrder().getId());

            for (OrderItemModel orderItemModel :
                    orderDataModel.getSaved().getItemlist()) {
                addOrderItemToActiveOrder(orderItemModel.getOrderItem(), userId);
            }
        }
        return getOrderDetailByUserId(userId);
    }

    public UserOrderDataModel replaceActiveOrderByHistory(Long userId, Long historyId) {
//        deleteActiveOrder(userId);
        return replaceOrderToCart(userId, historyId);
    }

    private UserOrderDataModel replaceOrderToCart(Long userId, Long orderId) {
        OrderModel activeOrderDetailByUserId = getActiveOrderDetailByUserId(userId);
        if (activeOrderDetailByUserId != null)
            delete(activeOrderDetailByUserId.getId());

        UserOrder favorite = getOrderDetailById(orderId);

        UserOrder newOrder = new UserOrder();
        newOrder.setState(OrderEnum.ACTIVE_ORDER.getValue());
        newOrder.setDate(new Date().toString());
        newOrder.setUserId(userId);


        UserOrder save = orderJpaRepository.save(newOrder);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItem orderItem :
                favorite.getOrderItems()) {
            OrderItem newItem = new OrderItem();
            newItem.setOrderId(save.getId());
            newItem.setQuantity(orderItem.getQuantity());
            newItem.setType(orderItem.getType());
            newItem.setProductId(orderItem.getProductId());
            newItem.setPromotionId(orderItem.getPromotionId());

            orderItems.add(newItem);
        }
        save.setOrderItems(orderItems);
        orderJpaRepository.save(save);

        return getOrderDetailByUserId(userId);
    }

    public UserOrderDataModel replaceActiveOrderByFavorite(Long userId, Long favoriteId) {
//        deleteActiveOrder(userId);
        return replaceOrderToCart(userId, favoriteId);
    }

    private void deleteActiveOrder(Long userId) {
        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(userId);
        userOrder.setState(OrderEnum.ACTIVE_ORDER.getValue());
        orderJpaRepository.delete(userOrder);
    }

    public UserOrderDataModel payCart(Long userId) throws EmptyResultException {
        Optional<UserOrder> userOrderByType = getUserOrderByType(userId, OrderEnum.ACTIVE_ORDER);
        if (userOrderByType.isEmpty()) throw new EmptyResultException("cart item");

        userOrderByType.get().setState(OrderEnum.LUNCHED.getValue());

        orderJpaRepository.save(userOrderByType.get());
        return getOrderDetailByUserId(userId);
    }

    public Page<UserOrder> findByTypeAndStatus(Integer pageNo, Integer pageSize, Long orderId, Integer status) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        UserOrder userOrder = new UserOrder();
        Page<UserOrder> pagedResult = null;

        if (hasValue(orderId)) {
            List<UserOrder> userOrders = new ArrayList<>();
            Optional<UserOrder> byId = orderJpaRepository.findById(orderId);
            if (byId.isPresent()) {
                userOrders.add(byId.get());
                pagedResult = new PageImpl<UserOrder>(List.of(byId.get()));
            }

        } else if (hasValue(status)) {
            UserOrder exam = new UserOrder();
            exam.setState(status);
            pagedResult = orderJpaRepository.findAll(Example.of(exam), paging);
        } else
            pagedResult = orderJpaRepository.findAll(paging);
        if (pagedResult != null && pagedResult.hasContent()) {
            return pagedResult;
        } else {
            return new PageImpl<UserOrder>(new ArrayList<>());
        }
    }

    private boolean hasValue(Long n) {
        return n != null;
    }

    private boolean hasValue(String s) {
        return s != null && !s.equals("") && !s.equals("null");
    }

    private boolean hasValue(Integer n) {
        return n != null;
    }

}
