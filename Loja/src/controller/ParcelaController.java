/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ParcelaDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Parcela;
import view.ParcelaView;

/**
 *
 * @author edimar
 */
public class ParcelaController {

    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ParcelaDAO dao = new ParcelaDAO(); //alterar
            List<Parcela> objetos = dao.selecionar(); //alterar
            Object colunas[] = new Object[5]; //alterar o índice de acordo com o número de campos exibidos 

            if (!objetos.isEmpty()) {
                for (Parcela objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getCodigo();  //alterar
                    colunas[1] = objeto.getValor(); //alterar
                    colunas[2] = objeto.getDataPagamento();
                    colunas[3] = objeto.getDataVencimento();
                    colunas[4] = objeto.getCod_venda();
                    
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

    public static void atualizaCampos(ParcelaView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String codigo = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String valor = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1
        String datapagamento = tela.tabela.getValueAt(linhaSelecionada, 2).toString();
        String datavencimento = tela.tabela.getValueAt(linhaSelecionada, 3).toString();
        String cod_venda = tela.tabela.getValueAt(linhaSelecionada, 4).toString();
        
        //alterar setando os valores dos campos
        tela.jtfCodigo.setText(codigo);
        tela.jtfValor.setText(valor);
        tela.jtfDataPagamento.setText(datapagamento);
        tela.jtfDataVencimento.setText(datavencimento);
        tela.jtfCodVenda.setText(cod_venda);

        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(ParcelaView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        Double valor = Double.parseDouble(tela.jtfValor.getText().trim()); 
        String datapagamento = tela.jtfDataPagamento.getText().trim();
        String datavencimento = tela.jtfDataVencimento.getText().trim();
        Integer cod_venda = Integer.parseInt(tela.jtfCodVenda.getText().trim()); 


        //alterar:: criando objeto
        Parcela parcela = new Parcela();
        parcela.setValor(valor);
        parcela.setDataPagamento(datapagamento);
        parcela.setDataVencimento(datavencimento);
        parcela.setCod_venda(cod_venda);

        //alterar:: adicionando o objeto no banco de dados
        ParcelaDAO dao = new ParcelaDAO();
        boolean resultado = dao.adicionar(parcela);
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Inserido com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a inserção!");
        }

    }

    public static void alterar(ParcelaView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());
        Double valor = Double.parseDouble(tela.jtfValor.getText().trim());
        String datapagamento = tela.jtfDataPagamento.getText().trim();
        String datavencimento = tela.jtfDataVencimento.getText().trim();
        Integer cod_venda = Integer.parseInt(tela.jtfCodVenda.getText().trim());

        //alterar:: criando objeto
        Parcela parcela = new Parcela();
        parcela.setCodigo(codigo); //na alteração tem que setar o código
        parcela.setValor(valor);
        parcela.setDataPagamento(datapagamento);
        parcela.setDataVencimento(datavencimento);
        parcela.setCod_venda(cod_venda);
        
        //alterar:: alterando o objeto no banco de dados
        ParcelaDAO dao = new ParcelaDAO(); //alterar
        boolean resultado = dao.alterar(parcela); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
    public static void excluir(ParcelaView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());

        //alterar:: criando objeto
        Parcela parcela = new Parcela();
        parcela.setCodigo(codigo); //na exclusão só precisa setar a chave primária

        //alterar:: excluindo o objeto no banco de dados
        ParcelaDAO dao = new ParcelaDAO(); //alterar
        boolean resultado = dao.excluir(parcela); //alterar
        
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
    public static boolean verificarCampos(ParcelaView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfValor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo valor!");
            return false;
        }
        if (tela.jtfDataPagamento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo data pagamento!");
            return false;
        }
        if (tela.jtfDataVencimento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo data vencimento!");
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
    public static void limparCampos(ParcelaView tela) {
        //alterar:: limpando os campos
        tela.jtfCodigo.setText("");
        tela.jtfValor.setText("");
        tela.jtfDataPagamento.setText("");
        tela.jtfDataVencimento.setText("");
        tela.jtfCodVenda.setText("");

        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(false);
        tela.jbtExcluir.setEnabled(false);
    }
}
