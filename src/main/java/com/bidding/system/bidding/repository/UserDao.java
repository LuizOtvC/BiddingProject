/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.UserBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class UserDao {
    
    public void register(UserBean user){
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("INSERT INTO usuarios (nome, email, senha, role) VALUES (?, ?, ?, ?)");
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, user.getRole());
            
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Falha na atualização: Nenhuma linha foi afetada.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public UserBean logar(String email, String senha){
        UserBean user = new UserBean();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = conn.prepareStatement("Select * from usuarios where email = ? and senha = ?");
            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if (rs.next()) {         
                user.setId(rs.getLong("id"));
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
            }
            
            
            
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }
}
