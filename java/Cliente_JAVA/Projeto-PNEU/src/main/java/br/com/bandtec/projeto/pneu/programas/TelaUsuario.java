/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bandtec.projeto.pneu.programas;

import br.com.bandtec.projeto.pneu.clienteJira.ConexaoAPIJira;
import br.com.bandtec.projeto.pneu.modelos.ConexaoBanco;
import br.com.bandtec.projeto.pneu.modelos.Issue;
import br.com.bandtec.projeto.pneu.modelos.UsuarioCadastrado;
import br.com.bandtec.projeto.pneu.modelos.UsuarioLogado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author LEONARDOOLIVEIRALEAL
 */
public class TelaUsuario extends javax.swing.JFrame {
    UsuarioCadastrado usuario = new UsuarioCadastrado();
    Integer comparacao = 0;
    private void verificar() throws SQLException {
        Connection con = null;
        try {
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
            String comparar = tfNick.getText();
            Statement stm = con.createStatement();
            String SQLSelect = "select * from Usuario;";
            ResultSet rs = stm.executeQuery(SQLSelect);
            
            while(rs.next()) {
                if (comparar.equals(rs.getString("nick"))){
                    comparacao ++;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
    }
    
    void logarBanco() throws ClassNotFoundException{
        String nome = tfNome.getText();
        String cargo = tfCargo.getText();
        String nick = tfNick.getText();
        String senha = tfSenha.getText();
        String confSenha = tfConfSenha.getText();
        String Email = tfEmail.getText();
        
        usuario.setNome(nome);
        usuario.setCargo(cargo);
        usuario.setNick(nick);
        usuario.setSenha(senha);
        usuario.setEmail(Email);
        
        Connection con = null;
        // Tenta conexão com banco MySQL
        try {                
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
                
            // Verifica se algum dos campos está vazio
            if(nome.equals("") || cargo.equals("") || nick.equals("") || senha.equals("") || confSenha.equals("")){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            } else {
                if (senha.equals(confSenha)) {
                    String SQL = "insert into Usuario(nome, cargo, nick, senha, email) values (?, ?, ?, ?, ?)";

                    // Criando inserção mysql preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(SQL);
                    preparedStmt.setString(1, usuario.getNome());
                    preparedStmt.setString(2, usuario.getCargo());
                    preparedStmt.setString(3, usuario.getNick());
                    preparedStmt.setString(4, usuario.getSenha());   
                    preparedStmt.setString(5, usuario.getEmail());  

                    // Executando preparedstatement
                    preparedStmt.execute();
                    
                    System.out.println(UsuarioLogado.getEmail());;
                    JOptionPane.showMessageDialog(null,"Cadastro de usuário feito com sucesso!","Cadastro bem sucedido",JOptionPane.INFORMATION_MESSAGE);
                    
                    String msg = "O usuário "+usuario.getNome()+" foi cadastrado(a).";
                    String titulo = "Cadastro de usuário executado";
                    try {
                        Issue novaIssue = new Issue();
                        ConexaoAPIJira.criacao(novaIssue, msg, titulo);
                    } catch (IOException ex) {
                        Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    // Limpa campos após cadastro feito com sucesso
                    tfNome.setText("");
                    tfCargo.setText("");
                    tfNick.setText("");
                    tfSenha.setText("");
                    tfConfSenha.setText("");
                    tfEmail.setText("");
                } else {
                    JOptionPane.showMessageDialog(null,"Digite as senhas iguais","Erro de confirmação de senha",JOptionPane.ERROR_MESSAGE);
                }
                
            } 
            
            // Em caso de erro com conexão, gera aviso
            }catch(SQLException e){
                e.printStackTrace(); //vejamos que erro foi gerado e quem o gerou
                JOptionPane.showMessageDialog(null,"Erro na conexão, com o banco de dados!","Erro de Conexão",JOptionPane.WARNING_MESSAGE);
            }finally { 
                try{ 
                    con.close(); // Após finalizar processo, fecha a conexão
                }catch(SQLException onConClose){
                    System.out.println("Houve erro no fechamento da conexão");
                    JOptionPane.showMessageDialog(null,"Erro na conexão, com o banco de dados!","Erro de conexão",JOptionPane.WARNING_MESSAGE);
                    onConClose.printStackTrace();
                }
            } // fim do bloco try-catch-finally

    }
    
    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        jlCadastrados.setText("<html><u>Consultar usuários cadastrados</u>");
        jlCadastrados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jlCadastrados.addMouseListener(new MouseAdapter(){  
    
            public void mouseClicked(MouseEvent e){  
                
                TelaConsultaUsuario frameConsulta = new TelaConsultaUsuario();
                frameConsulta.setVisible(true);
                
                Connection con = null;
                try {
                    Class.forName(ConexaoBanco.DRIVER);
                    con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
                    Statement stm = con.createStatement();
                    String SQLSelect = "select * from Usuario;";
                    ResultSet rs = stm.executeQuery(SQLSelect);
                    DefaultListModel dlm = new DefaultListModel();   

                    while(rs.next()) {
                        dlm.addElement(rs.getString("nome")+", "+rs.getString("nick")+", "+rs.getString("cargo"));
                    }

                    frameConsulta.jlListaCadastro.setModel(dlm);  
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
                } finally { 
                        try{ 
                            con.close(); // Após finalizar processo, fecha a conexão
                        }catch(SQLException onConClose){
                            System.out.println("Houve erro no fechamento da conexão");
                            JOptionPane.showMessageDialog(null,"Erro na conexão, com o banco de dados!","Erro de conexão",JOptionPane.WARNING_MESSAGE);
                            onConClose.printStackTrace();
                        }
                    }

                }  
    }); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jFrame1 = new javax.swing.JFrame();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btCadastrar2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tfCargo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfNick = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btCadastrar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jlCadastrados = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        tfSenha = new javax.swing.JPasswordField();
        tfConfSenha = new javax.swing.JPasswordField();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(35, 35, 35));

        jLabel9.setBackground(new java.awt.Color(151, 109, 208));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(3, 173, 227));
        jLabel9.setText("PNEU");

        btCadastrar2.setBackground(new java.awt.Color(3, 173, 227));
        btCadastrar2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btCadastrar2.setForeground(new java.awt.Color(255, 255, 255));
        btCadastrar2.setText("Voltar");
        btCadastrar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCadastrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrar2ActionPerformed(evt);
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
                .addComponent(btCadastrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btCadastrar2)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Usuário");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tfNome.setToolTipText("Insira a cidade");
        tfNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfNome.setMinimumSize(new java.awt.Dimension(2, 22));
        tfNome.setSelectionColor(new java.awt.Color(3, 173, 227));
        tfNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNomeActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(65, 65, 65));
        jLabel11.setText("Nome");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(65, 65, 65));
        jLabel12.setText("Cargo");

