package ar.edu.unq.desapp.grupof.backendcriptop2papi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/*"))
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
          "Cripto P2P API",
          "API for contacting people interested in crypto currency exchanges",
          "1.0",
          "Study focused",
          new springfox.documentation.service.Contact("Grupo F", "https://github.com/nicolasdemaio/backend-criptop2p-api", "trejojulian998@gmail.com"),
                "API licence",
                "https://github.com/nicolasdemaio/backend-criptop2p-api",
                Collections.emptyList()


        );
    }
}