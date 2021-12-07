package springsecurity.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import springsecurity.business.entities.Roles;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    private boolean hasRole(Collection<? extends GrantedAuthority> collection, String role) {

        return collection.stream().anyMatch(x -> x.getAuthority().equals(role));
//        for(GrantedAuthority ga: collection) {
//            if(ga.getAuthority().equals(role)) {
//                return true;
//            }
//        }
//        return false;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("Роли авторизованного = " + authentication.getAuthorities());

//        if(request.isUserInRole("ROLE_ADMIN")) {
        if(hasRole(authentication.getAuthorities(),Roles.ROLE_ADMIN)) {
            System.out.println("Обнаружен АДМИН");
            response.sendRedirect("/admin");
        } else {
            System.out.println("Обнаружен ЮЗЕР");
            response.sendRedirect("/user");
        }

        System.out.println("Вызван обработчик успешной авторизации");
    }
}