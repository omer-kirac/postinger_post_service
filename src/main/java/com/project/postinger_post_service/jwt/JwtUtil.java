package com.project.postinger_post_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
@Service
public class JwtUtil {

    private String SECRET_KEY = "NTNv7j0TuYARvmNMmWXo6fKvM4o6nv/aUi9ryX38ZH+L1bkrnD1ObOQ8JAUmHCBq7Iy7otZcyAagBLHVKvvYaIpmMuxmARQ97jUVG16Jkpkp1wXOPsrF9zwew6TpczyHkHgX5EuLg2MeBuiT/qJACs1J0apruOOJCg/gOtkjB4c=";
    public Long extractId(@CookieValue(name = "jwtToken", required = false) String jwtFromCookie) {

        System.out.println("extracedId'ye girdi.");
        if (jwtFromCookie != null) {
            System.out.println("if'e girdi");
            System.out.println("jwtFromCookie: " + jwtFromCookie);
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwtFromCookie)
                    .getBody();
            System.out.println("deneme: "+ claims);
            Long userId = claims.get("userId", Long.class);
            return userId;
        } else {
            return null;
        }


    }
}
   /* public static Claims decodeJWT(String jwt, String secretKey) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt);

        return claimsJws.getBody();
    }

    public static void main(String[] args) {
        String jwtToken = "your_jwt_token_here";
        String secretKey = "your_secret_key_here";

        Claims claims = decodeJWT(jwtToken, secretKey);
        System.out.println("JWT Claims: " + claims);
    }
}*/
