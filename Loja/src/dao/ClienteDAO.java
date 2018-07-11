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
import model.Cliente;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class ClienteDAO {

    public boolean adicionar(Cliente cliente) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO cliente (cpf, cod_pessoa) VALUES (?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, cliente.getCpf()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setInt(2, cliente.getCod_pessoa());
            
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Cliente cliente) {
        try {
            String sql = " UPDATE cliente "
                    + "    SET cpf = ? "
                    + "  WHERE cod_pessoa = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, cliente.getCpf());
            pstmt.setInt(2, cliente.getCod_pessoa());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Cliente cliente) {
        try {
            String sql = " DELETE FROM cliente WHERE cod_pessoa = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, cliente.getCod_pessoa()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Cliente> selecionar() {
        String sql = "SELECT cod_pessoa, cpf FROM cliente ORDER BY cod_pessoa"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Cliente> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Cliente cliente = new Cliente(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                cliente.setCod_pessoa(rs.getInt("cod_pessoa")); //alterar
                cliente.setCpf(rs.getString("cpf"));  //alterar

                lista.add(cliente);
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
        Cliente cliente = new Cliente(); //alterar
                
        ClienteDAO dao = new ClienteDAO(); //alterar
        dao.adicionar(cliente); //alterar
    }
}
