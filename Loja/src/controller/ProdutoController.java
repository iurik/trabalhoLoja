/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProdutoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Produto;
import view.ProdutoView;

/**
 *
 * @author edimar
 */
public class ProdutoController {

    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ProdutoDAO dao = new ProdutoDAO(); //alterar
            List<Produto> objetos = dao.selecionar(); //alterar
            Object colunas[] = new Object[9]; //alterar o índice de acordo com o número de campos exibidos 

            if (!objetos.isEmpty()) {
                for (Produto objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getCodigo();  //alterar
                    colunas[1] = objeto.getDescricao(); //alterar
                    colunas[2] = objeto.getPrateleira();
                    colunas[3] = objeto.getValorUnitario();
                    colunas[4] = objeto.getQuantidadeEstoque();
                    colunas[5] = objeto.getCusto();
                    colunas[6] = objeto.getNcm();
                    colunas[7] = objeto.getCod_tipo();
                    colunas[8] = objeto.getCod_fornecedor();
                    
                    model.addRow(colunas);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeLinhasTabela(JTable tabela) {
        try {
            DefaultTableModel dtm = (DefaultTableModel) tabela.getModel();
            dtm.setRowCount(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void atualizaCampos(ProdutoView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String codigo = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String descricao = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1
        String prateleira = tela.tabela.getValueAt(linhaSelecionada, 2).toString();
        String valorunitario = tela.tabela.getValueAt(linhaSelecionada, 3).toString();
        String quantidadeestoque = tela.tabela.getValueAt(linhaSelecionada, 4).toString();
        String custo = tela.tabela.getValueAt(linhaSelecionada, 5).toString();
        String ncm = tela.tabela.getValueAt(linhaSelecionada, 6).toString();
        String cod_tipo = tela.tabela.getValueAt(linhaSelecionada, 7).toString();
        String cod_fornecedor = tela.tabela.getValueAt(linhaSelecionada, 8).toString();
        //alterar setando os valores dos campos
        tela.jtfCodigo.setText(codigo);
        tela.jtfDescricao.setText(descricao);
        tela.jtfPrateleira.setText(prateleira);
        tela.jtfValorUnitario.setText(valorunitario);
        tela.jtfQuantidadeEstoque.setText(quantidadeestoque);
        tela.jtfCusto.setText(custo);
        tela.jtfNcm.setText(ncm);
        tela.jtfCodTipo.setText(cod_tipo);
        tela.jtfCodFornecedor.setText(cod_fornecedor);

        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(ProdutoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        String descricao = tela.jtfDescricao.getText().trim();
        String prateleira = tela.jtfPrateleira.getText().trim();
        Double valorunitario = Double.parseDouble(tela.jtfValorUnitario.getText().trim());
        Integer quantidadeestoque = Integer.parseInt(tela.jtfQuantidadeEstoque.getText().trim());
        Double custo = Double.parseDouble(tela.jtfCusto.getText().trim()); 
        String ncm = tela.jtfNcm.getText().trim();
        Integer cod_tipo = Integer.parseInt(tela.jtfCodTipo.getText().trim()); 
        Integer cod_fornecedor = Integer.parseInt(tela.jtfCodFornecedor.getText().trim()); 

        //alterar:: criando objeto
        Produto produto = new Produto();
        produto.setDescricao(descricao);
        produto.setPrateleira(prateleira);
        produto.setValorUnitario(valorunitario);
        produto.setQuantidadeEstoque(quantidadeestoque);
        produto.setCusto(custo);
        produto.setNcm(ncm);
        produto.setCod_tipo(cod_tipo);
        produto.setCod_fornecedor(cod_fornecedor);

        //alterar:: adicionando o objeto no banco de dados
        ProdutoDAO dao = new ProdutoDAO();
        boolean resultado = dao.adicionar(produto);
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Inserido com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a inserção!");
        }

    }

    public static void alterar(ProdutoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());
        String descricao = tela.jtfDescricao.getText().trim();
        String prateleira = tela.jtfPrateleira.getText().trim();
        Double valorunitario = Double.parseDouble(tela.jtfValorUnitario.getText().trim());
        Integer quantidadeestoque = Integer.parseInt(tela.jtfQuantidadeEstoque.getText().trim());
        Double custo = Double.parseDouble(tela.jtfCusto.getText().trim()); 
        String ncm = tela.jtfNcm.getText().trim();
        Integer cod_tipo = Integer.parseInt(tela.jtfCodTipo.getText().trim()); 
        Integer cod_fornecedor = Integer.parseInt(tela.jtfCodFornecedor.getText().trim()); 

        //alterar:: criando objeto
        Produto produto = new Produto();
        produto.setCodigo(codigo); //na alteração tem que setar o código
        produto.setDescricao(descricao);
        produto.setPrateleira(prateleira);
        produto.setValorUnitario(valorunitario);
        produto.setQuantidadeEstoque(quantidadeestoque);
        produto.setCusto(custo);
        produto.setNcm(ncm);
        produto.setCod_tipo(cod_tipo);
        produto.setCod_fornecedor(cod_fornecedor);

        //alterar:: alterando o objeto no banco de dados
        ProdutoDAO dao = new ProdutoDAO(); //alterar
        boolean resultado = dao.alterar(produto); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
    public static void excluir(ProdutoView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());

        //alterar:: criando objeto
        Produto produto = new Produto();
        produto.setCodigo(codigo); //na exclusão só precisa setar a chave primária

        //alterar:: excluindo o objeto no banco de dados
        ProdutoDAO dao = new ProdutoDAO(); //alterar
        boolean resultado = dao.excluir(produto); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Excluído com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a exclusão!");
        }
    }

    /**
     * Verifica se os campos estão preenchidos corretamente
     *
     * @param tela
     * @return true se todos os campos estão preenchidos corretamente, false se
     * algum campo não está preenchido corretamente
     */
    public static boolean verificarCampos(ProdutoView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfDescricao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo descricao!");
            return false;
        }
        if (tela.jtfPrateleira.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo prateleira!");
            return false;
        }
        if (tela.jtfValorUnitario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo valor unitario!");
            return false;
        }
        if (tela.jtfQuantidadeEstoque.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo quantidade estoque!");
            return false;
        }
        if (tela.jtfCusto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo custo!");
            return false;
        }
        if (tela.jtfNcm.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo ncm!");
            return false;
        }
        if (tela.jtfCodTipo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo código tipo!");
            return false;
        }
        if (tela.jtfCodFornecedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo código fornecedor!");
            return false;
        }
        return true;
    }

    /**
     * Deixa os campos em branco e habilita/desabilita os botões
     *
     * @param tela
     */
    public static void limparCampos(ProdutoView tela) {
        //alterar:: limpando os campos
        tela.jtfCodigo.setText("");
        tela.jtfDescricao.setText("");
        tela.jtfPrateleira.setText("");
        tela.jtfValorUnitario.setText("");
        tela.jtfQuantidadeEstoque.setText("");
        tela.jtfCusto.setText("");
        tela.jtfNcm.setText("");
        tela.jtfCodTipo.setText("");
        tela.jtfCodFornecedor.setText("");


        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(false);
        tela.jbtExcluir.setEnabled(false);
    }
}
