package nakisa.saman.pizzaorder.repository.configuration;

import nakisa.saman.pizzaorder.model.entry.BaseProduct;
import nakisa.saman.pizzaorder.model.entry.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ConfigurationRepositoryCRUD extends JpaRepository<Configuration,Long> {
    Page<Configuration> findByKeyContainingIgnoreCaseAndValueContainingIgnoreCase(String ckey, String value, Pageable paging);
    Page<Configuration> findByKeyContainingIgnoreCase(String ckey,  Pageable paging);
    Page<Configuration> findByValueContainingIgnoreCase( String cvalue, Pageable paging);

}
