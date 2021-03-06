/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ClienteDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import view.ClienteView;

/**
 *
 * @author edimar
 */
public class ClienteController {

    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ClienteDAO dao = new ClienteDAO(); //alterar
            List<Cliente> objetos = dao.selecionar(); //alterar
            Object colunas[] = new Object[2]; //alterar o índice de acordo com o número de campos exibidos 

            if (!objetos.isEmpty()) {
                for (Cliente objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getCod_pessoa();  //alterar
                    colunas[1] = objeto.getCpf(); //alterar
                    
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

    public static void atualizaCampos(ClienteView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String codPessoa = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String cpf = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1

        //alterar setando os valores dos campos
        tela.jtfCodPessoa.setText(codPessoa);
        tela.jtfCpf.setText(cpf);

        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(ClienteView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        Integer codPessoa = Integer.parseInt(tela.jtfCodPessoa.getText().trim());
        String cpf = tela.jtfCpf.getText().trim();

        //alterar:: criando objeto
        Cliente cliente = new Cliente();
        cliente.setCod_pessoa(codPessoa);
        cliente.setCpf(cpf);

        //alterar:: adicionando o objeto no banco de dados
        ClienteDAO dao = new ClienteDAO();
        boolean resultado = dao.adicionar(cliente);
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Inserido com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a inserção!");
        }

    }

    public static void alterar(ClienteView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codPessoa = Integer.parseInt(tela.jtfCodPessoa.getText().trim());
        String cpf = tela.jtfCpf.getText().trim();

        //alterar:: criando objeto
        Cliente cliente = new Cliente();
        cliente.setCod_pessoa(codPessoa); //na alteração tem que setar o código
        cliente.setCpf(cpf);

        //alterar:: alterando o objeto no banco de dados
        ClienteDAO dao = new ClienteDAO(); //alterar
        boolean resultado = dao.alterar(cliente); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
    public static void excluir(ClienteView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer codPessoa = Integer.parseInt(tela.jtfCodPessoa.getText().trim());

        //alterar:: criando objeto
        Cliente cliente = new Cliente();
        cliente.setCod_pessoa(codPessoa); //na exclusão só precisa setar a chave primária

        //alterar:: excluindo o objeto no banco de dados
        ClienteDAO dao = new ClienteDAO(); //alterar
        boolean resultado = dao.excluir(cliente); //alterar
        
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
    public static boolean verificarCampos(ClienteView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfCodPessoa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo código pessoa!");
            return false;
        }
         if (tela.jtfCpf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo cpf!");
            return false;
        }
        return true;
    }

    /**
     * Deixa os campos em branco e habilita/desabilita os botões
     *
     * @param tela
     */
    public static void limparCampos(ClienteView tela) {
        //alterar:: limpando os campos
        tela.jtfCodPessoa.setText("");
        tela.jtfCpf.setText("");

        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(false);
        tela.jbtExcluir.setEnabled(false);
    }
}
