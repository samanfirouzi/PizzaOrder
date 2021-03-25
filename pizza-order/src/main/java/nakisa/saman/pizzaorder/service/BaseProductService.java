package nakisa.saman.pizzaorder.service;

import nakisa.saman.pizzaorder.model.entry.BaseProduct;
import nakisa.saman.pizzaorder.repository.product.BaseProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BaseProductService {
    @Autowired
    BaseProductJpaRepository baseProductJpaRepository;


    public Iterable<BaseProduct> getByType(Integer type) {
        BaseProduct baseProduct = new BaseProduct();
        baseProduct.setType(type);
        List<BaseProduct> all = baseProductJpaRepository.findAll(Example.of(baseProduct));
        return all;
    }

    public Page<BaseProduct> findByNameLikeOrIdOrType(Integer pageNo, Integer pageSize, Long id, String name, Integer type) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        BaseProduct baseProduct = new BaseProduct();
        Page<BaseProduct> pagedResult = null;

        if (hasValue(id)) {
            List<BaseProduct> baseProducts = new ArrayList<>();
            Optional<BaseProduct> byId = baseProductJpaRepository.findById(id);
            if(byId.isPresent()){
                baseProducts.add(byId.get());
                pagedResult= new PageImpl<BaseProduct>(List.of(byId.get()));
            }

        } else {
            int c = 0;
            if (hasValue(name)) {
                c = 1;
            }
            if (hasValue(type)) {
                baseProduct.setType(type);
                c += 2;
            }

            if (c == 3)
                pagedResult = baseProductJpaRepository.findByNameContainingIgnoreCaseAndType(name, type, paging);

            if (c == 1)
                pagedResult = baseProductJpaRepository.findByNameContainingIgnoreCase(name, paging);

            if (c == 2)
                pagedResult = baseProductJpaRepository.findAll(Example.of(baseProduct), paging);

            if (c == 0)
                pagedResult = baseProductJpaRepository.findAll( paging);
        }
        if (pagedResult!=null && pagedResult.hasContent()) {
            return pagedResult;
        } else {
            return new PageImpl<BaseProduct>(new ArrayList<>());
        }
    }


    private boolean hasValue(Long n) {
        return n != null;
    }

    private boolean hasValue(String s) {
        return s != null && !s.equals("") && !s.equals("null");
    }

    private boolean hasValue(Integer n) {
        return n != null;
    }
}
