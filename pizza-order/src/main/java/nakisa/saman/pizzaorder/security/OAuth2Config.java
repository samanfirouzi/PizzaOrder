package nakisa.saman.pizzaorder.security;

import nakisa.saman.pizzaorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    private String clientId = "saman";
    private String clientSecret = "saman-secure-key";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAywPjVDRg2HYVSAkK8SL1LsR5mTqlS2dUc6cmBGb+3DzMduzx\n" +
            "Xruu3kF/OEh73iB8eEcVzcqJds0qMwwEMvKBxdn3FyNRd0aMlPACJhoLAXXw6deQ\n" +
            "gNj9btq9uYMps5M7qn8+dZZAjt0sHagyqZ5IgNR/gH5coSkhkb0DarqOmmWdyqnZ\n" +
            "MdzaSc8t/ZIngjzaYnNgLE2c+Xp6PkwGV5ykjdwZzVDGICNWT0FBRFKQm4oGn6xi\n" +
            "pjzq+/uCd89jp4fiCkeMOJIsXHvFnbK+HS8mx2luCN9Uy77nRo25ly33Z0XsdZtA\n" +
            "jobu1HXQPVmE/233mlaBciJeklOHmc3qwTRqjQIDAQABAoIBAGmlBE91UlBSYpCx\n" +
            "4/fH8T79Dyol0o6CnvfwK2BvGIETQnNAqfpaQI14F/P6BDt7PEp682W71/zyltsp\n" +
            "jaXVfKKw4lwwDNy84bs/il65frCr50pI2vGx+XAOdFxtXO4N2CbJQH+ASOgPQD3O\n" +
            "oU6TB7fC3PbIyvsypD1q1rRbkInXGOTekCFrXn9GtqCi4pWNwnVPpa0MlBYQkSr8\n" +
            "ZRjkCLrctaJ8zUGRouryXnhaJa6tsKaQ/SzNvSrZGCO8sUnxwv+lT4hf3rpd7eqX\n" +
            "9YNCQJTY8+DrX7n5ViJmLxgAkd2R5AYjF410aZjibfC/nMSwS13Mpcu9pGkG2TGp\n" +
            "HfIs9wECgYEA/B+a1vb/uCazB3+2zOa+KIv1crv/lZaS6TIfnH12rdHsEK3Dsznz\n" +
            "UFpFVCIiKWnzSWCA8RrRImCRP2ErfmWS9udPzcKdwsS9BsN8ht6wA8LIAICUvW4W\n" +
            "x04dhZbncXEjZV2HxWymP7gaU0+DPvvESEOn5EpOuv56DIIbB8ufMUkCgYEAziL8\n" +
            "Wu0heQMQN0A7iE2qWiOz1Nei/kx4arU8r9Un0DB0/HVYtfOWYKkZZqhW+JKL/rsp\n" +
            "+WXd7UtRvRO4shqZmltLOM2icuJLUv7rVKUvx6BVN4gtR/9eI/dD0FQ661ewxys9\n" +
            "O/Q1bIipBgDFiakBx7vecrI4SMocmhNMM4Ya8yUCgYBE54JS06hptLHjD8Vu1HUN\n" +
            "8co/9B9Sy5cVAmzfQO7JbIC+wi6k8eaMhE9EQhviA6HI4QX8Xt7fLVMk5jKT1cDH\n" +
            "l8oYquKg01Hx4kVLOtsR/A7bDEkaSFcTW0b4NpjESS5qSpiLnTkGWxNex3FQKlCn\n" +
            "i038N06zDk9e0cP/amf0eQKBgQCkCt98lJ1AqhlrfqhhrjzVAviHdxt09/IUKSqk\n" +
            "WyMV4JoYlmIEWlbfkXJvaaPcqPLTwqqCmHhl8LyZlWo0AksetWxFRg/zEPcUk10/\n" +
            "vT0qxgNK8Ano8+Y9BPdogL9qrhw2Q8i1P4CMYKzx5AdPp7mM84rxB7cW5hCg5J6N\n" +
            "mjk0KQKBgFGb1sT5Ml9DOQHO/Ba3jFs2A5DllnaMF2f8v9gBDv2YWTzuoQcvhCtr\n" +
            "fx+9FS3na6wk6rUIZMRSLZyQtPPLK/JKOki7UqlZjMVvHD01DeMvie09LKRg4acN\n" +
            "F4O1mt0anxDvmLqg9kJwhAFOKjCL5v7ke5FVg+Jt1cCiF4gxjBmo\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAywPjVDRg2HYVSAkK8SL1\n" +
            "LsR5mTqlS2dUc6cmBGb+3DzMduzxXruu3kF/OEh73iB8eEcVzcqJds0qMwwEMvKB\n" +
            "xdn3FyNRd0aMlPACJhoLAXXw6deQgNj9btq9uYMps5M7qn8+dZZAjt0sHagyqZ5I\n" +
            "gNR/gH5coSkhkb0DarqOmmWdyqnZMdzaSc8t/ZIngjzaYnNgLE2c+Xp6PkwGV5yk\n" +
            "jdwZzVDGICNWT0FBRFKQm4oGn6xipjzq+/uCd89jp4fiCkeMOJIsXHvFnbK+HS8m\n" +
            "x2luCN9Uy77nRo25ly33Z0XsdZtAjobu1HXQPVmE/233mlaBciJeklOHmc3qwTRq\n" +
            "jQIDAQAB\n" +
            "-----END PUBLIC KEY-----";
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public UserAuthenticationConverter userAuthenticationConverter() {
        DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
        defaultUserAuthenticationConverter.setUserDetailsService(userService);
        return defaultUserAuthenticationConverter;
    }


    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        ((DefaultAccessTokenConverter) converter.getAccessTokenConverter())
                .setUserTokenConverter(userAuthenticationConverter());
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(18000);

    }

}
