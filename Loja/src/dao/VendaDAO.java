/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Venda;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class VendaDAO {

    public boolean adicionar(Venda venda) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO venda (valorTotal, datahora, cod_cliente) VALUES (?, ?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setDouble(1, venda.getValorTotal()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setDate(2, new Date (new java.util.Date().getTime()));
            pstmt.setInt(3, venda.getCod_cliente());
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Venda venda) {
        try {
            String sql = " UPDATE venda "
                    + "    SET valortotal = ?, cod_cliente = ? "
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setDouble(1, venda.getValorTotal());
            pstmt.setInt(2, venda.getCod_cliente());
            pstmt.setInt(3, venda.getCodigo());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Venda venda) {
        try {
            String sql = " DELETE FROM venda WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, venda.getCodigo()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Venda> selecionar() {
        String sql = "SELECT codigo, valortotal, datahora, cod_cliente FROM venda ORDER BY codigo"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Venda> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Venda venda = new Venda(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                venda.setCodigo(rs.getInt("codigo")); //alterar
                venda.setValorTotal(rs.getDouble("valortotal"));  //alterar
                venda.setDatahora(rs.getDate("datahora"));
                venda.setCod_cliente(rs.getInt("cod_cliente"));

                lista.add(venda);
            }
            stmt.close();
            return lista;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //método só para testar
    public static void main(String[] args) {
        Venda venda = new Venda(); //alterar
        venda.setValorTotal(850.00);
        venda.setCod_cliente(1);
                     
        VendaDAO dao = new VendaDAO(); //alterar
        dao.adicionar(venda); //alterar
    }
}
