package nakisa.saman.pizzaorder.controller;

import nakisa.saman.pizzaorder.model.entry.Configuration;
import nakisa.saman.pizzaorder.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("config")
public class ConfigurationController {
    @Autowired
    ConfigurationService configurationService;


    @PermitAll
    @GetMapping("all")
    public Iterable<Configuration> getAll() {
        return configurationService.getAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("search")
    public Page<Configuration> search(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                      @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String value) {
        Page<Configuration> all = configurationService.findByNameLikeOrIdOrType(pageNo, pageSize, name, value);
        return all;
    }

    @PermitAll
    @GetMapping("get{key}")
    public String getConfig(@PathVariable String key) {
        return ConfigurationService.conf.get(key);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add")
    public Long add(@RequestBody Configuration configuration) {
        return configurationService.saveOrUpdateConfig(configuration);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    public Long update(@RequestBody Configuration configuration) {
        return configurationService.saveOrUpdateConfig(configuration);
    }
}
