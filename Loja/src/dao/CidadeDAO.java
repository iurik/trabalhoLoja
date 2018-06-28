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
import model.Cidade;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class CidadeDAO {

    public boolean adicionar(Cidade cidade) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO cidade (nome, cod_estado) VALUES (?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, cidade.getNome()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setInt(2, cidade.getCod_estado());
            
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Cidade cidade) {
        try {
            String sql = " UPDATE cidade "
                    + "    SET nome = ?, cod_estado = ? "
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, cidade.getNome());
            pstmt.setInt(2, cidade.getCod_estado());
            pstmt.setInt(3, cidade.getCodigo());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Cidade cidade) {
        try {
            String sql = " DELETE FROM cidade WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, cidade.getCodigo()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Cidade> selecionar() {
        String sql = "SELECT codigo, nome, cod_estado FROM cidade ORDER BY nome"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Cidade> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Cidade cidade = new Cidade(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                cidade.setCodigo(rs.getInt("codigo")); //alterar
                cidade.setNome(rs.getString("nome"));  //alterar
                cidade.setCod_estado(rs.getInt("cod_estado"));

                lista.add(cidade);
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
        Cidade cidade = new Cidade(); //alterar
        cidade.setNome("Selbach"); //alterar
        cidade.setCod_estado(1);
        
        CidadeDAO dao = new CidadeDAO(); //alterar
        dao.adicionar(cidade); //alterar
    }
}
