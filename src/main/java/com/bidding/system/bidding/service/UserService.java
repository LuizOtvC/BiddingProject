/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.model.UserLogarBean;
import com.bidding.system.bidding.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class UserService {
    @Autowired
    private UserDao service;
    
    @Autowired
    private TokenService tokenservice;
    
    public void registrar(UserBean user){
        String mensagem = "";
        if(user.getNome().equals("")){
            mensagem = "Nome não identificado";
        }else if(user.getEmail().equals("")){
            mensagem = "Email não preenchido";
        }else if(user.getSenha().equals("")){
            mensagem = "senha não preenchida";
        }else if(user.getRole().equals("")){
            user.setRole("FORNECEDOR");
        }
        
        if(!mensagem.equals("")){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
        }
        
        service.register(user);
    }
    
    public String logar(UserLogarBean user){
        String mensagem = "";
        if(user.getEmail().equals("")){
            mensagem = "email não preenchido";
        }else if(user.getSenha().equals("")){
            mensagem = "senha não preenchida";
        }
        if(!mensagem.equals("")){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
        }
        
        UserBean Userlogar = service.logar(user.getEmail(), user.getSenha());
        return tokenservice.gerarToken(Userlogar);
        
        
    }
    
    
}
