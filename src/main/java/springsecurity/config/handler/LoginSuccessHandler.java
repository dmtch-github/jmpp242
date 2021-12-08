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
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
//        if(request.isUserInRole("ROLE_ADMIN")) TODO данный метод не определяет принадлежность к Роли???
        String url = "/";
        if(hasRole(authentication.getAuthorities(),Roles.ROLE_ADMIN)) {
            url = "/admin";
        } else if(hasRole(authentication.getAuthorities(),Roles.ROLE_USER)) {
            url = "/user";
        }
        response.sendRedirect(url);
        System.out.println("Пользователь перенаправлен на страницу " + url);

        /*
                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println("Обнаружены роли: " + roles);
        if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user");
        } else if(roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else {
            httpServletResponse.sendRedirect("/");
        }

         */
    }
}