        tfCargo.setToolTipText("Insira a cidade");
        tfCargo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfCargo.setMinimumSize(new java.awt.Dimension(2, 22));
        tfCargo.setPreferredSize(new java.awt.Dimension(2, 22));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(65, 65, 65));
        jLabel13.setText("Nick");

        tfNick.setToolTipText("Insira a cidade");
        tfNick.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfNick.setPreferredSize(new java.awt.Dimension(2, 22));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(65, 65, 65));
        jLabel14.setText("Senha");

        btCadastrar.setBackground(new java.awt.Color(3, 173, 227));
        btCadastrar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        btCadastrar.setText("Cadastrar");
        btCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(65, 65, 65));
        jLabel15.setText("Confirmar senha");

        jlCadastrados.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jlCadastrados.setForeground(new java.awt.Color(3, 173, 227));
        jlCadastrados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlCadastrados.setText("Consultar usuários cadastrados");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(65, 65, 65));
        jLabel16.setText("E-mail");

        tfEmail.setToolTipText("Insira a cidade");
        tfEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfEmail.setMinimumSize(new java.awt.Dimension(2, 22));
        tfEmail.setSelectionColor(new java.awt.Color(3, 173, 227));
        tfEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfEmailActionPerformed(evt);
            }
        });

        tfSenha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 215)));

        tfConfSenha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 215)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jlCadastrados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel16)
                                .addComponent(tfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addComponent(tfNick, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfCargo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15)
                                .addComponent(jLabel13)
                                .addComponent(jLabel12)
                                .addComponent(tfSenha)
                                .addComponent(tfConfSenha))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(2, 2, 2)
                .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(2, 2, 2)
                .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addGap(1, 1, 1)
                .addComponent(tfCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addGap(1, 1, 1)
                .addComponent(tfNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addGap(2, 2, 2)
                .addComponent(tfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfConfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jlCadastrados)
                .addContainerGap(29, Short.MAX_VALUE))
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

    private void btCadastrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrar2ActionPerformed
        TelaCadastro frame = new TelaCadastro();
        frame.setVisible(true);
        dispose();

    }//GEN-LAST:event_btCadastrar2ActionPerformed

    private void tfNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomeActionPerformed

    private void btCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarActionPerformed
        try {
            verificar();
        } catch (SQLException ex) {
            Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (comparacao == 0) {
            try {
            // TODO add your handling code here:
                logarBanco();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null,"Nickname já esta em uso","Erro ao cadastrar usuário",JOptionPane.ERROR_MESSAGE);
        }
        comparacao = 0;
    }//GEN-LAST:event_btCadastrarActionPerformed

    private void tfEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEmailActionPerformed

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
//            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TelaUsuario().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCadastrar;
    private javax.swing.JButton btCadastrar2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jlCadastrados;
    private javax.swing.JTextField tfCargo;
    private javax.swing.JPasswordField tfConfSenha;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfNick;
    private javax.swing.JTextField tfNome;
    private javax.swing.JPasswordField tfSenha;
    // End of variables declaration//GEN-END:variables
}
