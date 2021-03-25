package nakisa.saman.pizzaorder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import nakisa.saman.pizzaorder.model.entry.UserOrder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderModel {
    private Long id;
    private String date;
    private Integer state;
    @JsonIgnore
    private UserOrder userOrder;
    private List<OrderItemModel> itemlist = null;
}
