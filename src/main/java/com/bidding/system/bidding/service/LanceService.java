/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditaisBean;
import com.bidding.system.bidding.model.LancesBean;
import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.repository.EditaisDao;
import com.bidding.system.bidding.repository.LancesDao;
import java.sql.Date;
import java.time.LocalDateTime;
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
    private LancesDao service;
    
    @Autowired
    private TokenService tokenservice;
    
    
    
    public void novoLance(Long id, LancesBean lance, UserBean usuarioLogado) {
    if (!usuarioLogado.getRole().equals("FORNECEDOR")) {
        throw new ResponseStatusException(HttpStatusCode.valueOf(403),
                "Acesso negado: apenas usuários com role FORNECEDOR podem criar lances");
    }
    lance.setId_usuario(usuarioLogado.getId()); 
    lance.setId_edital(id);                     
    lance.setData_lance(LocalDateTime.now());

    service.adicionarLance(id, lance);
}
    public java.util.List<com.bidding.system.bidding.model.MeuLanceBean> getMeusLances(String token) {
        if (!tokenservice.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido!");
        }

        UserBean userLogado = tokenservice.extrairClaims(token);
        if (!"FORNECEDOR".equals(userLogado.getRole())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Apenas fornecedores podem visualizar seus lances.");
        }

        java.util.List<com.bidding.system.bidding.model.MeuLanceBean> meusLances = service.getMeusLances(userLogado.getId());

        for (com.bidding.system.bidding.model.MeuLanceBean lance : meusLances) {
            boolean isFechado = !"ABERTO".equals(lance.getStatus());
            if (isFechado) {
                Double menorValor = service.getMenorLanceByEdital(lance.getId_edital());
                if (menorValor != null && lance.getValor() == menorValor) {
                    lance.setVencedor(true);
                } else {
                    lance.setVencedor(false);
                }
            } else {
                lance.setVencedor(false);
            }
        }

        return meusLances;
    }
}
