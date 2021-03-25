package nakisa.saman.pizzaorder.model.entry;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PromotionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Long promotionId;

    @OneToOne( )
    @JoinColumn(name = "productId")
    private Product product;
//    private Long productId;

    private long quantity;
}
