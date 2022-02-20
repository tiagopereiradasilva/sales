package br.com.company.sales.service;

import br.com.company.sales.SalesSysApplication;
import br.com.company.sales.entity.UserSystem;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService {

    @Value("${security.jwt.time}")
    private String expiration;
    @Value("${security.jwt.secret}")
    private String secret;

    public String createToken(UserSystem userSystem){
        //converdendo valor (minutos de expiração) do "application.properties" em long.
        long expireIn = Long.parseLong(expiration);
        //incrementando o valor de expiração à data atual.
        LocalDateTime dateTimeExpiration = LocalDateTime.now().plusMinutes(expireIn);
        //Criando data para passar no "setExpiration" do Jwt.
        Date dateExpiration = Date.from(dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant());
        //retornando token gerado como string.
        return Jwts
                .builder()
                .setSubject(userSystem.getUsername())
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean tokenIsValid(String token){
        try {
            //Pegando as claims do token.
            Claims claims = getClaims(token);
            //Pegando data de expiração passada para o token.
            Date dateExpiration = claims.getExpiration();
            //Convertendo dataExpiration para LocalDateTime para facilitar cálculos.
            LocalDateTime dateTimeExpiration =
                    dateExpiration
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
            //Verificando se o token ainda é válido e retornando o devido valor lógico.
            return !LocalDateTime.now().isAfter(dateTimeExpiration);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    private String getUserName(String token)throws ExpiredJwtException{
        //Pegando subject do token.
        return getClaims(token).getSubject();
    }

    //Run de teste para essa classe. Para usá-lo, basta descomentá-lo e executar o projeto a partir dele.
//    public static void main(String[] args){
//        var context = SpringApplication.run(SalesSysApplication.class);
//        JwtService jwtService = context.getBean(JwtService.class);
//        UserSystem userSystem = new UserSystem();
//        userSystem.setUsername("tiago");
//        String token = jwtService.createToken(userSystem);
//        String invalidToken = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NDUzODIwMzksImVtYWlsIjoidGlhZ29wZXJlaXJhQG1haWwuY29tIiwiY2xpZW50X2lkIjoiMiJ9.zVqfHrF7IKlNIWIlU8adGzhax6NoK-BOFb_48gLxX9CNEL6Jz30GDhb5QB0u4taqSJmb3QfqdItYzymyIjBD-Q";
//        System.out.println(token);
//        System.out.println("Token é válido? -> "+jwtService.tokenIsValid(token));
//        try{
//            System.out.println("Username : "+jwtService.getUserName(token));
//        }catch (Exception e){
//            System.out.println("Não foi possível encontrar username -> "+e.getMessage());
//        }
//
//    }

}
