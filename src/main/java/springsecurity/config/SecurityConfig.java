package springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springsecurity.business.entities.Roles;
import springsecurity.config.handler.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    //АУТЕНТИФИКАЦИЯ - идентификация реальности пользователя
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //аутентификация выполняется через класс с интерфейсом UserDetailsService
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //АВТОРИЗАЦИЯ - идентификация доступа к ресурсам в зависимости от роли пользователя
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .successHandler(new LoginSuccessHandler())
            .permitAll();

        http.logout()
            .permitAll()
            .and().csrf().disable();

        http
            .authorizeRequests() // предоставить разрешение для перечисленых URL
            .antMatchers("/login").anonymous()
            .antMatchers("/user/**").hasRole(Roles.USER)
            .antMatchers("/admin/**").hasRole(Roles.ADMIN)
            .anyRequest().authenticated()   //любые запросы только аутентифицированным пользователям
            .and().formLogin().permitAll(); //к логину доступ всем
    }
}
