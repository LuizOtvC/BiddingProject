/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.Controller;

import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.model.UserLogarBean;
import com.bidding.system.bidding.repository.UserDao;
import com.bidding.system.bidding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aluno
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {
    
    @Autowired
    private UserService service;
    
    @PostMapping("/registrar")
    public String Registrar(@RequestBody UserBean user){
        service.registrar(user);
        return "cadastro realizado com sucesso";
    }
    
    @PostMapping("/logar")
    public String Logar(@RequestBody UserLogarBean user){
        service.logar(user);
        return "login sucedido";
    }
    
}
