/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditaisBean;
import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.repository.EditaisDao;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
    
    
    
    
    
    public List<EditaisBean> lerTodos(String authHeader, boolean urgente){
         if (!tokenservice.validarToken(authHeader)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido!");
        }
        List<EditaisBean> linhas = service.lerTodos();
        
        if (!urgente) {
            return linhas;
        }
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limite = agora.plusHours(48);

        return linhas.stream()
                .filter(edital -> "ABERTO".equalsIgnoreCase(edital.getStatus()))
                .filter(edital -> edital.getData_fechamento() != null)
                .filter(edital -> {
                    LocalDateTime fechamento = edital.getData_fechamento();

                    return fechamento.isAfter(agora)
                            && fechamento.isBefore(limite);
                })
                .collect(Collectors.toList());
    }
    
     public void novoEdital(EditaisBean edital, UserBean usuarioLogado) {
        String message = "";
        if (!usuarioLogado.getRole().equals("COMPRADOR")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403),
                    "Acesso negado: apenas usuários com role COMPRADOR podem criar editais"
            );
        }
        if (edital.getTitulo().isEmpty()) {
            message += "Título não preenchido!";
        }
        if (edital.getDescricao().isEmpty()) {
            message += "Descrição não preenchida!";
        }
        if (edital.getData_fechamento() == null) {
            message += "Data não preenchida!";
        }
        if (!message.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), message);
        }
        edital.setStatus("ABERTO");
        int rows = service.adicionarEdital(edital);
        if (rows == 0) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(500),
                    "Erro ao criar edital");
        }
    }
}
