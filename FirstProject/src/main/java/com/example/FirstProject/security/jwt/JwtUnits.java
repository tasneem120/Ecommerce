package com.example.FirstProject.security.jwt;

import com.example.FirstProject.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUnits {
private static final Logger LOGGER= LoggerFactory.getLogger(JwtUnits.class);
@Value("${spring.app.jwtExpirationMs}")
private int jwtExpirationInMs;
@Value("${spring.app.jwtSecret}")
private  String jwtSecret;
@Value("${spring.ecom.app.jwtCookie}")
private String jwtCookieName;

//    public  String getJwtFromHeader(  HttpServletRequest httpServletRequest) {
//        String bearerToken = httpServletRequest.getHeader("Authorization");
//        LOGGER.debug("Bearer Token: {}", bearerToken);
//if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//    return bearerToken.substring(7);
//}
//return null;
//    }
    public String getJwtCookieName(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request,jwtCookieName);
        if (cookie != null) {
//            System.out.println("Cookie: " + cookie.getValue());
            return cookie.getValue();
        }else return null;

    }
public ResponseCookie generateJwtCookie(UserDetailsImpl  userDetails) {
        String jwt=generatTokenFromUsername(userDetails.getUsername());
        ResponseCookie cookie=ResponseCookie.from(jwtCookieName,jwt).
                maxAge(24*60*60)
                .path("/api").
                httpOnly(false).
                build();
        return cookie;
}

    public ResponseCookie getCleanJwtCookie() {

        ResponseCookie cookie=ResponseCookie.from(jwtCookieName,null)
                .path("/api").
                build();
        return cookie;
    }
public  String generatTokenFromUsername(   String username) {

        return Jwts.builder(). subject(username)
                .issuedAt(new Date()).
                expiration(new Date((new Date().getTime()+jwtExpirationInMs)))
                .signWith(key()).compact();


}
public  String getUsernameFromToken( String jwtToken  ) {
        return Jwts.parser().verifyWith((SecretKey) key())
                .build().
                parseSignedClaims(jwtToken).getPayload().getSubject();
}
public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret) );
}
public Boolean validateToken( String jwtToken ) {
        try {
            System.out.println("Validating Token");
            Jwts.parser().verifyWith((SecretKey) key())
                    .build().parseClaimsJws(jwtToken);
            return true;

        }catch (MalformedJwtException e){
            LOGGER.error("Invalid JWT Token : {} ",e.getMessage());
        }catch (ExpiredJwtException e){
            LOGGER.error("Expired JWT Token : {} ",e.getMessage());
        }catch (UnsupportedJwtException e){
            LOGGER.error("Unsupported JWT Token : {} ",e.getMessage());

        }catch (IllegalArgumentException e){
            LOGGER.error("JWT Clamis string is empty : {} ",e.getMessage());
        }
        return false;
}
}
