package nakisa.saman.pizzaorder.repository.product;

import nakisa.saman.pizzaorder.model.entry.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepositoryCRUD  extends CrudRepository<Product, Long> {
}
