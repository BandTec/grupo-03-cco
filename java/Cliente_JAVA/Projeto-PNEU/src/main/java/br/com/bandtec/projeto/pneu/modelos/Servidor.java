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
public class Servidor {
    private String nomeServidor;
    private String estadoServidor;
    private String cidadeServidor;
    private String bairroServidor;

    public String getCidadeServidor() {
        return cidadeServidor;
    }

    public void setCidadeServidor(String cidadeServidor) {
        this.cidadeServidor = cidadeServidor;
    }

    public String getBairroServidor() {
        return bairroServidor;
    }

    public void setBairroServidor(String bairroServidor) {
        this.bairroServidor = bairroServidor;
    }
    
    public String getNomeServidor() {
        return nomeServidor;
    }

    public void setNomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
    }

    public String getEstadoServidor() {
        return estadoServidor;
    }

    public void setEstadoServidor(String estadoServidor) {
        this.estadoServidor = estadoServidor;
    }
}
