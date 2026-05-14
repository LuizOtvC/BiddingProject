/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditaisBean;
import com.bidding.system.bidding.model.LancesBean;
import com.bidding.system.bidding.model.UserBean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.Jwts.claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    
    public SecretKey getKeySign(){
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String gerarToken(UserBean user){
        if((user.getId() == 0) || (user.getId() == null) || (user.getNome().equals("")) || (user.getEmail().equals("")) || (user.getRole().equals(""))){
        throw new ResponseStatusException(HttpStatus.valueOf(400), "Um ou mais tokens faltando");
    }else{
            return Jwts.builder()
            .subject(user.getNome())
            .claim("id", user.getId())
            .claim("nome", user.getNome())
            .claim("role", user.getRole())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 3000000))
            .signWith(this.getKeySign())
            .compact();    
        }
        
    }
    public boolean validarToken(String token) {
        try {
            // Cria um parser JWT com a chave secreta para validação
            Jwts.parser()
                    .setSigningKey(getKeySign())
                    .build()
                    // Analisa e valida o token (lança exceção se inválido ou expirado)
                    .parseClaimsJws(token);
            // Se chegou aqui, o token é válido
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            
             throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expirado ou invalido");
        }
    }
    
    public UserBean extrairClaims(String token) {
            Claims claim = Jwts.parser()
            .verifyWith(this.getKeySign())
            .build()
            .parseSignedClaims(token)
            .getPayload();
            
            
            UserBean user = new UserBean();
            user.setId(claim.get("id", Long.class));
            user.setNome(claim.get("nome", String.class));
            user.setRole(claim.get("role", String.class));
            return user;

}
    
    public EditaisBean extrairClaimsEditais(String token) {
            Claims claim = Jwts.parser()
            .verifyWith(this.getKeySign())
            .build()
            .parseSignedClaims(token)
            .getPayload();
            
            
            EditaisBean lance = new EditaisBean();
            lance.setId(claim.get("id", Long.class));
            lance.setStatus(claim.get("status", String.class));
           Long timestamp = claim.get("data", Long.class);
            if (timestamp != null) {
            lance.setData_fechamento(new java.sql.Date(timestamp));
}
            return lance;

}
}
