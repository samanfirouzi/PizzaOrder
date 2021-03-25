package nakisa.saman.pizzaorder.service;

import nakisa.saman.pizzaorder.model.entry.Promotion;
import nakisa.saman.pizzaorder.repository.promotion.PromotionJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {
    @Autowired
    PromotionJpaRepository promotionJpaRepository;

    public Page<Promotion> findByNameLikeOrId(Integer pageNo, Integer pageSize, Long id, String name) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Promotion promotion = new Promotion();
        Page<Promotion> pagedResult = null;

        if (hasValue(id)) {
            List<Promotion> promotions = new ArrayList<>();
            Optional<Promotion> byId = promotionJpaRepository.findById(id);
            if (byId.isPresent()) {
                promotions.add(byId.get());
                pagedResult = new PageImpl<Promotion>(List.of(byId.get()));
            }

        } else if (hasValue(name)) {
            pagedResult = promotionJpaRepository.findByNameContainingIgnoreCase(name, paging);
        } else
            pagedResult = promotionJpaRepository.findAll(paging);
        if (pagedResult != null && pagedResult.hasContent()) {
            return pagedResult;
        } else {
            return new PageImpl<Promotion>(new ArrayList<>());
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
