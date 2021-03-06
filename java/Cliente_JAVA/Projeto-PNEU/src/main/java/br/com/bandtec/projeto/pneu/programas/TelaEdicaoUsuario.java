package br.com.bandtec.projeto.pneu.programas;

import br.com.bandtec.projeto.pneu.clienteJira.ConexaoAPIJira;
import br.com.bandtec.projeto.pneu.modelos.ConexaoBanco;
import br.com.bandtec.projeto.pneu.modelos.Issue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class TelaEdicaoUsuario extends javax.swing.JFrame {

    String nickAtual = null;
    
    /**
     * Creates new form TelaEdicaoUsuario
     */
    public TelaEdicaoUsuario() {
        initComponents();
    }
    String nomeUsuario = null;
    void pegarDadosUsuario(String nome, String email, String cargo, String nick){
        nomeUsuario = nome;
        lbNomeUsuario.setText(nome);
        tfEmail.setText(email);
        tfCargo.setText(cargo);
        tfNick.setText(nick);
        
        nickAtual = nick;
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
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btVoltar = new javax.swing.JButton();
        lbTitulo = new javax.swing.JLabel();
        lbNomeUsuario = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btEditarDadosPessoais = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tfSenhaAtual = new javax.swing.JPasswordField();
        tfNovaSenha = new javax.swing.JPasswordField();
        tfConfNovaSenha = new javax.swing.JPasswordField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        tfCargo = new javax.swing.JTextField();
        tfNick = new javax.swing.JTextField();
        btRedefinicaoSenha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(35, 35, 35));

        jLabel9.setBackground(new java.awt.Color(151, 109, 208));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(3, 173, 227));
        jLabel9.setText("PNEU");

        btVoltar.setBackground(new java.awt.Color(3, 173, 227));
        btVoltar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btVoltar.setForeground(new java.awt.Color(255, 255, 255));
        btVoltar.setText("Voltar");
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

        lbTitulo.setBackground(new java.awt.Color(0, 0, 0));
        lbTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitulo.setText("Edição de Usuário");
        lbTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lbNomeUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbNomeUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNomeUsuario.setText("Nome Sobrenome");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Cargo");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Nick");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Senha Atual");

        btEditarDadosPessoais.setBackground(new java.awt.Color(3, 173, 227));
        btEditarDadosPessoais.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btEditarDadosPessoais.setForeground(new java.awt.Color(255, 255, 255));
        btEditarDadosPessoais.setText("Confirmar Editação");
        btEditarDadosPessoais.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btEditarDadosPessoais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarDadosPessoaisActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Nova senha");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("E-mail");

        tfSenhaAtual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 215)));

        tfNovaSenha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 215)));

        tfConfNovaSenha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 215)));
        tfConfNovaSenha.setPreferredSize(new java.awt.Dimension(2, 22));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Confirmar nova senha");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel18.setText("Dados pessoais");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel19.setText("Redefinição de Senha");

        tfEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfEmailActionPerformed(evt);
            }
        });

        tfCargo.setToolTipText("Insira a cidade");
        tfCargo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfCargo.setMinimumSize(new java.awt.Dimension(2, 22));
        tfCargo.setPreferredSize(new java.awt.Dimension(2, 22));

        tfNick.setToolTipText("Insira a cidade");
        tfNick.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfNick.setPreferredSize(new java.awt.Dimension(2, 22));

        btRedefinicaoSenha.setBackground(new java.awt.Color(3, 173, 227));
        btRedefinicaoSenha.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btRedefinicaoSenha.setForeground(new java.awt.Color(255, 255, 255));
        btRedefinicaoSenha.setText("Redefinir Senha");
        btRedefinicaoSenha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btRedefinicaoSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRedefinicaoSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbNomeUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfNick, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addComponent(tfCargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel12))
                                .addComponent(tfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel18)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(128, Short.MAX_VALUE)
                        .addComponent(btEditarDadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel14)
                    .addComponent(tfSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfConfNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel19)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btRedefinicaoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(91, 91, 91))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(lbTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNomeUsuario)
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfConfNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btEditarDadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRedefinicaoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVoltarActionPerformed
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
        dispose();
    }//GEN-LAST:event_btVoltarActionPerformed

    private void btEditarDadosPessoaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarDadosPessoaisActionPerformed
       
        String novoEmail = tfEmail.getText();
        String novoCargo = tfCargo.getText();
        String novoNick = tfNick.getText();
        
        // Conectando com banco de dados
        Connection con = null;          
        try {
            
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
        
            Object[] options = { "Não", "Sim"};  
            int opcao2 = JOptionPane.showOptionDialog(null, "Tem certeza que deseja editar os dados deste usuário?", "Confirmação de edição", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);  
            if(opcao2 == 1){ // Sim
                
                // Cria o java mysql update preparedstatement
                String query1 = "UPDATE Usuario SET email = '"+novoEmail+"' WHERE nick like '"+nickAtual+"';";
                String query2 = "UPDATE Usuario SET cargo = '"+novoCargo+"' WHERE nick like '"+nickAtual+"';";
                String query3 = "UPDATE Usuario SET nick = '"+novoNick+"' WHERE nick like '"+nickAtual+"';";
                PreparedStatement preparedStmt1 = con.prepareStatement(query1);
                PreparedStatement preparedStmt2 = con.prepareStatement(query2);
                PreparedStatement preparedStmt3 = con.prepareStatement(query3);


                // executa o java preparedstatement
                preparedStmt1.executeUpdate();
                preparedStmt2.executeUpdate();
                preparedStmt3.executeUpdate();
                
                nickAtual = novoNick;
                
                // Mensagem de sucesso de edição
                JOptionPane.showMessageDialog(null, "Usuário editado com sucesso");
                
                String msg = "O Usuário "+nomeUsuario+" foi editado.";
                String titulo = "Edição de usuário executada";
                try {
                    Issue novaIssue = new Issue();
                    ConexaoAPIJira.criacao(novaIssue, msg, titulo);
                } catch (IOException ex) {
                    Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
          
            con.close();
                
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TelaConsultaComponente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btEditarDadosPessoaisActionPerformed

    private void tfEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEmailActionPerformed

    private void btRedefinicaoSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRedefinicaoSenhaActionPerformed
        
        String senhaNoBanco = null;
        String senhaAtual = tfSenhaAtual.getText();
        
        String novaSenha = tfNovaSenha.getText();
        String confirmacaoNovaSenha = tfConfNovaSenha.getText();
        
        // Conexao com banco
        Connection con = null;            
        try {
            // Confirmação de exclusão
            
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);

            Statement stm = con.createStatement();
            String SQL = "select senha from Usuario where nick like '"+nickAtual+"';";
            ResultSet rs = stm.executeQuery(SQL); 

            while(rs.next()) {
                senhaNoBanco = rs.getString("senha");
            }

            if (senhaAtual.equals(senhaNoBanco)){
                if (novaSenha.equals(confirmacaoNovaSenha)) {

                    Object[] options = { "Não", "Sim"};  
                    int opcao2 = JOptionPane.showOptionDialog(null, "Confirmar redefinição de senha?", "Confirmação de redefinição de senha", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);  
                    if(opcao2 == 1){ // Opção Sim

                        // Atualiza senha
                        String query = "UPDATE Usuario SET senha = '"+novaSenha+"' WHERE nick like '"+nickAtual+"';";
                        PreparedStatement preparedStmt = con.prepareStatement(query);
                        preparedStmt.executeUpdate();
                        
                        JOptionPane.showMessageDialog(null,"Senha atualizada com sucesso!","Redefinição de senha!",JOptionPane.INFORMATION_MESSAGE);
                        
                        String msg = "A senha do usuário "+nomeUsuario+" foi editada.";
                        String titulo = "Edição de senha de usuário executada";
                        try {
                            Issue novaIssue = new Issue();
                            ConexaoAPIJira.criacao(novaIssue, msg, titulo);
                        } catch (IOException ex) {
                            Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        tfSenhaAtual.setText("");
                        tfNovaSenha.setText("");
                        tfConfNovaSenha.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "As novas senhas não batem.");
                    tfNovaSenha.setText("");
                    tfConfNovaSenha.setText("");

                }

            } else {
                JOptionPane.showMessageDialog(null, "A senha atual digitada não corresponde à cadastrada no banco. \nRedigite a senha.");
                tfSenhaAtual.setText("");
            }

            
            
            con.close();
                
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TelaConsultaComponente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btRedefinicaoSenhaActionPerformed

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
//            java.util.logging.Logger.getLogger(TelaEdicaoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TelaEdicaoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TelaEdicaoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TelaEdicaoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TelaEdicaoUsuario().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEditarDadosPessoais;
    private javax.swing.JButton btRedefinicaoSenha;
    private javax.swing.JButton btVoltar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JLabel lbNomeUsuario;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JTextField tfCargo;
    private javax.swing.JPasswordField tfConfNovaSenha;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfNick;
    private javax.swing.JPasswordField tfNovaSenha;
    private javax.swing.JPasswordField tfSenhaAtual;
    // End of variables declaration//GEN-END:variables
}
