package nakisa.saman.pizzaorder.controller.productAndPromotion;

import nakisa.saman.pizzaorder.exceprions.EmptyResultException;
import nakisa.saman.pizzaorder.model.entry.Promotion;
import nakisa.saman.pizzaorder.model.entry.PromotionItem;
import nakisa.saman.pizzaorder.repository.promotion.PromotionItemJpaRepository;
import nakisa.saman.pizzaorder.repository.promotion.PromotionRepositoryCRUD;
import nakisa.saman.pizzaorder.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;


@RestController()
@RequestMapping("promotion")
public class PromotionController {

    @Autowired
    PromotionRepositoryCRUD promotionRepositoryCRUD;
    @Autowired
    PromotionItemJpaRepository itemJpaRepository;
    @Autowired
    PromotionService promotionService;

    @PermitAll
    @GetMapping("all")
    public Iterable<Promotion> all() {
        Iterable<Promotion> all = promotionRepositoryCRUD.findAll();
        return all;
    }

    @PermitAll
    @GetMapping("search")
    public Page<Promotion> search(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                  @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                  @RequestParam(required = false) Long id,
                                  @RequestParam(required = false) String name) throws EmptyResultException {
        Page<Promotion> all = promotionService.findByNameLikeOrId(pageNo, pageSize, id, name);
        if(all.isEmpty())
            throw new EmptyResultException("promotion");
        return all;
    }

    @PermitAll
    @GetMapping("detail/{id}")
    public Promotion getById(@PathVariable Long id) {
        Promotion all = promotionRepositoryCRUD.findById(id).get();
        return all;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add")
    public Long addPromotion(@RequestBody Promotion promotion) {
        List<PromotionItem> promotionItems = promotion.getPromotionItems();
        promotion.setPromotionItems(new ArrayList<>());
        Long id = promotionRepositoryCRUD.save(promotion).getId();
        for (PromotionItem item :
                promotionItems) {
            item.setPromotionId(id);
            itemJpaRepository.save(item);

        }
        return id;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    public Long updateProduct(@RequestBody Promotion promotion) {
        PromotionItem pTemp = new PromotionItem();
        pTemp.setPromotionId(promotion.getId());
        itemJpaRepository.delete(pTemp);
        return promotionRepositoryCRUD.save(promotion).getId();
    }

}
