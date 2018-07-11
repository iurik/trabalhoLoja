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
import model.Estado;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class EstadoDAO {

    public boolean adicionar(Estado estado) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO estado (nome, sigla, cod_pais) VALUES (?, ?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, estado.getNome()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setString(2, estado.getSigla());
            pstmt.setInt(3, estado.getCod_pais());
            
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Estado estado) {
        try {
            String sql = " UPDATE estado "
                    + "    SET nome = ?, sigla = ?, cod_pais = ? "
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, estado.getNome());
            pstmt.setString(2, estado.getSigla());
            pstmt.setInt(3, estado.getCod_pais());
            pstmt.setInt(4, estado.getCodigo());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Estado estado) {
        try {
            String sql = " DELETE FROM estado WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, estado.getCodigo()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Estado> selecionar() {
        String sql = "SELECT codigo, nome, sigla, cod_pais FROM estado ORDER BY nome"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Estado> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Estado estado = new Estado(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                estado.setCodigo(rs.getInt("codigo")); //alterar
                estado.setNome(rs.getString("nome"));
                estado.setSigla(rs.getString("sigla"));
                estado.setCod_pais(rs.getInt("cod_pais"));  //alterar

                lista.add(estado);
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
        Estado estado = new Estado(); //alterar
                
        EstadoDAO dao = new EstadoDAO(); //alterar
        dao.adicionar(estado); //alterar
    }
}
