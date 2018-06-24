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
import model.Tipo;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class TipoDAO {

    public boolean adicionar(Tipo tipo) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO tipo (descricao) VALUES (?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, tipo.getDescricao()); // alterar o primeiro parâmetro indica a interrogação, começando em 1

            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Tipo tipo) {
        try {
            String sql = " UPDATE tipo "
                    + "    SET descricao = ? "
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, tipo.getDescricao());
            pstmt.setInt(2, tipo.getCodigo());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Tipo tipo) {
        try {
            String sql = " DELETE FROM tipo WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, tipo.getCodigo()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Tipo> selecionar() {
        String sql = "SELECT codigo, descricao FROM tipo ORDER BY descricao"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Tipo> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Tipo tipo = new Tipo(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                tipo.setCodigo(rs.getInt("codigo")); //alterar
                tipo.setDescricao(rs.getString("descricao"));  //alterar

                lista.add(tipo);
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
        Tipo tipo = new Tipo(); //alterar
        tipo.setDescricao("Calça"); //alterar

        TipoDAO dao = new TipoDAO(); //alterar
        dao.adicionar(tipo); //alterar
    }
}
