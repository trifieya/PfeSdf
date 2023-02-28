package tn.sdf.pfesdf.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;


@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password; //pour ne pas être retourné dans la réponse JSON.

    private Collection<? extends GrantedAuthority> authorities; //les éléments de la Collection sont des objets qui étendent l'interface GrantedAuthority. GrantedAuthority est une interface de Spring Security qui représente une autorisation attribuée à un utilisateur. Les implémentations courantes de cette interface sont SimpleGrantedAuthority et Role, qui sont utilisées pour stocker les rôles de l'utilisateur.

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl buildPersonne(Personne personne) {
        List<GrantedAuthority> authorities = personne.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                personne.getIdPersonne(),
                personne.getUsername(),
                personne.getEmail(),
                personne.getPassword(),
                authorities);
    }
    public static UserDetailsImpl buildParrain(Parrain parrain) {
        List<GrantedAuthority> authorities = parrain.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());


        return new UserDetailsImpl(
                parrain.getIdParrain(),
                parrain.getUsername(),
                parrain.getEmail(),
                parrain.getPassword(),
                authorities);
    }
    public static UserDetailsImpl buildAgent(Agent agent) {
        List<GrantedAuthority> authorities = agent.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                agent.getIdAgent(),
                agent.getUsername(),
                agent.getEmail(),
                agent.getPassword(),
                authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }



}
