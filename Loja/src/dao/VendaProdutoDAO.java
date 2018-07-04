/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.VendaProduto;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class VendaProdutoDAO {

    public boolean adicionar(VendaProduto vendaproduto) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO vendaproduto (quantidade, valorunitario, cod_produto, cod_venda) VALUES (?, ?, ?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setInt(1, vendaproduto.getQuantidade()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setDouble(2, vendaproduto.getValorUnitario());
            pstmt.setInt(3, vendaproduto.getCod_produto());
            pstmt.setInt(4, vendaproduto.getCod_venda());
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(VendaProduto vendaproduto) {
        try {
            String sql = " UPDATE vendaproduto "
                    + "    SET quantidade = ?, valorunitario = ?  "
                    + "  WHERE cod_produto = ?, cod_venda = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setInt(1, vendaproduto.getQuantidade());
            pstmt.setDouble(2, vendaproduto.getValorUnitario());
            pstmt.setInt(3, vendaproduto.getCod_produto());
            pstmt.setInt(4, vendaproduto.getCod_venda());
            
            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(VendaProduto vendaproduto) {
        try {
            String sql = " DELETE FROM vendaproduto WHERE cod_produto = ?, cod_venda = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, vendaproduto.getCod_produto()); //alterar conforme a chave primária
            pstmt.setInt(2, vendaproduto.getCod_venda());
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<VendaProduto> selecionar() {
        String sql = "SELECT quantidade, valorunitario, cod_produto, cod_venda FROM vendaproduto ORDER BY cod_venda"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<VendaProduto> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                VendaProduto vendaproduto = new VendaProduto(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                vendaproduto.setQuantidade(rs.getInt("quantidade"));
                vendaproduto.setValorUnitario(rs.getDouble("valorunitario"));//alterar
                vendaproduto.setCod_produto(rs.getInt("cod_produto"));
                vendaproduto.setCod_venda(rs.getInt("cod_venda"));  //alterar

                lista.add(vendaproduto);
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
        VendaProduto vendaproduto = new VendaProduto(); //alterar
        vendaproduto.setQuantidade(3); //alterar
        vendaproduto.setValorUnitario(359.50); 
        vendaproduto.setCod_produto(1);
        vendaproduto.setCod_venda(1); 

        VendaProdutoDAO dao = new VendaProdutoDAO(); //alterar
        dao.adicionar(vendaproduto); //alterar
    }
}
