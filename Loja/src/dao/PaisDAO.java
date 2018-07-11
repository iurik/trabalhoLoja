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
import model.Pais;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class PaisDAO {

    public boolean adicionar(Pais pais) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO pais (nome, sigla) VALUES (?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, pais.getNome()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setString(2, pais.getSigla());
            
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Pais pais) {
        try {
            String sql = " UPDATE pais "
                    + "    SET nome = ?, sigla = ? "
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, pais.getNome());
            pstmt.setString(2, pais.getSigla());
            pstmt.setInt(3, pais.getCodigo());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Pais pais) {
        try {
            String sql = " DELETE FROM pais WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, pais.getCodigo()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Pais> selecionar() {
        String sql = "SELECT codigo, nome, sigla FROM pais ORDER BY nome"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Pais> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Pais pais = new Pais(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                pais.setCodigo(rs.getInt("codigo")); //alterar
                pais.setNome(rs.getString("nome"));  //alterar
                pais.setSigla(rs.getString("sigla"));

                lista.add(pais);
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
        Pais pais = new Pais(); //alterar
                
        PaisDAO dao = new PaisDAO(); //alterar
        dao.adicionar(pais); //alterar
    }
}
