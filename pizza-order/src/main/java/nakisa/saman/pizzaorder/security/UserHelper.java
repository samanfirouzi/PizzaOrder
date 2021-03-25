package nakisa.saman.pizzaorder.security;

import lombok.Getter;
import nakisa.saman.pizzaorder.model.entry.UserData;
import org.springframework.security.core.userdetails.User;

public class UserHelper extends User {
    private static final long serialVersionUID = 1L;

    //custom parameters
    @Getter
    private Long userId;

    public UserHelper(UserData user) {
        super(
                user.getUsername(),
                user.getPassword(),
                user.getGrantedAuthorities()
        );

        userId = user.getId();
    }


}
