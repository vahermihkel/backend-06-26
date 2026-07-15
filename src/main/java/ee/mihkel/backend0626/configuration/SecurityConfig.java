package ee.mihkel.backend0626.configuration;

import ee.mihkel.backend0626.security.BearerTokenAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final BearerTokenAuthFilter bearerTokenAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
             .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> {
                request.requestMatchers(HttpMethod.POST, "/smart-id").permitAll();
                request.requestMatchers(HttpMethod.POST, "/signup").permitAll();
                request.requestMatchers(HttpMethod.POST, "/login").permitAll();
                request.requestMatchers(HttpMethod.GET, "/products").permitAll();
                request.requestMatchers(HttpMethod.GET, "/categories").permitAll();
                request.requestMatchers(HttpMethod.GET, "/products/*").permitAll();
                request.requestMatchers(HttpMethod.POST, "/products").hasAuthority("ADMIN");
                request.requestMatchers(HttpMethod.DELETE, "/products/*").hasAuthority("ADMIN");
                request.requestMatchers(HttpMethod.PUT, "/products").hasAuthority("ADMIN");
                request.requestMatchers(HttpMethod.POST, "/categories").hasAuthority("ADMIN");
                request.requestMatchers(HttpMethod.DELETE, "/categories/*").hasAuthority("ADMIN");
                request.requestMatchers(HttpMethod.GET, "/orders").hasAuthority("ADMIN");
                request.requestMatchers(HttpMethod.GET, "/persons").hasAuthority("SUPERADMIN");
                request.requestMatchers(HttpMethod.GET, "/send-email").permitAll();
                request.anyRequest().authenticated(); // CUSTOMER SAAB TEHA
        })
                .addFilterBefore(bearerTokenAuthFilter , UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
