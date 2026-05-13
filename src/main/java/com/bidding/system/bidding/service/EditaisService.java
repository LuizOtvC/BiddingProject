/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditaisBean;
import com.bidding.system.bidding.repository.EditaisDao;
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
public class EditaisService {
    @Autowired
    private EditaisDao service;
    
    @Autowired
    private TokenService tokenservice;
    
    public void adicionarCondicao(EditaisBean edita){
        String mensagem = "";
        if(edita.getTitulo().equals("")){
            mensagem = "Nome não identificado";
        }else if(edita.getDescricao().equals("")){
            mensagem = "Email não preenchido";
        }else if(edita.getData_fechamento().equals("")){
            mensagem = "senha não preenchida";
        }else if(edita.getStatus().equals("")){
            mensagem = "não identificado";
        }
        
        if(!mensagem.equals("")){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
        }
        
        tokenservice.getKeySign();
    }
    
    }
