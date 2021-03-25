package nakisa.saman.pizzaorder.repository.order;

import nakisa.saman.pizzaorder.model.entry.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<UserOrder,Long> {
}
