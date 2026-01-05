package com.example.demo.seguridad;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicios.UsuarioServicio;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // UserDetailsService
    @Bean
public UserDetailsService userDetailsService() {
    return username -> {
        Usuario u = usuarioServicio.buscarUsuarioPorNombreSP(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Verificar estado (boolean)
        if (!u.isEstado()) {
            throw new UsernameNotFoundException("Usuario inactivo");
        }

        return User.builder()
                .username(u.getUsuario())
                .password(u.getClave())
                .roles(u.getRol()) // "ADMIN", "USER", "VENDEDOR"
                .build();
    };
}



    // DaoAuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Seguridad HTTP
    @Bean
    public org.springframework.security.web.SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/invitado", "/registro/**", "/css/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/usuario/**").authenticated()
                .requestMatchers("/vendedor/**").hasAnyRole("ADMIN", "VENDEDOR")
                .requestMatchers("/compras/**").authenticated()

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((req, res, auth) -> {
                    if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        res.sendRedirect("/admin/principal");
                    } else if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                        res.sendRedirect("/usuario/principal");
                    } else if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR"))) {
                        res.sendRedirect("/vendedor/principal");
                    } else {
                        res.sendRedirect("/login?error");
                    }
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
            )
            .csrf(csrf -> csrf.disable());

        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    // Filtro No Cache global
    @Component
    public static class NoCacheFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            res.setHeader("Pragma", "no-cache");
            res.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        }
    }
}
