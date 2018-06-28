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
import model.Produto;
import config.Conexao;

/**
 *
 * @author iuri
 */
public class ProdutoDAO {

    public boolean adicionar(Produto produto) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO produto (prateleira, valorunitario, quantidadeestoque, descricao, custo, ncm, cod_tipo, cod_fornecedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, produto.getPrateleira()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setDouble(2, produto.getValorUnitario());
            pstmt.setInt(3, produto.getQuantidadeEstoque());
            pstmt.setString(4, produto.getDescricao());
            pstmt.setDouble(5, produto.getCusto());
            pstmt.setString(6, produto.getNcm());
            pstmt.setInt(7, produto.getCod_tipo());
            pstmt.setInt(8, produto.getCod_fornecedor());
            
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Produto produto) {
        try {
            String sql = " UPDATE produto "
                    + "    SET prateleira = ?, valorunitario = ?, quantidadeestoque = ?, descricao = ?, custo = ?, ncm = ?, cod_tipo = ?, cod_fornecedor = ? "
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, produto.getPrateleira());
            pstmt.setDouble(2, produto.getValorUnitario());
            pstmt.setInt(3, produto.getQuantidadeEstoque());
            pstmt.setString(4, produto.getDescricao());
            pstmt.setDouble(5, produto.getCusto());
            pstmt.setString(6, produto.getNcm());
            pstmt.setInt(7, produto.getCod_tipo());
            pstmt.setInt(8, produto.getCod_fornecedor());
            pstmt.setInt(9, produto.getCodigo());

            pstmt.executeUpdate(); //executando
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Produto produto) {
        try {
            String sql = " DELETE FROM produto WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, produto.getCodigo()); //alterar conforme a chave primária

            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Produto> selecionar() {
        String sql = "SELECT codigo, prateleira, valorunitario, quantidadeestoque, descricao, custo, ncm, cod_tipo, cod_fornecedor FROM produto ORDER BY descricao"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Produto> lista = new ArrayList<>(); //alterar a classe

            while (rs.next()) {
                Produto produto = new Produto(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                produto.setCodigo(rs.getInt("codigo")); //alterar
                produto.setPrateleira(rs.getString("nome"));  //alterar
                produto.setValorUnitario(rs.getDouble("valorunitario"));
                produto.setQuantidadeEstoque(rs.getInt("quantidadeestoque"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setCusto(rs.getDouble("custo"));
                produto.setNcm(rs.getString("ncm"));
                produto.setCod_tipo(rs.getInt("cod_tipo"));
                produto.setCod_fornecedor(rs.getInt("cod_fornecedor"));
                
                lista.add(produto);
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
        Produto produto = new Produto(); //alterar
        produto.setPrateleira("preteleira 2"); //alterar
        produto.setValorUnitario(69.00);
        produto.setQuantidadeEstoque(9);
        produto.setDescricao("Preto, G");
        produto.setCusto(40.90);
        produto.setNcm("95135764");
        produto.setCod_tipo(1);
        produto.setCod_fornecedor(1);
        

        ProdutoDAO dao = new ProdutoDAO(); //alterar
        dao.adicionar(produto); //alterar
    }
}
