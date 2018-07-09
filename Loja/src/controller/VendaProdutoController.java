/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.VendaProdutoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.VendaProduto;
import view.VendaProdutoView;

/**
 *
 * @author edimar
 */
public class VendaProdutoController {

    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            VendaProdutoDAO dao = new VendaProdutoDAO(); //alterar
            List<VendaProduto> objetos = dao.selecionar(); //alterar
            Object colunas[] = new Object[4]; //alterar o índice de acordo com o número de campos exibidos 

            if (!objetos.isEmpty()) {
                for (VendaProduto objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getQuantidade();  //alterar
                    colunas[1] = objeto.getValorUnitario(); //alterar
                    colunas[2] = objeto.getCod_produto();
                    colunas[3] = objeto.getCod_venda();
                    
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

    public static void atualizaCampos(VendaProdutoView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String quantidade = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String valorunitario = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1
        String cod_produto = tela.tabela.getValueAt(linhaSelecionada, 2).toString();
        String cod_venda = tela.tabela.getValueAt(linhaSelecionada, 3).toString();
        //alterar setando os valores dos campos
        tela.jtfQuantidade.setText(quantidade);
        tela.jtfValorUnitario.setText(valorunitario);
        tela.jtfCodProduto.setText(cod_produto);
        tela.jtfCodVenda.setText(cod_venda);

        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(VendaProdutoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        Integer quantidade = Integer.parseInt(tela.jtfQuantidade.getText().trim());
        Double valorunitario = Double.parseDouble(tela.jtfValorUnitario.getText().trim());
        Integer cod_produto = Integer.parseInt(tela.jtfCodProduto.getText().trim());
        Integer cod_venda = Integer.parseInt(tela.jtfCodVenda.getText().trim());


        //alterar:: criando objeto
        VendaProduto vendaproduto = new VendaProduto();
        vendaproduto.setQuantidade(quantidade);
        vendaproduto.setValorUnitario(valorunitario);
        vendaproduto.setCod_produto(cod_produto);
        vendaproduto.setCod_venda(cod_venda);

        //alterar:: adicionando o objeto no banco de dados
        VendaProdutoDAO dao = new VendaProdutoDAO();
        boolean resultado = dao.adicionar(vendaproduto);
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Inserido com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a inserção!");
        }

    }

    public static void alterar(VendaProdutoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer quantidade = Integer.parseInt(tela.jtfQuantidade.getText().trim());
        Double valorunitario = Double.parseDouble(tela.jtfValorUnitario.getText().trim());
        Integer cod_produto = Integer.parseInt(tela.jtfCodProduto.getText().trim());
        Integer cod_venda = Integer.parseInt(tela.jtfCodVenda.getText().trim());

        //alterar:: criando objeto
        VendaProduto vendaproduto = new VendaProduto();
        vendaproduto.setQuantidade(quantidade); //na alteração tem que setar o código
        vendaproduto.setValorUnitario(valorunitario);
        vendaproduto.setCod_produto(cod_produto);
        vendaproduto.setCod_venda(cod_venda);

        //alterar:: alterando o objeto no banco de dados
        VendaProdutoDAO dao = new VendaProdutoDAO(); //alterar
        boolean resultado = dao.alterar(vendaproduto); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
    public static void excluir(VendaProdutoView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer cod_produto = Integer.parseInt(tela.jtfCodProduto.getText().trim());
        Integer cod_venda = Integer.parseInt(tela.jtfCodVenda.getText().trim());
        
        //alterar:: criando objeto
        VendaProduto vendaproduto = new VendaProduto();
        vendaproduto.setCod_produto(cod_produto); //na exclusão só precisa setar a chave primária
        vendaproduto.setCod_venda(cod_venda);
        //alterar:: excluindo o objeto no banco de dados
        VendaProdutoDAO dao = new VendaProdutoDAO(); //alterar
        boolean resultado = dao.excluir(vendaproduto); //alterar
        
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
    public static boolean verificarCampos(VendaProdutoView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfQuantidade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo quantidade!");
            return false;
        }
        if (tela.jtfValorUnitario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo valor unitario!");
            return false;
        }
        if (tela.jtfCodProduto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo código produto!");
            return false;
        }
        if (tela.jtfCodVenda.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo código venda!");
            return false;
        }
        return true;
    }

    /**
     * Deixa os campos em branco e habilita/desabilita os botões
     *
     * @param tela
     */
    public static void limparCampos(VendaProdutoView tela) {
        //alterar:: limpando os campos
        tela.jtfQuantidade.setText("");
        tela.jtfValorUnitario.setText("");
        tela.jtfCodProduto.setText("");
        tela.jtfCodVenda.setText("");
        
        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(false);
        tela.jbtExcluir.setEnabled(false);
    }
}
