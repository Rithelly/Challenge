package br.com.challenge.starwars.config.secutiry;

import br.com.challenge.starwars.ChallengeApplication;
import br.com.challenge.starwars.config.security.jwt.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplication.class)
class TokenJwtTest {
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Test
    public void testToken() {

        UserDetails user = userDetailsService.loadUserByUsername("teste");
        Assertions.assertNotNull(user);

        String jwtToken = JwtUtil.createToken(user);
        System.out.println(jwtToken);
        Assertions.assertNotNull(jwtToken);

        boolean ok = JwtUtil.isTokenValid(jwtToken);
        Assertions.assertTrue(ok);

        String login = JwtUtil.getLogin(jwtToken);
        Assertions.assertEquals("teste",login);

        List<GrantedAuthority> roles = JwtUtil.getRoles(jwtToken);
        Assertions.assertNotNull(roles);

        System.out.println(roles);
        String role = roles.get(0).getAuthority();
        Assertions.assertEquals(role,"ADM");
    }

}
