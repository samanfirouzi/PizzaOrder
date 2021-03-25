package nakisa.saman.pizzaorder.repository.promotion;

import nakisa.saman.pizzaorder.model.entry.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PromotionJpaRepository extends JpaRepository<Promotion,Long> {
    Page<Promotion> findByNameContainingIgnoreCase(String name, Pageable paging);
}
