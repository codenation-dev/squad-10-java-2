package br.com.codenation.log.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userService;

    @Value("${jwt.clientId}")
    private String clientId;

    @Value("${jwt.client-secret}")
    private String clientSecret;

    @Value("${jwt.signing-key}")
    private String jwtSigningKey;

    @Value("${jwt.accessTokenValidititySeconds}")
    private int accessTokenValiditySeconds;

    @Value("${jwt.authorizedGrantTypes}")
    private String[] authorizedGrantTypes;

    @Value("${jwt.refreshTokenValiditySeconds}")
    private int refreshTokenValiditySeconds;


    public OAuthConfiguration(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserDetailsService userService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                .authorizedGrantTypes(authorizedGrantTypes)
                .scopes("global")
                .resourceIds("api");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(userService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
        return new JwtAccessTokenConverter();
    }
}
