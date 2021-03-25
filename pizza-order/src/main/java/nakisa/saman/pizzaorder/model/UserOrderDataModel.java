package nakisa.saman.pizzaorder.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOrderDataModel {
    private OrderModel current;
    private OrderModel saved;
    private List<OrderModel> favorite;
    private List<OrderModel> history;

}
