/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EnderecoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Endereco;
import view.EnderecoView;

/**
 *
 * @author edimar
 */
public class EnderecoController {

    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            EnderecoDAO dao = new EnderecoDAO(); //alterar
            List<Endereco> objetos = dao.selecionar(); //alterar
            Object colunas[] = new Object[8]; //alterar o índice de acordo com o número de campos exibidos 

            if (!objetos.isEmpty()) {
                for (Endereco objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getCodigo();  //alterar
                    colunas[1] = objeto.getNumero(); //alterar
                    colunas[2] = objeto.getCep();
                    colunas[3] = objeto.getBairro();
                    colunas[4] = objeto.getComplemento();
                    colunas[5] = objeto.getLogradouro();
                    colunas[6] = objeto.getCod_cid();
                    colunas[7] = objeto.getCod_pessoa();
                    
                    
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

    public static void atualizaCampos(EnderecoView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String codigo = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String numero = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1
        String cep = tela.tabela.getValueAt(linhaSelecionada, 2).toString();
        String bairro = tela.tabela.getValueAt(linhaSelecionada, 3).toString();
        String complemento = tela.tabela.getValueAt(linhaSelecionada, 4).toString();
        String logradouro = tela.tabela.getValueAt(linhaSelecionada, 5).toString();
        String cod_cidade  = tela.tabela.getValueAt(linhaSelecionada, 6).toString();
        String cod_pessoa = tela.tabela.getValueAt(linhaSelecionada, 7).toString();
        
        //alterar setando os valores dos campos
        tela.jtfCodigo.setText(codigo);
        tela.jtfNumero.setText(numero);
        tela.jtfCep.setText(cep);
        tela.jtfBairro.setText(bairro);
        tela.jtfComplemento.setText(complemento);
        tela.jtfLogradouro.setText(logradouro);
        tela.jtfCodCidade.setText(cod_cidade);
        tela.jtfCodPessoa.setText(cod_pessoa);    
        
        
        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(EnderecoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        String numero = tela.jtfNumero.getText().trim();
        String cep = tela.jtfCep.getText().trim();
        String bairro = tela.jtfBairro.getText().trim();
        String complemento = tela.jtfComplemento.getText().trim();
        String logradouro = tela.jtfLogradouro.getText().trim();
        Integer cod_cidade = Integer.parseInt(tela.jtfCodCidade.getText().trim());
        Integer cod_pessoa = Integer.parseInt(tela.jtfCodPessoa.getText().trim());
        

        //alterar:: criando objeto
        Endereco endereco = new Endereco();
        endereco.setNumero(numero);
        endereco.setCep(cep);
        endereco.setBairro(bairro);
        endereco.setComplemento(complemento);
        endereco.setLogradouro(logradouro);
        endereco.setCod_cid(cod_cidade);
        endereco.setCod_pessoa(cod_pessoa);

        //alterar:: adicionando o objeto no banco de dados
        EnderecoDAO dao = new EnderecoDAO();
        boolean resultado = dao.adicionar(endereco);
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Inserido com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a inserção!");
        }

    }

    public static void alterar(EnderecoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());
        String numero = tela.jtfNumero.getText().trim();
        String cep = tela.jtfCep.getText().trim();
        String bairro = tela.jtfBairro.getText().trim();
        String complemento = tela.jtfComplemento.getText().trim();
        String logradouro = tela.jtfLogradouro.getText().trim();
        Integer cod_cidade = Integer.parseInt(tela.jtfCodCidade.getText().trim());
        Integer cod_pessoa = Integer.parseInt(tela.jtfCodPessoa.getText().trim());

        //alterar:: criando objeto
        Endereco endereco = new Endereco();
        endereco.setCodigo(codigo); //na alteração tem que setar o código
        endereco.setNumero(numero);
        endereco.setCep(cep);
        endereco.setBairro(bairro);
        endereco.setComplemento(complemento);
        endereco.setLogradouro(logradouro);
        endereco.setCod_cid(cod_cidade);
        endereco.setCod_pessoa(cod_pessoa);

        //alterar:: alterando o objeto no banco de dados
        EnderecoDAO dao = new EnderecoDAO(); //alterar
        boolean resultado = dao.alterar(endereco); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
    public static void excluir(EnderecoView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());

        //alterar:: criando objeto
        Endereco endereco = new Endereco();
        endereco.setCodigo(codigo); //na exclusão só precisa setar a chave primária

        //alterar:: excluindo o objeto no banco de dados
        EnderecoDAO dao = new EnderecoDAO(); //alterar
        boolean resultado = dao.excluir(endereco); //alterar
        
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
    public static boolean verificarCampos(EnderecoView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfCep.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo cep!");
            return false;
        }
        if (tela.jtfCodCidade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo código cidade!");
            return false;
        }
        if (tela.jtfCodPessoa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha o campo código pessoa!");
            return false;
        }
        return true;
    }

    /**
     * Deixa os campos em branco e habilita/desabilita os botões
     *
     * @param tela
     */
    public static void limparCampos(EnderecoView tela) {
        //alterar:: limpando os campos
        tela.jtfCodigo.setText("");
        tela.jtfBairro.setText("");
        tela.jtfCep.setText("");
        tela.jtfBairro.setText("");
        tela.jtfComplemento.setText("");
        tela.jtfLogradouro.setText("");
        tela.jtfCodCidade.setText("");
        tela.jtfCodPessoa.setText("");

        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(false);
        tela.jbtExcluir.setEnabled(false);
    }
}
