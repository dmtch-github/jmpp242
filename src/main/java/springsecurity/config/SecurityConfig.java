package springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import springsecurity.business.entities.Roles;
import springsecurity.config.handler.LoginSuccessHandler;

//@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //определяем тип кодирования пароля
    //не сочетается со строкой
//    User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    //определяем пользователей
    //Аутентификация
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //пароль пока не шифруем
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        //пока описываем пользователей системы в коде программы,потом будем брать из БД
        auth.inMemoryAuthentication()
//                .withUser("dima").password("dima").roles(Roles.ADMIN);
                .withUser(userBuilder.username("dima").password("dima").roles(Roles.USER))
                .withUser(userBuilder.username("admin").password("admin").roles(Roles.ADMIN))
                .withUser(userBuilder.username("dimax").password("dimax").roles(Roles.USER,Roles.ADMIN));
    }

    //определяем доступ по URL
    //АВТОРИЗАЦИЯ
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
//        http.logout()
//                // разрешаем делать логаут всем
//                .permitAll()
//                // указываем URL логаута
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                // указываем URL при удачном логауте
//                .logoutSuccessUrl("/login?logout")
//                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
//                .and().csrf().disable();

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
