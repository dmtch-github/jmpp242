package springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import springsecurity.business.entities.Roles;
import springsecurity.config.handler.LoginSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Задаем тип кодирования пароля
     * Метод конфликтует со строкой задаваемой в методе configure(AuthenticationManagerBuilder auth)
     * User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    //Аутентификация - идентификация реальности пользователя
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //аутентификация будет через класс с интерфейсом UserDetailsService
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //создание базы пользователей в слое UserDaoMemImp
//        auth.userDetailsService(inMemoryUserDetailsManager());// использует бин inMemoryUserDetailsManager()
    }

    /**
     * Создаем базу пользователей в памяти
     */
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager()
//    {
//        return new InMemoryUserDetailsManager(new ArrayList<>());
//    }

    //АВТОРИЗАЦИЯ - идентификация доступа к ресурсам в зависимости от роли пользователя
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//                .loginPage("/login")    //этот адрес прописываем в контроллере
//                //указываем логику обработки при логине
                .successHandler(new LoginSuccessHandler())
//                // указываем action с формы логина
//                .loginProcessingUrl("/login")
//                // Указываем параметры логина и пароля с формы логина
//                .usernameParameter("j_username")
//                .passwordParameter("j_password")
//                // даем доступ к форме логина всем
                .permitAll();

//        http.formLogin()
//                // указываем страницу с формой логина
//                .successHandler(new LoginSuccessHandler())
//                // даем доступ к форме логина всем
//                .permitAll();


//
        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном логауте
                //.logoutSuccessUrl("/login?logout")
                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable();

        http
                .authorizeRequests() // предоставить разрешение для перечисленых URL
                .antMatchers("/login").anonymous()
                .antMatchers("/admin/**").hasRole(Roles.ADMIN)  //доступ через роли
                .antMatchers("/user/**").hasAnyRole(Roles.USER, Roles.ADMIN)
//                .antMatchers("/admin/**").hasAuthority(Roles.ROLE_ADMIN)  //доступ через разрешения
//                .antMatchers("/user/**").hasAnyAuthority(Roles.ROLE_USER, Roles.ROLE_ADMIN)
//                .antMatchers("/**").permitAll() //разрешает доступ всем
                .anyRequest().authenticated()   //любые запросы только аутентифицированным пользователям
                .and().formLogin().permitAll(); //к логину доступ всем

    }
}
