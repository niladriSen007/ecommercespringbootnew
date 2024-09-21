package com.niladri.ecommerce.security;


import com.niladri.ecommerce.model.role.AppRoles;
import com.niladri.ecommerce.model.role.RoleModel;
import com.niladri.ecommerce.model.user.UserModel;
import com.niladri.ecommerce.repository.role.RoleRepo;
import com.niladri.ecommerce.repository.user.UserRepo;
import com.niladri.ecommerce.security.jwt.AuthEntryPointJwt;
import com.niladri.ecommerce.security.jwt.AuthTokenFilter;
import com.niladri.ecommerce.security.services.UserDetailsServiceCustom;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
    public class WebSecurityConfig {
        @Autowired
        UserDetailsServiceCustom userDetailsService;

        @Autowired
        private AuthEntryPointJwt unauthorizedHandler;

        @Bean
        public AuthTokenFilter authenticationJwtTokenFilter() {
            return new AuthTokenFilter();
        }


        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

            authProvider.setUserDetailsService(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder());

            return authProvider;
        }


        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
            return authConfig.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }



        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth ->
                            auth.requestMatchers("/api/v1/auth/**").permitAll()
                                    .requestMatchers("/v3/api-docs/**").permitAll()
                                    .requestMatchers("/h2-console/**").permitAll()
                                    //.requestMatchers("/api/admin/**").permitAll()
                                    //.requestMatchers("/api/public/**").permitAll()
                                    .requestMatchers("/swagger-ui/**").permitAll()
                                    .requestMatchers("/api/v1/test/**").permitAll()
                                    .requestMatchers("/images/**").permitAll()
                                    .anyRequest().authenticated()
                    );

            http.authenticationProvider(authenticationProvider());

            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            http.headers(headers -> headers.frameOptions(
                    frameOptions -> frameOptions.sameOrigin()));

            return http.build();
        }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
            return (web -> web.ignoring().requestMatchers("/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**"));
        }


        @Bean
        public CommandLineRunner initData(RoleRepo roleRepository, UserRepo userRepository, PasswordEncoder passwordEncoder) {
            return args -> {
                // Retrieve or create roles
                RoleModel userRole = roleRepository.findByRoleName(AppRoles.ROLE_USER)
                        .orElseGet(() -> {
                            RoleModel newUserRole = new RoleModel(AppRoles.ROLE_USER);
                            return roleRepository.save(newUserRole);
                        });

                RoleModel sellerRole = roleRepository.findByRoleName(AppRoles.ROLE_SELLER)
                        .orElseGet(() -> {
                            RoleModel newSellerRole = new RoleModel(AppRoles.ROLE_SELLER);
                            return roleRepository.save(newSellerRole);
                        });

                RoleModel adminRole = roleRepository.findByRoleName(AppRoles.ROLE_ADMIN)
                        .orElseGet(() -> {
                            RoleModel newAdminRole = new RoleModel(AppRoles.ROLE_ADMIN);
                            return roleRepository.save(newAdminRole);
                        });

                Set<RoleModel> userRoles = Set.of(userRole);
                Set<RoleModel> sellerRoles = Set.of(sellerRole);
                Set<RoleModel> adminRoles = Set.of(userRole, sellerRole, adminRole);


                // Create users if not already present
                if (!userRepository.existsByUserName("user1")) {
                    UserModel user1 = new UserModel("user1", "user1@example.com", passwordEncoder.encode("password1"));
                    userRepository.save(user1);
                }

                if (!userRepository.existsByUserName("seller1")) {
                    UserModel seller1 = new UserModel("seller1", "seller1@example.com", passwordEncoder.encode("password2"));
                    userRepository.save(seller1);
                }

                if (!userRepository.existsByUserName("admin")) {
                    UserModel admin = new UserModel("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
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

