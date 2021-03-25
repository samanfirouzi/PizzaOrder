package nakisa.saman.pizzaorder.repository.order;

import nakisa.saman.pizzaorder.model.entry.UserOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepositoryCRUD extends CrudRepository<UserOrder,Long> {
}
