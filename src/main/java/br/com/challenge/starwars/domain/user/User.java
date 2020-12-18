package br.com.challenge.starwars.domain.user;

import br.com.challenge.starwars.domain.film.Person;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id_usuario")
    private String idUsuario;

    private String nome;
    private String login;
    private String senha;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_funcoes",
            joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_funcao", referencedColumnName = "id_funcao"))
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column
    private Situacao situacao = Situacao.ATIVO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
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

    public boolean associateRole(Role role) {
        return roles.add(role);
    }

    @PrePersist
    public void PrePersist() {
        idUsuario = UUID.randomUUID().toString();
    }
}
