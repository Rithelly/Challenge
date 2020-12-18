package br.com.challenge.starwars.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@Table(name = "funcao")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements GrantedAuthority {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id_funcao")
    private String idFuncao;

    private String nome;

    @Override
    public String getAuthority() {
        return nome;
    }
}