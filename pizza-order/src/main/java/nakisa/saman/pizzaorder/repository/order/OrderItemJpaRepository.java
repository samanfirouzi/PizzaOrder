package nakisa.saman.pizzaorder.repository.order;

import nakisa.saman.pizzaorder.model.entry.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem,Long> {
}
