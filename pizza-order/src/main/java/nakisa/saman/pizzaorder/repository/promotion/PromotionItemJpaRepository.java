package nakisa.saman.pizzaorder.repository.promotion;

import nakisa.saman.pizzaorder.model.entry.Promotion;
import nakisa.saman.pizzaorder.model.entry.PromotionItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionItemJpaRepository extends JpaRepository<PromotionItem,Long> {
}
