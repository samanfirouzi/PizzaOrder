package nakisa.saman.pizzaorder.repository.product;

import nakisa.saman.pizzaorder.model.entry.BaseProduct;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BaseProductJpaRepository extends JpaRepository<BaseProduct, Long> {
    Page<BaseProduct> findByNameContainingIgnoreCase(String name, Pageable paging);
    Page<BaseProduct> findByNameContainingIgnoreCaseAndType(String name, Integer type, Pageable paging);
}
