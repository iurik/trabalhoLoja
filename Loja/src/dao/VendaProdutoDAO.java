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

    public boolean adicionar(VendaProduto vendaP) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO vendaP (quantidade, valorunitario, cod_produto, cod_venda) VALUES (?, ?, ?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setInt(1, vendaP.getQuantidade()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setDouble(2, vendaP.getValorUnitario());
            pstmt.setInt(3, vendaP.getCod_produto());
            pstmt.setInt(4, vendaP.getCod_venda());
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(VendaProduto vendaP) {
        try {
            String sql = " UPDATE vendaP "
                    + "    SET quantidade = ?, valorunitario = ?  "
                    + "  WHERE cod_produto = ?, cod_venda = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setInt(1, vendaP.getQuantidade());
            pstmt.setDouble(2, vendaP.getValorUnitario());
            pstmt.setInt(3, vendaP.getCod_produto());
            pstmt.setInt(4, vendaP.getCod_venda());
            
            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(VendaProduto vendaP) {
        try {
            String sql = " DELETE FROM vendaP WHERE cod_produto = ?, cod_venda = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, vendaP.getCod_produto()); //alterar conforme a chave primária
            pstmt.setInt(2, vendaP.getCod_venda());
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<VendaProduto> selecionar() {
        String sql = "SELECT quantidade, valorunitario, cod_produto, cod_venda FROM vendaP ORDER BY cod_venda"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<VendaProduto> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                VendaProduto vendaP = new VendaProduto(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                vendaP.setQuantidade(rs.getInt("quantidade"));
                vendaP.setValorUnitario(rs.getDouble("valorunitario"));//alterar
                vendaP.setCod_produto(rs.getInt("cod_produto"));
                vendaP.setCod_venda(rs.getInt("cod_venda"));  //alterar

                lista.add(vendaP);
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
        VendaProduto vendaP = new VendaProduto(); //alterar
        vendaP.setQuantidade(3); //alterar
        vendaP.setValorUnitario(359.50); 
        vendaP.setCod_produto(1);
        vendaP.setCod_venda(1); 

        VendaProdutoDAO dao = new VendaProdutoDAO(); //alterar
        dao.adicionar(vendaP); //alterar
    }
}
