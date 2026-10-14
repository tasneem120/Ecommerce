package com.example.FirstProject.security;
import java.util.Optional;
import com.example.FirstProject.Repository.RoleRepo;
import com.example.FirstProject.Repository.UserRepository;
import com.example.FirstProject.model.AppRoles;
import com.example.FirstProject.model.Role;
import com.example.FirstProject.model.User;
import com.example.FirstProject.security.jwt.AuthEntryPointJwt;
import com.example.FirstProject.security.jwt.AuthTokenFilter;
import com.example.FirstProject.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Autowired
    UserDetailsServiceImpl userDetailsService;
@Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authTokenFilterBean() {
        return new AuthTokenFilter();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProviderBean() {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider(userDetailsService);
authenticationProvider.setPasswordEncoder(passwordEncoderBean());
return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authconfig) throws Exception {
        return authconfig.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {

        http. csrf(csrf -> csrf.disable()).exceptionHandling(exception->
                exception.authenticationEntryPoint(unauthorizedHandler)).
                sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).
                authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers("/swagger-ui/**").permitAll()
//                        .requestMatchers("/api/admin/**").permitAll()
//                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/test/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                .anyRequest().authenticated()
        );
        http.authenticationProvider(authenticationProviderBean());
        http.addFilterBefore(authTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
http.headers(headers->headers.
        frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
));
return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(
                "/v2/api-docs",
                "/ponfiguration/ui",
                "/swagger-resources/ ** ",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/ ** "
        ));
    }
        @Bean
        public CommandLineRunner initData(RoleRepo roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
            return args -> {
                // Retrieve or create roles
                Role userRole = roleRepository.findByRoleName(AppRoles.ROLE_USER)
                        .orElseGet(() -> {
                            Role newUserRole = new Role(AppRoles.ROLE_USER);
                            return roleRepository.save(newUserRole);
                        });

                Role sellerRole = roleRepository.findByRoleName(AppRoles.ROLE_SELLER)
                        .orElseGet(() -> {
                            Role newSellerRole = new Role(AppRoles.ROLE_SELLER);
                            return roleRepository.save(newSellerRole);
                        });

                Role adminRole = roleRepository.findByRoleName(AppRoles.ROLE_ADMIN)
                        .orElseGet(() -> {
                            Role newAdminRole = new Role(AppRoles.ROLE_ADMIN);
                            return roleRepository.save(newAdminRole);
                        });

                Set<Role> userRoles = Set.of(userRole);
                Set<Role> sellerRoles = Set.of(sellerRole);
                Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);


                // Create users if not already present
                if (!userRepository.existsByUserName("user1")) {
                    User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("password1"));
                    userRepository.save(user1);
                }

                if (!userRepository.existsByUserName("seller1")) {
                    User seller1 = new User("seller1", "seller1@example.com", passwordEncoder.encode("password2"));
                    userRepository.save(seller1);
                }

                if (!userRepository.existsByUserName("admin")) {
                    User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                    userRepository.save(admin);
                }

                // Update roles for existing users
                userRepository.findByUserName("user1").ifPresent(user -> {
                    user.setRoles(userRoles);
                    userRepository.save(user);
                });

                userRepository.findByUserName("seller1").ifPresent(seller -> {
                    seller.setRoles(sellerRoles);
                    userRepository.save(seller);
                });

                userRepository.findByUserName("admin").ifPresent(admin -> {
                    admin.setRoles(adminRoles);
                    userRepository.save(admin);
                });
            };

    }
}
