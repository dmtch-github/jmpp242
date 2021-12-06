package springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import springsecurity.business.entities.Roles;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //определяем пользователей
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //пароль пока не шифруем
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        //пока описываем пользователей системы в коде программы,потом будем брать из БД
        auth.inMemoryAuthentication()
                .withUser(userBuilder.username("dima").password("dima").roles(Roles.USER))
                .withUser(userBuilder.username("admin").password("admin").roles(Roles.ADMIN))
                .withUser(userBuilder.username("dimax").password("dimax").roles(Roles.USER,Roles.USER));
    }

    //определяем доступ по URL
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()
//                // указываем страницу с формой логина
//                .loginPage("/login")
//                //указываем логику обработки при логине
//                .successHandler(new LoginSuccessHandler())
//                // указываем action с формы логина
//                .loginProcessingUrl("/login")
//                // Указываем параметры логина и пароля с формы логина
//                .usernameParameter("j_username")
//                .passwordParameter("j_password")
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
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                //страницы аутентификаци доступна всем
                .antMatchers("/login").anonymous()
                // защищенные URL
                .antMatchers("/show-all-users").hasRole(Roles.ADMIN)
                .antMatchers("/show-user").hasAnyRole(Roles.USER, Roles.ADMIN)
                .and().formLogin().permitAll();
                //access("hasAnyRole('ADMIN')").anyRequest().authenticated();
    }
}
