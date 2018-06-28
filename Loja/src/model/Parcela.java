/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author iuri
 */
public class Parcela {
    private int codigo;
    private double valor;
    private Date dataVencimento;
    private Date dataPagamento;
    private int cod_venda;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    
    public void setDataVencimento(String dataVencimento){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
            setDataVencimento(format.parse(dataVencimento));
        } catch (ParseException ex){
            Logger.getLogger(Venda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    
    public void setDataPagamento(String dataPagamento){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
            setDataPagamento(format.parse(dataPagamento));
        } catch (ParseException ex){
            Logger.getLogger(Venda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getCod_venda() {
        return cod_venda;
    }

    public void setCod_venda(int cod_venda) {
        this.cod_venda = cod_venda;
    }
    
    
}
