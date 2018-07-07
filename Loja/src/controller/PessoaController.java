/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PessoaDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Pessoa;
import view.PessoaView;

/**
 *
 * @author edimar
 */
public class PessoaController {

    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            PessoaDAO dao = new PessoaDAO(); //alterar
            List<Pessoa> objetos = dao.selecionar(); //alterar
            Object colunas[] = new Object[3]; //alterar o índice de acordo com o número de campos exibidos 

            if (!objetos.isEmpty()) {
                for (Pessoa objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getCodigo();  //alterar
                    colunas[1] = objeto.getNome(); //alterar
                    colunas[2] = objeto.getTelefone();
                    
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

    public static void atualizaCampos(PessoaView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String codigo = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String nome = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1
        String telefone = tela.tabela.getValueAt(linhaSelecionada, 2).toString();

        //alterar setando os valores dos campos
        tela.jtfCodigo.setText(codigo);
        tela.jtfNome.setText(nome);
        tela.jtfTelefone.setText(telefone);

        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(PessoaView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        String nome = tela.jtfNome.getText().trim();
        String telefone = tela.jtfTelefone.getText().trim();

        //alterar:: criando objeto
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setTelefone(telefone);

        //alterar:: adicionando o objeto no banco de dados
        PessoaDAO dao = new PessoaDAO();
        boolean resultado = dao.adicionar(pessoa);
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Inserido com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a inserção!");
        }

    }

    public static void alterar(PessoaView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());
        String nome = tela.jtfNome.getText().trim();
        String telefone = tela.jtfTelefone.getText().trim();

        //alterar:: criando objeto
        Pessoa pessoa = new Pessoa();
        pessoa.setCodigo(codigo); //na alteração tem que setar o código
        pessoa.setNome(nome);
        pessoa.setTelefone(telefone);

        //alterar:: alterando o objeto no banco de dados
        PessoaDAO dao = new PessoaDAO(); //alterar
        boolean resultado = dao.alterar(pessoa); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
    public static void excluir(PessoaView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());

        //alterar:: criando objeto
        Pessoa pessoa = new Pessoa();
        pessoa.setCodigo(codigo); //na exclusão só precisa setar a chave primária

        //alterar:: excluindo o objeto no banco de dados
        PessoaDAO dao = new PessoaDAO(); //alterar
        boolean resultado = dao.excluir(pessoa); //alterar
        
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
    public static boolean verificarCampos(PessoaView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo nome!");
            return false;
        }
        return true;
    }

    /**
     * Deixa os campos em branco e habilita/desabilita os botões
     *
     * @param tela
     */
    public static void limparCampos(PessoaView tela) {
        //alterar:: limpando os campos
        tela.jtfCodigo.setText("");
        tela.jtfNome.setText("");
        tela.jtfTelefone.setText("");
        
        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(false);
        tela.jbtExcluir.setEnabled(false);
    }
}
