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
import model.Endereco;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class EnderecoDAO {

    public boolean adicionar(Endereco endereco) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO endereco (numero, cep, bairro, complemento, logradouro, cod_cid, cod_pessoa) VALUES (?, ?, ?, ?, ?, ?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, endereco.getNumero()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setString(2, endereco.getCep());
            pstmt.setString(3, endereco.getBairro());
            pstmt.setString(4, endereco.getComplemento());
            pstmt.setString(5, endereco.getLogradouro());
            pstmt.setInt(6, endereco.getCod_cid());
            pstmt.setInt(7, endereco.getCod_pessoa());
            
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Endereco endereco) {
        try {
            String sql = " UPDATE endereco "
                    + "    SET numero = ?, cep = ?, bairro = ?, complemento = ?, logradouro = ?, cod_cid = ?, cod_pessoa = ? "
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, endereco.getNumero());
            pstmt.setString(2, endereco.getCep());
            pstmt.setString(3, endereco.getBairro());
            pstmt.setString(4, endereco.getComplemento());
            pstmt.setString(5, endereco.getLogradouro());
            pstmt.setInt(6, endereco.getCod_cid());
            pstmt.setInt(7, endereco.getCod_pessoa());
            pstmt.setInt(8, endereco.getCodigo());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Endereco endereco) {
        try {
            String sql = " DELETE FROM endereco WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, endereco.getCodigo()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Endereco> selecionar() {
        String sql = "SELECT codigo, numero, cep, bairro, complemento, logradouro, cod_cid, cod_pessoa FROM endereco ORDER BY cod_cid"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Endereco> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Endereco endereco = new Endereco(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                endereco.setCodigo(rs.getInt("codigo")); //alterar
                endereco.setNumero(rs.getString("numero"));  //alterar
                endereco.setCep(rs.getString("cep"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setCod_cid(rs.getInt("cod_cid"));
                endereco.setCod_pessoa(rs.getInt("cod_pessoa"));

                lista.add(endereco);
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
        Endereco endereco = new Endereco(); //alterar
        endereco.setNumero(null); //alterar
        endereco.setCep("99450000");
        endereco.setBairro("Linha Floresta");
        endereco.setComplemento(null);
        endereco.setLogradouro(null);
        endereco.setCod_pessoa(1);
        endereco.setCod_cid(1);
        
        EnderecoDAO dao = new EnderecoDAO(); //alterar
        dao.adicionar(endereco); //alterar
    }
}
