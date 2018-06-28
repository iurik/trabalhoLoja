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
import model.Pessoa;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class PessoaDAO {

    public boolean adicionar(Pessoa pessoa) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO pessoa (nome, telefone) VALUES (?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, pessoa.getNome()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setString(2, pessoa.getTelefone());    
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Pessoa pessoa) {
        try {
            String sql = " UPDATE pessoa "
                    + "    SET nome = ?, telefone = ? "
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, pessoa.getNome());
            pstmt.setString(1, pessoa.getTelefone());
            pstmt.setInt(2, pessoa.getCodigo());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Pessoa pessoa) {
        try {
            String sql = " DELETE FROM pessoa WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, pessoa.getCodigo()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Pessoa> selecionar() {
        String sql = "SELECT codigo, nome, telefone FROM pessoa ORDER BY nome"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Pessoa> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                pessoa.setCodigo(rs.getInt("codigo")); //alterar
                pessoa.setNome(rs.getString("nome"));  //alterar
                pessoa.setNome(rs.getString("telefone"));

                lista.add(pessoa);
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
        Pessoa pessoa = new Pessoa(); //alterar
        pessoa.setNome("Iuri"); //alterar
        pessoa.setTelefone("54991450891");

        PessoaDAO dao = new PessoaDAO(); //alterar
        dao.adicionar(pessoa); //alterar
    }
}
