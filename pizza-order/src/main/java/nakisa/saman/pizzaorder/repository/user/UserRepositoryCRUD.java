package nakisa.saman.pizzaorder.repository.user;

import nakisa.saman.pizzaorder.model.entry.UserData;
import org.springframework.data.repository.CrudRepository;


public interface UserRepositoryCRUD extends CrudRepository<UserData, Long> {

}
