/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.EditaisBean;
import com.bidding.system.bidding.model.LancesBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class LancesDao {
    public void adicionarLance(Long id, LancesBean lance){
       try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("INSERT INTO lances (valor, data_lance, id_edital, id_usuario) VALUES (?, ?, ?, ?)");
            stmt.setDouble(1, lance.getValor());
            stmt.setTimestamp(2, Timestamp.valueOf(lance.getData_lance()));
            stmt.setLong(3, id);
            stmt.setLong(4, lance.getId_usuario());
            
            
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
       
    }
    
    public java.util.List<com.bidding.system.bidding.model.MeuLanceBean> getMeusLances(Long id) {
        java.util.List<com.bidding.system.bidding.model.MeuLanceBean> lista = new java.util.ArrayList<>();
        try {
            Connection conn = Conexao.conectar(); 
            String sql = "SELECT l.id as id_lance, l.valor, l.data_lance, e.id as id_edital, e.titulo, e.status FROM lances l JOIN editais e ON l.id_edital = e.id WHERE l.id_usuario = ?  ORDER BY l.data_lance DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                com.bidding.system.bidding.model.MeuLanceBean dto = new com.bidding.system.bidding.model.MeuLanceBean();
                dto.setId(rs.getLong("id_lance"));
                dto.setValor(rs.getDouble("valor"));
                dto.setData_lance(rs.getTimestamp("data_lance").toLocalDateTime());
                dto.setId_edital(rs.getLong("id_edital"));
                dto.setTitulo(rs.getString("titulo"));
                dto.setStatus(rs.getString("status"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
     public Double getMenorLanceByEdital(Long idEdital) {
        Double menorValor = null; // começa como null; só é populado se existirem lances no edital
        try {
            Connection conn = Conexao.conectar(); // obtém a conexão ativa com o banco
            PreparedStatement stmt = conn.prepareStatement("SELECT MIN(valor) as menor_valor FROM lances WHERE id_edital = ?"); // MIN() retorna o menor valor de "valor" entre todos os lances do edital
            stmt.setLong(1, idEdital); // substitui o ? pelo id do edital
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) { // sempre haverá uma linha de retorno (com NULL se não houver lances)
                double val = rs.getDouble("menor_valor");
                if (!rs.wasNull()) { // rs.wasNull() verifica se o valor lido foi NULL no banco (edital sem lances); se não for null, atribui o valor
                    menorValor = val;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menorValor; // retorna o menor valor ou null se o edital não tiver lances
    }
}
