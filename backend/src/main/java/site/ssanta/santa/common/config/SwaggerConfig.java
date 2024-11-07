package site.ssanta.santa.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info=@Info(title= "SANTA API SPEC",
        description = "Specification for service SANTA",
        version = "v1.0.0")
)
@SecurityScheme(
        name = "JWT",
        type= SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi v1OpenApi() {
        return GroupedOpenApi.builder()
                .group("SANTA API SPEC V1")
                .pathsToMatch("/**")
                .build();
    }
}
