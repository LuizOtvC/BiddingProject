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
    
    public void adicionarlance(LancesBean edita, String token){
        EditaisBean editaa = tokenservice.extrairClaimsEditais(token);
        String mensagem = "";
        Date data = edita.getData_lance();
        if(editaa.getStatus().equals("ENCERRADO") || editaa.getData_fechamento().equals()){
            if(edita.getValor() == 0){
            mensagem += "Valor não encontrado";
        }else if(edita.getData_lance().equals())){
            mensagem += "descrição não preenchido";
        }else if(data == null){
            mensagem += "data não preenchida";
        }else if(edita.getStatus().equals("")){
            mensagem += "não identificado";
        }
        
        if(!mensagem.equals("")){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
        }
        
        edita.setStatus("ABERTO");
        int linhas = service.adicionarCondicao(edita);
        if(linhas == 0){
            throw new ResponseStatusException(HttpStatusCode.valueOf(500), "Erro ao cadastrar ao banco de dados");
        }     
    }else{
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Acesso não autorizado");
        }
        }
}
