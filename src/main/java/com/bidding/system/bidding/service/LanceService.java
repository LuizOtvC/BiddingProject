/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditaisBean;
import com.bidding.system.bidding.model.LancesBean;
import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.repository.EditaisDao;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class LanceService {
    @Autowired
    private EditaisDao service;
    
    @Autowired
    private TokenService tokenservice;
    
    @Autowired
    private LancesBean lance;
    
    public void adicionarlance(Long id, LancesBean edita, String token){
        if(tokenservice.validarToken(token)){
            UserBean userlogado = tokenservice.extrairClaims(token);
            
            if(!userlogado.getRole().equals("FORNECEDOR")){
                throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Voce precisa ser Fornecedor para adicionar um lance");
            }
            
            EditaisBean edital = service.getById(id);
            
            if(!edital.getStatus().equals("ABERTO")){
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), "nao pode se crair lances para um edital fechado");
            }
            
            if(edital.getData_fechamento().before(lance.getData_lance()) ){
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Data do lance posterior ao fechamento");
                
            }
        }else{
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token Invalido");
        }
   
}
}
