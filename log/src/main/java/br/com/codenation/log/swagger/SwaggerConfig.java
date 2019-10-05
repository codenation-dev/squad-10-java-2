package br.com.codenation.log.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Value("${jwt.clientId}")
    private String clientId;

    @Value("${jwt.client-secret}")
    private String clientSecret;

    private String tokenEndpoint = "http://localhost:8080/oauth/token";

    private static final String LOG_OAUTH = "log_oauth";

    @Bean
    public Docket apis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.codenation"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .securitySchemes(List.of(securityScheme()))
                .securityContexts(List.of(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Central de Erros",
                "Sistema para centralizar registros de erros de aplicações",
                "Versão 0.0.1",
                null,
                null,
                null,
                null,
                new ArrayList<>()
        );
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .appName("central-de-erros")
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(tokenEndpoint);

        return new OAuthBuilder().name(LOG_OAUTH)
                .grantTypes(Collections.singletonList(grantType))
                .scopes(List.of(scopes()))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScopeBuilder()
                        .scope("read")
                        .description("???")
                        .build(),
                new AuthorizationScopeBuilder()
                        .scope("write")
                        .description("???")
                        .build()
        };
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        List.of(SecurityReference.builder()
                                .reference(LOG_OAUTH)
                                .scopes(scopes())
                                .build()))
                .forPaths(PathSelectors.regex("/log.*"))
                .build();
    }
}
