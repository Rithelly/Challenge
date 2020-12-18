package br.com.challenge.starwars.domain.user.repository.spec;

import br.com.challenge.starwars.domain.user.User;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public interface UserSpec {

    @Spec(path = "email", params = "email", spec = Like.class)
    interface EmailSpec extends Specification<User> {}
}
