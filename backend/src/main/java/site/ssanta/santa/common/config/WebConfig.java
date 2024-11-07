package site.ssanta.santa.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${FRONT.URL}")
    private String FRONT_URL;

    @Value("${BACK.PATTERN}")
    private String PATTERN;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(PATTERN)
                .allowedOrigins(FRONT_URL)
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name())
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}
