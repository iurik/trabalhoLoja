/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EstadoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Estado;
import model.Pais;
import view.EstadoView;

/**
 *
 * @author edimar
 */
public class EstadoController {

    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            EstadoDAO dao = new EstadoDAO(); //alterar
            List<Estado> objetos = dao.selecionar(); //alterar
            Object colunas[] = new Object[4]; //alterar o índice de acordo com o número de campos exibidos 

            if (!objetos.isEmpty()) {
                for (Estado objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getCodigo();  //alterar
                    colunas[1] = objeto.getNome(); //alterar
                    colunas[2] = objeto.getSigla();
                    colunas[3] = objeto.getCod_pais(); 
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

    public static void atualizaCampos(EstadoView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String codigo = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String nome = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1
        String sigla = tela.tabela.getValueAt(linhaSelecionada, 2).toString();
        String cod_pais  = tela.tabela.getValueAt(linhaSelecionada, 3).toString();

        //alterar setando os valores dos campos
        tela.jtfCodigo.setText(codigo);
        tela.jtfNome.setText(nome);
        tela.jtfSigla.setText(sigla);
        tela.jcbCodPais.setSelectedItem(cod_pais);

        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(EstadoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        String nome = tela.jtfNome.getText().trim();
        String sigla = tela.jtfSigla.getText().trim();
        Integer cod_pais = ((Pais)tela.jcbCodPais.getSelectedItem()).getCodigo();

        //alterar:: criando objeto
        Estado estado = new Estado();
        estado.setNome(nome);
        estado.setSigla(sigla);
        estado.setCod_pais(cod_pais);

        //alterar:: adicionando o objeto no banco de dados
        EstadoDAO dao = new EstadoDAO();
        boolean resultado = dao.adicionar(estado);
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Inserido com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a inserção!");
        }

    }

    public static void alterar(EstadoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());
        String nome = tela.jtfNome.getText().trim();
        String sigla = tela.jtfSigla.getText().trim();
        Integer cod_pais = ((Pais)tela.jcbCodPais.getSelectedItem()).getCodigo();

        //alterar:: criando objeto
        Estado estado = new Estado();
        estado.setCodigo(codigo); //na alteração tem que setar o código
        estado.setNome(nome);
        estado.setSigla(sigla);
        estado.setCod_pais(cod_pais);
        
        //alterar:: alterando o objeto no banco de dados
        EstadoDAO dao = new EstadoDAO(); //alterar
        boolean resultado = dao.alterar(estado); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
    public static void excluir(EstadoView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());

        //alterar:: criando objeto
        Estado estado = new Estado();
        estado.setCodigo(codigo); //na exclusão só precisa setar a chave primária

        //alterar:: excluindo o objeto no banco de dados
        EstadoDAO dao = new EstadoDAO(); //alterar
        boolean resultado = dao.excluir(estado); //alterar
        
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
    public static boolean verificarCampos(EstadoView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo nome!");
            return false;
        }
        if (tela.jtfSigla.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo sigla!");
            return false;
        }
        /*if (tela.jcbCodPais.getSelectedItem()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo código país!");
            return false;
        }*/
        return true;
    }

    /**
     * Deixa os campos em branco e habilita/desabilita os botões
     *
     * @param tela
     */
    public static void limparCampos(EstadoView tela) {
        //alterar:: limpando os campos
        tela.jtfCodigo.setText("");
        tela.jtfNome.setText("");
        tela.jtfSigla.setText("");
        tela.jcbCodPais.setSelectedItem("");

        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(false);
        tela.jbtExcluir.setEnabled(false);
    }
}
