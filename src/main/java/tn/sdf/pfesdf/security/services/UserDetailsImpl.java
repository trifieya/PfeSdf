package tn.sdf.pfesdf.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tn.sdf.pfesdf.entities.*;


@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private String nom;


    private String Photo;

    private String prenom;

    private LocalDate age;

    private TrancheAge trancheAge;
    //@JsonIgnore
    private String password;


    private Gender gender;
    private Integer phnum;
    private Integer cin;

    private Float logitude;

    private Float latitude;
    private List<String> roles;

    private Collection<? extends GrantedAuthority> authorities; //les éléments de la Collection sont des objets qui étendent l'interface GrantedAuthority. GrantedAuthority est une interface de Spring Security qui représente une autorisation attribuée à un utilisateur. Les implémentations courantes de cette interface sont SimpleGrantedAuthority et Role, qui sont utilisées pour stocker les rôles de l'utilisateur.


    public UserDetailsImpl(Long id, String username, String email, String nom, String photo, String prenom,
                           LocalDate age, TrancheAge trancheAge, String password, Gender gender,
                           Integer phnum, Integer cin, Float logitude, Float latitude,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.Photo = photo;
        this.prenom = prenom;
        this.age = age;
        this.trancheAge = trancheAge;
        this.password = password;
        this.gender = gender;
        this.phnum = phnum;
        this.cin = cin;
        this.logitude = logitude;
        this.latitude = latitude;
        this.authorities = authorities;
    }
    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }




    public static UserDetailsImpl build(Personne personne) {
        List<GrantedAuthority> authorities = personne.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                personne.getIdPersonne(),
                personne.getUsername(),
                personne.getEmail(),
                personne.getNom(),
                personne.getPhoto(),
                personne.getPrenom(),
                personne.getAge(),
                personne.getTrancheAge(),
                personne.getPassword(),
                personne.getGender(),
                personne.getPhnum(),
                personne.getCin(),
                personne.getLogitude(),
                personne.getLatitude(),
                authorities);
    }

    public static UserDetailsImpl build(Parrain parrain) {
        List<GrantedAuthority> authorities = parrain.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                parrain.getIdParrain(),
                parrain.getUsername(),
                parrain.getEmail(),
                parrain.getNom(),
                parrain.getPhoto(),
                parrain.getPrenom(),
                parrain.getAge(),
                parrain.getTrancheAge(),
                parrain.getPassword(),
                parrain.getGender(),
                parrain.getPhnum(),
                parrain.getCin(),
                parrain.getLogitude(),
                parrain.getLatitude(),
                authorities);
    }

    public static UserDetailsImpl build(Agent agent) {
        List<GrantedAuthority> authorities = agent.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                agent.getIdAgent(),
                agent.getUsername(),
                agent.getEmail(),
                agent.getNom(),
                agent.getPhoto(),
                agent.getPrenom(),
                agent.getAge(),
                agent.getTrancheAge(),
                agent.getPassword(),
                agent.getGender(),
                agent.getPhnum(),
                agent.getCin(),
                agent.getLogitude(),
                agent.getLatitude(),
                authorities);
    }
    public static UserDetailsImpl build(Admin admin) {
        List<GrantedAuthority> authorities = admin.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                admin.getIdAdmin(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getPassword(),
                authorities);
    }

    public static UserDetailsImpl build(Object user) {
        if (user instanceof Personne) {
            return build((Personne) user);
        } else if (user instanceof Parrain) {
            return build((Parrain) user);
        } else if (user instanceof Agent) {
            return build((Agent) user);
        } else if (user instanceof Admin) {
            return build((Admin) user);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }
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

    public String getNom() {
        return nom;
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
