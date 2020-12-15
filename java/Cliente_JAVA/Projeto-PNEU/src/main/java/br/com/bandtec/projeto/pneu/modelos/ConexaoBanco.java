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
public class ConexaoBanco {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // procura onde está o banco no netbeans
    public static final String URL = "jdbc:mysql://localhost:3306/projeto?useTimezone=true&serverTimezone=UTC"; 
    //local do banco e fuso horário ip instancia fran:172.31.78.10, docker fran:fa119aa398b4
    //porta fran: 3307
    
//    public static final String ID_SERVIDOR = null;
            // Usuário e senha do banco local
    public static final String USER = "PNEU2SEMESTRE";
    public static final String PASSWORD = "pneu123";
}
