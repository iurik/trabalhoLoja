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
import model.Fornecedor;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class FornecedorDAO {

    public boolean adicionar(Fornecedor fornecedor) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO fornecedor (cnpj, cod_pessoa) VALUES (?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, fornecedor.getCnpj()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setInt(2, fornecedor.getCod_pessoa());
            
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Fornecedor fornecedor) {
        try {
            String sql = " UPDATE fornecedor"
                    + "    SET cnpj = ? "
                    + "  WHERE cod_pessoa = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, fornecedor.getCnpj());
            pstmt.setInt(2, fornecedor.getCod_pessoa());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Fornecedor fornecedor) {
        try {
            String sql = " DELETE FROM fornecedor WHERE cod_pessoa = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, fornecedor.getCod_pessoa()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Fornecedor> selecionar() {
        String sql = "SELECT cod_pessoa, cnpj FROM fornecedor ORDER BY cod_pessoa"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Fornecedor> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                fornecedor.setCod_pessoa(rs.getInt("cod_pessoa")); //alterar
                fornecedor.setCnpj(rs.getString("cnpj"));  //alterar

                lista.add(fornecedor);
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
        Fornecedor fornecedor = new Fornecedor(); //alterar
                
        FornecedorDAO dao = new FornecedorDAO(); //alterar
        dao.adicionar(fornecedor); //alterar
    }
}
