package nakisa.saman.pizzaorder.service;

import nakisa.saman.pizzaorder.model.entry.Configuration;
import nakisa.saman.pizzaorder.repository.configuration.ConfigurationRepositoryCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigurationService {

    public static Map<String, String> conf = new HashMap();

    @Autowired
    ConfigurationRepositoryCRUD configurationRepositoryCRUD;

    @PostConstruct
    private void init() {
        configurationRepositoryCRUD.findAll().forEach(configuration -> {
            conf.put(configuration.getKey(), configuration.getValue());
        });
    }

    public long saveOrUpdateConfig(Configuration configuration) {
        configuration = configurationRepositoryCRUD.save(configuration);

        conf.put(configuration.getKey(), configuration.getValue());

        return configuration.getId();
    }

    public Iterable<Configuration> getAll() {
        return configurationRepositoryCRUD.findAll();
    }

    public Page<Configuration> findByNameLikeOrIdOrType(Integer pageNo, Integer pageSize, String name, String value) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Configuration> pagedResult = null;
        int vl = 0;

        if (name != null && !name.isBlank())
            vl = 1;
        if (value != null && !value.isBlank())
            vl += 2;

        if (vl == 1)
            pagedResult = configurationRepositoryCRUD.findByKeyContainingIgnoreCase(name, paging);
        else if (vl == 2)
            pagedResult = configurationRepositoryCRUD.findByValueContainingIgnoreCase(value, paging);
        else if (vl == 3)
            pagedResult = configurationRepositoryCRUD.findByKeyContainingIgnoreCaseAndValueContainingIgnoreCase(name, value, paging);
        else pagedResult = configurationRepositoryCRUD.findAll(paging);
        return pagedResult;
    }
}