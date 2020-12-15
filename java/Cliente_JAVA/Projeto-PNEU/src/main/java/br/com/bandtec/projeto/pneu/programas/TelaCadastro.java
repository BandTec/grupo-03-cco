/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bandtec.projeto.pneu.programas;

import br.com.bandtec.projeto.pneu.modelos.ConexaoBanco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author Francescolly
 */
public class TelaCadastro extends javax.swing.JFrame {

    TelaLocalizacao frame3 = new TelaLocalizacao();
    TelaServidor frame4 = new TelaServidor();
    TelaComponente frame5 = new TelaComponente();
    TelaUsuario frame6 = new TelaUsuario();
    

    String driver = "com.mysql.cj.jdbc.Driver"; // procura onde está o banco no netbeans
    String url = "jdbc:mysql://localhost:3306/projeto?useTimezone=true&serverTimezone=UTC"; //local do banco e fuso horário

    // Usuário e senha do banco local
    String user="PNEU2SEMESTRE";
    String password="pneu123";
    
    public TelaCadastro() {
        initComponents();
    }   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btLocalizacao = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btVoltar = new javax.swing.JButton();
        btServidor = new javax.swing.JButton();
        btComponente = new javax.swing.JButton();
        btUsuario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client PNEU - Cadastro");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btLocalizacao.setBackground(new java.awt.Color(3, 173, 227));
        btLocalizacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btLocalizacao.setForeground(new java.awt.Color(255, 255, 255));
        btLocalizacao.setText("Localização");
        btLocalizacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btLocalizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLocalizacaoActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Menu de Cadastro");

        jPanel10.setBackground(new java.awt.Color(35, 35, 35));

        jLabel9.setBackground(new java.awt.Color(151, 109, 208));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(3, 173, 227));
        jLabel9.setText("PNEU");

        btVoltar.setBackground(new java.awt.Color(3, 173, 227));
        btVoltar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btVoltar.setForeground(new java.awt.Color(255, 255, 255));
        btVoltar.setText("Sair");
        btVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btVoltar)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btServidor.setBackground(new java.awt.Color(3, 173, 227));
        btServidor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btServidor.setForeground(new java.awt.Color(255, 255, 255));
        btServidor.setText("Servidor");
        btServidor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btServidorActionPerformed(evt);
            }
        });

        btComponente.setBackground(new java.awt.Color(3, 173, 227));
        btComponente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btComponente.setForeground(new java.awt.Color(255, 255, 255));
        btComponente.setText("Componente");
        btComponente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btComponente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btComponenteActionPerformed(evt);
            }
        });

        btUsuario.setBackground(new java.awt.Color(3, 173, 227));
        btUsuario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btUsuario.setText("Usuário");
        btUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btLocalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(btUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btLocalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btLocalizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLocalizacaoActionPerformed

        // Abre frame TelaLocalizacao
        frame3.setVisible(Boolean.TRUE);
        dispose();
    }//GEN-LAST:event_btLocalizacaoActionPerformed

    private void btServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btServidorActionPerformed
        
        // Abre frame TelaServidor
        frame4.setVisible(Boolean.TRUE);
        ColetarLocalizacao();
        dispose();
    }//GEN-LAST:event_btServidorActionPerformed

    private void btComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btComponenteActionPerformed
        
        // Abre frame TelaComponente
        frame5.setVisible(Boolean.TRUE);
        ColetarServidor(frame5);
        dispose();
    }//GEN-LAST:event_btComponenteActionPerformed

    private void btVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVoltarActionPerformed
        // TODO add your handling code here:
        TelaLogin frame = new TelaLogin();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_btVoltarActionPerformed
    
    private void btUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUsuarioActionPerformed
        // TODO add your handling code here:
        Integer senha_ok = 0;
        String senha = null;
        String senhaGerente = "Adm123";
        while (senha_ok == 0){
            JPasswordField pf = new JPasswordField();
            String msg = "Digite a senha de Gerente";
            Integer okCxl = JOptionPane.showConfirmDialog(null, pf, msg, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (okCxl == 0) {
                senha = new String(pf.getPassword());
               
                if (senha.equals(senhaGerente)){
                    JOptionPane.showMessageDialog(null, "Senha correta");
                    frame6.setVisible(Boolean.TRUE);
                    dispose();
                    senha_ok++;
                }else if (senha.equals("")){
                    JOptionPane.showMessageDialog(null, "Senha não preenchida");
                } else if (senha_ok == 0){
                    JOptionPane.showMessageDialog(null, "Senha incorreta");
               }
                // Em caso de erro com conexão, gera aviso
            } else {
                senha_ok++;
            }
        }
    }//GEN-LAST:event_btUsuarioActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TelaCadastro().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btComponente;
    private javax.swing.JButton btLocalizacao;
    private javax.swing.JButton btServidor;
    private javax.swing.JButton btUsuario;
    private javax.swing.JButton btVoltar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    // End of variables declaration//GEN-END:variables

    private void ColetarLocalizacao() {
        
        Connection con = null;
        // Tenta conexão com banco MySQL
                
        try {
            frame4.jcbEstado.removeAllItems();
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            Statement stm = con.createStatement();
            String SQLSelect = "Select Distinct (estado) from Localizacao;";
            ResultSet rs = stm.executeQuery(SQLSelect);
            while(rs.next()) {
                String estado = rs.getString("estado");
                frame4.jcbEstado.addItem(estado);
            }
        }catch (SQLException ex) {
            Logger.getLogger(TelaCadastro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void ColetarServidor(TelaComponente frame) {
        
        Connection con = null;
        // Tenta conexão com banco MySQL
                
        try {
            frame.jcbServidor.removeAllItems();
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            Statement stm = con.createStatement();
            String SQLSelect = "Select Distinct (nomeServidor) from Servidor;";
            ResultSet rs = stm.executeQuery(SQLSelect);
            while(rs.next()) {
                String estado = rs.getString("nomeServidor");
                frame.jcbServidor.addItem(estado);
            }
        }catch (SQLException ex) {
            Logger.getLogger(TelaCadastro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
