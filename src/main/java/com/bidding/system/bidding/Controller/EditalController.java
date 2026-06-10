/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.Controller;

import com.bidding.system.bidding.model.EditaisBean;
import com.bidding.system.bidding.model.LancesBean;
import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.service.EditaisService;
import com.bidding.system.bidding.service.LanceService;
import com.bidding.system.bidding.service.TokenService;
import com.bidding.system.bidding.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aluno
 */
@RestController
@RequestMapping("/api/edital")
public class EditalController {
    
    @Autowired
    private EditaisService service;
    
    @Autowired
    private TokenService serviceToken;
    
    @Autowired
    private LanceService serviceLance;
    
    
    @PostMapping("/inserir")
    public String cadastrarEdital(@RequestHeader("Authorization") String auth, @RequestBody EditaisBean edital){
        String token = auth.replace("Bearer ", "");
        UserBean usuarioLogado = serviceToken.extrairClaims(token);
        service.novoEdital(edital, usuarioLogado);
        return "edital cadastrado com sucesso";
        
        
    }
    @GetMapping("/listar")
    public List<EditaisBean> lerTodos(@RequestHeader("Authorization") String auth, @RequestParam(value = "urgente", required = false, defaultValue = "false") boolean urgente){
        String token = auth.replace("Bearer ", "");
        serviceToken.validarToken(token);
        return service.lerTodos(token, urgente);   
  
    }
    
    
    
    @GetMapping("/listar/{id}")
    public ResponseEntity<EditaisBean> getEdital(@RequestHeader("Authorization") String auth, @PathVariable Long id) {
        String token = auth.replace("Bearer ", "");
    EditaisBean edital = service.getById(token, id);
    return ResponseEntity.ok(edital);
    }
    
}
