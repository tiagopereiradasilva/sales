package br.com.company.sales.config;

import br.com.company.sales.security.jwt.JwtAuthFilter;
import br.com.company.sales.security.jwt.JwtService;
import br.com.company.sales.service.UserSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Serviço para buscar informações do usuário
    @Autowired
    private UserSystemService userSystemService;
    @Autowired
    private JwtService jwtService;

    //Encoder para o password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(userSystemService, jwtService);
    }

    //Método para gerenciar as altenticações...
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSystemService)
                .passwordEncoder(passwordEncoder());
    }

    //Método para gerenciar as autorizações...
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/clientes/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/produtos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/pedidos/**")
                .hasRole("ADMIN")
                .antMatchers("/h2-console/**")
                .permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()//configuração para funcionamento do jwt.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//configuração para funcionamento do jwt (retirando as sessões.)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class); //executando nosso filtro jwt na sessão antes do filter padrão do spring.
    }
}
