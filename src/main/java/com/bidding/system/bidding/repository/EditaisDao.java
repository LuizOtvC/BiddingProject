/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.EditaisBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class EditaisDao {
   
    public void adicionarCondicao(EditaisBean edita){
       try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("INSERT INTO editais (titulo, descricao, data_fechamento, status) VALUES (?, ?, ?, ?)");
            stmt.setString(1, edita.getTitulo());
            stmt.setString(2, edita.getDescricao());
            stmt.setDate(3, edita.getData_fechamento());
            stmt.setString(4, edita.getStatus());
            
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Falha na atualização: Nenhuma linha foi afetada.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
}
