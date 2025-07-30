package pet.project.hh.config;

import pet.project.hh.service.AuthUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserDetailsService userDetailsService;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        String fetchUser = """
//                select email, password, enabled
//                from users
//                where email = ?
//                """;
//        String fetchRoles = """
//                select u.email, at.account_type
//                                from users u, account_types at
//                                where u.email = ?
//                                and u.account_type = at.id
//                """;
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(fetchUser)
//                .passwordEncoder(passwordEncoder)
//                .authoritiesByUsernameQuery(fetchRoles);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(userAuthenticationSuccessHandler())
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/users/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/respond/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.POST, "/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.PUT, "/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.DELETE, "/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.GET, "/resumes/**").hasAnyRole("EMPLOYER", "APPLICANT")
                        .requestMatchers(HttpMethod.POST, "/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.PUT, "/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.DELETE, "/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.GET, "/").hasAnyRole("EMPLOYER", "APPLICANT")
                        .requestMatchers(HttpMethod.GET, "/vacancies").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler userAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException {
                String role = authentication.getAuthorities().iterator().next().getAuthority();
                if (role.equals("ROLE_EMPLOYER")) {
                    response.sendRedirect("/resumes");
                } else if (role.equals("ROLE_APPLICANT")) {
                    response.sendRedirect("/vacancies");
                } else {
                    response.sendRedirect("/");
                }
            }
        };
    }
}