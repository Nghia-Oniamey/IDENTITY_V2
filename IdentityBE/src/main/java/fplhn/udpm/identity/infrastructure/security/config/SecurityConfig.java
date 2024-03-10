package fplhn.udpm.identity.infrastructure.security.config;


import fplhn.udpm.identity.infrastructure.security.RestAuthenticationEntryPoint;
import fplhn.udpm.identity.infrastructure.security.filter.TokenAuthenticationFilter;
import fplhn.udpm.identity.infrastructure.security.oauth2.CustomOAuth2UserService;
import fplhn.udpm.identity.infrastructure.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import fplhn.udpm.identity.infrastructure.security.oauth2.OAuth2AuthenticationFailureHandler;
import fplhn.udpm.identity.infrastructure.security.oauth2.OAuth2AuthenticationSuccessHandler;
import fplhn.udpm.identity.infrastructure.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        source.registerCorsConfiguration("/**", config.applyPermitDefaultValues());
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "*"));
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));

        return source;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(c -> c.configurationSource(corsConfigurationSource()));
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.exceptionHandling(e -> e.authenticationEntryPoint(new RestAuthenticationEntryPoint()));
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/",
                "/error",
                "/favicon.ico",
                "/*/*.png",
                "/*/*.gif",
                "/*/*.svg",
                "/*/*.jpg",
                "/*/*.html",
                "/*/*.css",
                "/*/*.js").permitAll());
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                "/auth/**",
                                "/api/auth/**",
                                "/api/connector/**",
                                "/oauth2/**",
                                "/api/module/**",
                                "/api/nhan-vien/**",
                                "/api/entry-module/**"
                        )
                        .permitAll());
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.oauth2Login(oauth2 -> oauth2.authorizationEndpoint(a -> a.baseUri("/oauth2/authorize"))
                .redirectionEndpoint(r -> r.baseUri("/oauth2/callback/**"))
                .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
                .authorizationEndpoint(a -> a.authorizationRequestRepository(cookieAuthorizationRequestRepository()))
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler));
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
