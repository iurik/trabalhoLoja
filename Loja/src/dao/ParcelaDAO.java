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
import model.Parcela;
import config.Conexao;
import java.sql.Date;

/**
 *
 * @author iuri
 */
public class ParcelaDAO {

    public boolean adicionar(Parcela parcela) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO parcela (valor, datavencimento, datapagamento, cod_venda) VALUES (?, ?, ?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setDouble(1, parcela.getValor()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setDate(2, new Date(parcela.getDataVencimento().getTime()));
            pstmt.setDate(3, new Date(parcela.getDataPagamento().getTime()));
            pstmt.setInt(4, parcela.getCod_venda());
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Parcela parcela) {
        try {
            String sql = " UPDATE parcela "
                    + "    SET valor = ?,  datavencimento = ?, datapagamento = ?, cod_venda = ? "
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setDouble(1, parcela.getValor()); 
            pstmt.setDate(2, new Date(parcela.getDataVencimento().getTime()));
            pstmt.setDate(3, new Date(parcela.getDataPagamento().getTime()));
            pstmt.setInt(4, parcela.getCod_venda());
            pstmt.setInt(5, parcela.getCodigo());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Parcela parcela) {
        try {
            String sql = " DELETE FROM parcela WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, parcela.getCodigo()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Parcela> selecionar() {
        String sql = "SELECT codigo, valor, datavencimento, datapagamento, cod_venda FROM parcela ORDER BY datavencimento"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Parcela> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Parcela parcela = new Parcela(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                parcela.setCodigo(rs.getInt("codigo")); //alterar
                parcela.setValor(rs.getDouble("valor"));  //alterar
                parcela.setDataVencimento(rs.getDate("datavencimento"));
                parcela.setDataPagamento(rs.getDate("datapagamento"));
                parcela.setCod_venda(rs.getInt("cod_venda"));
                
                lista.add(parcela);
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
        Parcela parcela = new Parcela(); //alterar
                
        ParcelaDAO dao = new ParcelaDAO(); //alterar
        dao.adicionar(parcela); //alterar
    }
}
