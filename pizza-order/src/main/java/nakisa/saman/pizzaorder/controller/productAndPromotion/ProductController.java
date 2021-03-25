package nakisa.saman.pizzaorder.controller.productAndPromotion;

import nakisa.saman.pizzaorder.exceprions.EmptyResultException;
import nakisa.saman.pizzaorder.model.entry.BaseProduct;
import nakisa.saman.pizzaorder.repository.product.BaseProductRepositoryCRUD;
import nakisa.saman.pizzaorder.repository.product.ProductRepositoryCRUD;
import nakisa.saman.pizzaorder.service.BaseProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;


@RestController()
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductRepositoryCRUD productRepositoryCRUD;
    @Autowired
    BaseProductRepositoryCRUD baseProductRepositoryCRUD;

    @Autowired
    BaseProductService baseProductService;

    @PermitAll
    @GetMapping("all")
    public Iterable<BaseProduct> all() {
        Iterable<BaseProduct> all = baseProductRepositoryCRUD.findAll();
        return all;
    }

    @PermitAll
    @GetMapping("search")
    public Page<BaseProduct> search(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                    @RequestParam(required = false) Long id,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer type) throws EmptyResultException {
        Page<BaseProduct> all = baseProductService.findByNameLikeOrIdOrType(pageNo, pageSize, id, name, type);
        if (all.isEmpty())
            throw new EmptyResultException("product");
        return all;
    }

    @PermitAll
    @GetMapping("type/{type}")
    public Iterable<BaseProduct> allByType(@PathVariable Integer type) {
        Iterable<BaseProduct> all = baseProductService.getByType(type);
        return all;
    }

    @PermitAll
    @GetMapping("detail/{id}")
    public BaseProduct getById(@PathVariable Long id) {
        BaseProduct all = baseProductRepositoryCRUD.findById(id).get();
        return all;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add")
    public Long addProduct(@RequestBody BaseProduct baseProduct) {
        Long id = baseProductRepositoryCRUD.save(baseProduct).getId();
        return id;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    public Long updateProduct(@RequestBody BaseProduct baseProduct) {
        return baseProductRepositoryCRUD.save(baseProduct).getId();
    }

}
