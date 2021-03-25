package nakisa.saman.pizzaorder.controller.user;

import nakisa.saman.pizzaorder.repository.user.UserRepositoryCRUD;
import nakisa.saman.pizzaorder.model.entry.UserData;
import nakisa.saman.pizzaorder.security.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    UserRepositoryCRUD userRepositoryCRUD;

    @Autowired
    JwtTokenStore tokenStore;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("userGrants")
    public Object[] userGrants(@AuthenticationPrincipal UserHelper user) {

        return user.getAuthorities().toArray();
    }

    @PermitAll
    @CrossOrigin("*")
    @GetMapping("/addUser")
    public UserData addUser(@RequestParam String fullName,@RequestParam String username,@RequestParam String password){
        UserData userData = new UserData();
        userData.setUsername(username);
        userData.setFullName(fullName);
        userData.setPassword(encoder.encode(password));
        userData.setUgrant("CUSTOMER");
        return userRepositoryCRUD.save(userData);
    }

    @GetMapping("/oauth/logout")
    public ResponseEntity<String> revoke(@RequestParam String refresh, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.contains("Bearer")) {
                String tokenValue = authorization.replace("Bearer", "").trim();

                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                OAuth2RefreshToken refreshToken =tokenStore.readRefreshToken(refresh);

                tokenStore.removeRefreshToken(refreshToken);
                tokenStore.removeAccessToken(accessToken);

                tokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid access token");
        }

        return ResponseEntity.ok().body("Access token invalidated successfully");
    }


}
