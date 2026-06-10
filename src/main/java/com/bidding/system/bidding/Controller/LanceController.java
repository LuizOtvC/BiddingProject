/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.Controller;

import com.bidding.system.bidding.model.LancesBean;
import com.bidding.system.bidding.model.MeuLanceBean;
import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.service.LanceService;
import com.bidding.system.bidding.service.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aluno
 */
@RestController
@RequestMapping("/api/lance")
public class LanceController {
    
    @Autowired
    private TokenService serviceToken;
    
    @Autowired
    private LanceService serviceLance;
    
    @PostMapping("/{id}")
    public String registarLance(@RequestHeader("Authorization") String auth, @RequestBody LancesBean lance, @PathVariable long id) {
    String token = auth.replace("Bearer ", "");
    UserBean usuarioLogado = serviceToken.extrairClaims(token);
    serviceLance.novoLance(id, lance, usuarioLogado);
    return "Lance Registrado com sucesso";
}
    @GetMapping("/meus-lances")
    public List<MeuLanceBean> getMeusLances(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return serviceLance.getMeusLances(token);
    }
}
