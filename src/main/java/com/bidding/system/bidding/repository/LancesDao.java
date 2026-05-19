/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.LancesBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class LancesDao {
    public void adicionarLance(Long id, LancesBean lance, String token){
       try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("INSERT INTO lances (valor, data_lance, id_edital, id_usuario) VALUES (?, ?, ?, ?)");
            stmt.setDouble(1, lance.getValor());
            stmt.setDate(2, lance.getData_lance());
            stmt.setLong(3, lance.getId_edital());
            stmt.setLong(4, lance.getId_usuario());
            
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
       
    }
}
