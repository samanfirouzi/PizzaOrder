package nakisa.saman.pizzaorder.service;

import nakisa.saman.pizzaorder.repository.user.UserRepository;
import nakisa.saman.pizzaorder.security.UserHelper;
import nakisa.saman.pizzaorder.model.entry.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;



    @Override
    public UserHelper loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserData userData = null;
        try {
            userData = userRepository.getUserDetails(username);
            UserHelper userHelper = new UserHelper(userData);

            return userHelper;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("'" + username + "' not found.");
        }
    }

}