package site.ssanta.santa.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.ssanta.santa.common.jwt.JwtTokenFilter;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${FRONT.URL}")
    private String FRONT_URL;

    @Value("${BACK.PATTERN}")
    private String PATTERN;

    private static final String[] JWT_FILTER_PATTERNS = {
            "/member/**",
            "/member/*"
    };

    private final JwtTokenFilter jwtTokenFilter;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(PATTERN)
                .allowedOrigins(FRONT_URL)
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name())
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> jwtFilter() {
        FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtTokenFilter);
        registrationBean.addUrlPatterns(JWT_FILTER_PATTERNS);
        registrationBean.setOrder(1);
        registrationBean.setName("jwtTokenFilter");
        return registrationBean;
    }
}
