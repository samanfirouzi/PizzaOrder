package nakisa.saman.pizzaorder.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import nakisa.saman.pizzaorder.model.entry.OrderItem;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemModel {
    private OrderItem orderItem;
    private String name;
    private String image;
    private String size;
    private Float price;
}
