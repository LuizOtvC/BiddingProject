/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.EditaisBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class EditaisDao {
   
    public int adicionarCondicao(EditaisBean edita){
       try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("INSERT INTO editais (titulo, descricao, data_fechamento, status) VALUES (?, ?, ?, ?)");
            stmt.setString(1, edita.getTitulo());
            stmt.setString(2, edita.getDescricao());
            stmt.setDate(3, edita.getData_fechamento());
            stmt.setString(4, edita.getStatus());
            
            return stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        } 
       return 0;
    }
    
    public List<EditaisBean> lerTodos(){
        List<EditaisBean> dados = new ArrayList();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("SELECT * FROM editais");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EditaisBean editais = new EditaisBean();
                editais.setId(rs.getLong("id"));
                editais.setTitulo(rs.getString("titulo"));
                editais.setDescricao(rs.getString("descricao"));
                editais.setData_fechamento(rs.getDate("data_fechamento"));
                editais.setStatus(rs.getString("status"));
                
                dados.add(editais);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
         return dados;   
    }
    
    public EditaisBean getById(Long id){
        EditaisBean edital = new EditaisBean();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("SELECT data_fechamento, status FROM editais where id = ?");
            stmt.setLong(1, id);
            
            rs = stmt.executeQuery();
            if(rs.next()){
                edital.setId(rs.getLong("id"));
                edital.setTitulo(rs.getString("titulo"));
                edital.setDescricao(rs.getString("descricao"));
                edital.setData_fechamento(rs.getDate("data_fechamento"));
                edital.setStatus(rs.getString("status"));
                
                
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return edital;
    }
}
