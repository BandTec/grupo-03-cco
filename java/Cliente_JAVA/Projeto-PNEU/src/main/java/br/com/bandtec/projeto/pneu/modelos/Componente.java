/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bandtec.projeto.pneu.modelos;

/**
 *
 * @author LEONARDOOLIVEIRALEAL
 */
public class Componente {
    private String Componente;
    private String ServidorComponente;

    public String getComponente() {
        return Componente;
    }

    public void setComponente(String TipoComponente) {
        this.Componente = TipoComponente;
    }

    public String getServidorComponente() {
        return ServidorComponente;
    }

    public void setServidorComponente(String ServidorComponente) {
        this.ServidorComponente = ServidorComponente;
    }
    
}
