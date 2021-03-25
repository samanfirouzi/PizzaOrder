package nakisa.saman.pizzaorder.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionDetail {
    private Long id;
    private float discount;
    private String name;
    private String description;
    private String image;
}
