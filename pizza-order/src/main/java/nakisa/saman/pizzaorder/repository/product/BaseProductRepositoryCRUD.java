package nakisa.saman.pizzaorder.repository.product;

import nakisa.saman.pizzaorder.model.entry.BaseProduct;
import org.springframework.data.repository.CrudRepository;

public interface BaseProductRepositoryCRUD   extends CrudRepository<BaseProduct, Long> {
}
