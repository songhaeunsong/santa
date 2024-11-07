//package site.ssanta.santa.common.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import java.util.stream.Stream;
//
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//    private static final String[] SWAGGER = {
//            "/swagger-resources/**",
//            "/favicon.ico",
//            "/api-docs/**",
//            "/swagger-ui/**",
//            "/swagger-ui.html",
//            "/swagger-ui/index.html",
//            "/docs/swagger-ui/index.html",
//            "/swagger-ui/swagger-ui.css"
//    };
//
//    private static final String[] WHITELIST = {
//        "/"
//    };
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
//                .sessionManagement(
//                        (sessionManagement) -> sessionManagement.sessionCreationPolicy(
//                                SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(
//                        authorize -> authorize
//                                .requestMatchers(Stream
//                                        .of(SWAGGER)
//                                        .map(AntPathRequestMatcher::antMatcher)
//                                        .toArray(AntPathRequestMatcher[]::new)).permitAll()
//                                .requestMatchers(Stream
//                                        .of(WHITELIST)
//                                        .map(AntPathRequestMatcher::antMatcher)
//                                        .toArray(AntPathRequestMatcher[]::new)).permitAll()
//                                .anyRequest().authenticated());
////                .addFilterBefore(
////                        new JwtAuthenticationFilter(this.jwtTokenProvider,
////                                this.jwtAuthenticationEntryPoint),
////                        UsernamePasswordAuthenticationFilter.class)
////                .exceptionHandling(exceptionHandling -> exceptionHandling
////                        .authenticationEntryPoint(this.jwtAuthenticationEntryPoint));
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder PasswordEncoder () {
//        return new BCryptPasswordEncoder();
//    }
//}
