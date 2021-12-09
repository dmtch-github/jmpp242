package springsecurity.business.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name="users242")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="email")
    private String email;   //уникальное значение

    @Column(name="name")
    private String name;

    @Column(name="last_name")
    private String lastName;

    @Column(name="age")
    private byte age;

    @Column(name="password")
    private String password;

    @Transient
    private String textRoles;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users242_roles242",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(int id, String email, String name, String lastName, byte age, String password, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        setRoles(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    /**
     * При установке ролей создается
     * строка с описанием ролей для сайта
     */
    public void setRoles(Set<Role> roles) {
        textRoles = roles.stream()
                .map(x -> x.getRole())
                .map(x -> x.replace("ROLE_",""))
                .sorted()
                .collect(Collectors.joining(" "));
        System.out.println(textRoles);
        this.roles = roles;
    }

    public void setRoles(String textRoles) {
        String[] rls = textRoles.split(" ");
        if(rls.length == 1) {
            if(rls[0].equals("USER")) {
                setRoles(Collections.singleton(new Role(0, "ROLE_USER")));
            } else {
                setRoles(Collections.singleton(new Role(1, "ROLE_ADMIN")));
            }
        }
        if(rls.length == 2) {
            setRoles(new HashSet<Role>(Arrays.asList(
                    new Role(0, "ROLE_USER"),
                    new Role(1, "ROLE_ADMIN"))));
        }

        //TODO здесь преобразовать строку в набор объектов Role
        //и установить в this.roles
    }

    public String getTextRoles() {
        return textRoles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

}
