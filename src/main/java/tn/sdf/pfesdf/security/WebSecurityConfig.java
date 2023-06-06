package tn.sdf.pfesdf.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tn.sdf.pfesdf.security.jwt.AuthEntryPointJwt;
import tn.sdf.pfesdf.security.jwt.AuthTokenFilter;
import tn.sdf.pfesdf.security.services.UserDetailsServiceImpl;


@Configuration
@EnableGlobalMethodSecurity( //assure la sécurité AOP sur les méthodes. Il permet @PreAuthorize, @PostAuthorize,
        //securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig {


    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler; // gère les tentatives d'accès non autorisées.

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }//un filtre personnalisé qui intercepte les requêtes entrantes, vérifie la présence d'un jeton JWT dans les en-têtes de la requête et valide le jeton.

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //comparant un mot de passe soumis à un mot de passe encodé stocké dans la base de données.
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();//utilisée pour authentifier les utilisateurs.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {//La méthode passwordEncoder renvoie une instance de BCryptPasswordEncoder, qui est utilisée pour encoder les mots de passe.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable() // En désactivant cette protection, on autorise les requêtes HTTP à être effectuées à partir de n'importe quel domaine.
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() //configure l'entrée du point d'authentification pour les erreurs d'authentification. L'implémentation unauthorizedHandler est utilisée pour renvoyer une réponse HTTP 401 (Unauthorized) lorsque l'utilisateur n'est pas authentifié.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //ce qui signifie que l'application n'utilise pas de session HTTP. Cela permet d'éviter les problèmes de synchronisation de session dans les environnements distribués et garantit que chaque requête HTTP est autonome et contient toutes les informations nécessaires à l'authentification.
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll() //indique que toutes les requêtes sur les URL commençant par /api/auth/ sont autorisées sans aucune restriction d'authentification.
               .antMatchers("/swagger-ui/**").permitAll() // indique que toutes les requêtes sur les URL commençant par /api/test/ sont également autorisées sans restriction.
             //   .antMatchers("/**").permitAll()
                //.antMatchers("/parrain/**").permitAll()
                //.antMatchers("/personne/**").permitAll()
                //.antMatchers("http://192.168.162.222:4200/**").permitAll()
                //.antMatchers("/parrain/retrieve-all-parrains").hasRole("ROLE_PARRAIN")
                .anyRequest().authenticated(); //spécifie que toutes les autres URL nécessitent une authentification.
        http.csrf().disable().authorizeRequests();

        http.authenticationProvider(authenticationProvider()); //indique que le DaoAuthenticationProvider créé dans la méthode authenticationProvider() doit être utilisé pour authentifier les utilisateurs.

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); //garantir que le jeton JWT est vérifié avant la tentative d'authentification par nom d'utilisateur et mot de passe.

        return http.build(); // renvoie la chaîne de filtres de sécurité configurée.
    }
}
