/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bandtec.projeto.pneu.programas;

import br.com.bandtec.projeto.pneu.clienteJira.ConexaoAPIJira;
import br.com.bandtec.projeto.pneu.modelos.ConexaoBanco;
import br.com.bandtec.projeto.pneu.modelos.Issue;
import br.com.bandtec.projeto.pneu.modelos.Servidor;
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
 * @author jenni
 */
public class TelaServidor extends javax.swing.JFrame {
    
    Servidor servidor = new Servidor();
    Integer comparacao = 0;
    private void verificar() throws SQLException {
        Connection con = null;
        try {
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
            String comparar = tfNomeServidor.getText();
            Statement stm = con.createStatement();
            String SQLSelect = "select * from Servidor;";
            ResultSet rs = stm.executeQuery(SQLSelect);
            while(rs.next()) {
                if (comparar.equals(rs.getString("nomeServidor"))){
                    comparacao ++;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
    }
    private void MudarjcbLocalizacao() throws SQLException {
        servidor.setEstadoServidor(jcbEstado.getSelectedItem().toString());
        jcbLocalizacao.removeAllItems();
        Connection con = null;
        // Tenta conexão com banco MySQL
                
        try {
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
            Statement stm = con.createStatement();
            String SQLSelect = "select * from Localizacao where estado = '"+servidor.getEstadoServidor()+"';";
            ResultSet rs = stm.executeQuery(SQLSelect);
            while(rs.next()) {
                servidor.setCidadeServidor(rs.getString("cidade"));
                servidor.setBairroServidor(rs.getString("bairro"));
                
                jcbLocalizacao.addItem(servidor.getCidadeServidor()+", "+servidor.getBairroServidor());
            }
        } catch (ClassNotFoundException ex) {
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
    
    void logarBanco() throws ClassNotFoundException{
        servidor.setNomeServidor(tfNomeServidor.getText());
        String DividirLocalizacao = jcbLocalizacao.getSelectedItem().toString();
        
        Connection con = null;
        // Tenta conexão com banco MySQL
        try {                
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
                
            // Verifica se algum dos campos está vazio
            if(servidor.getNomeServidor().equals("")){
                JOptionPane.showMessageDialog(null, "Preencha o nome do servidor", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            } else {
                ResultSet rs = null;
                
                Statement stm = con.createStatement();
                String[] result = DividirLocalizacao.split(", ");
                servidor.setCidadeServidor(result[0]);
                servidor.setBairroServidor(result[1]);
                String SQLSelect = "select * from Localizacao where estado = '"+servidor.getEstadoServidor()+"' and cidade = '"+servidor.getCidadeServidor()+"' and bairro = '"+servidor.getBairroServidor()+"';";
                rs = stm.executeQuery(SQLSelect);
                
                String fkLocalizacao = null;
                Integer fkLocalizacaoInt = null;
                Integer i = 0;
                while(rs.next()) {
                    i++;
                    // Atribui valores de nick, senha e nome presentes no BD
                    fkLocalizacao = rs.getString("IdLocalizacao");
                   
                    
                    fkLocalizacaoInt = Integer.parseInt(fkLocalizacao);

                    // Verifica se a combinação de senha e login existem na tabelo de Login
                
                    String SQLInsert = "insert into Servidor (statusOnline, nomeServidor, fkLocalizacao) values ('online', ?, ?);";
                
                    // Criando inserção mysql preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(SQLInsert);
                    preparedStmt.setString(1, servidor.getNomeServidor());
                    preparedStmt.setInt(2, fkLocalizacaoInt);  
                    // Executando preparedstatement
                    preparedStmt.execute();
                    
                    JOptionPane.showMessageDialog(null,"Cadastro do Servidor feito com sucesso!","Cadastro bem sucedido",JOptionPane.INFORMATION_MESSAGE);
                    
                    String msg = "O servidor "+servidor.getNomeServidor()+" foi cadastrado";
                    try {
                        Issue novaIssue = new Issue();
                        novaIssue.setProjectKey("BDJ");
                        novaIssue.setSummary(msg);
                        novaIssue.setDescription("Cadastro de servidor foi executado");
//                        novaIssue.setLabels("Castro", "Lindo");
                        ConexaoAPIJira.criacao(novaIssue);

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        System.out.println("Issue criada: "+gson.toJson(novaIssue));
                    } catch (IOException ex) {
                        Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    // Limpa campos após cadastro feito com sucesso
                    tfNomeServidor.setText("");
                } 
                
                if (i == 0) {
                    JOptionPane.showMessageDialog(null,"A localização inserida não está cadastrada no banco de dados","Localização não cadastrada",JOptionPane.ERROR_MESSAGE);
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
            }
    }// fim do bloco try-catch-finally
    /**
     * Creates new form TelaServidor
     */
    public TelaServidor() {
        initComponents();
        jlCadastrados.setText("<html><u>Consultar servidores cadastrados</u>");
        jlCadastrados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jlCadastrados.addMouseListener(new MouseAdapter()  
{  
        public void mouseClicked(MouseEvent e){
            TelaConsultaServidor frameConsulta = new TelaConsultaServidor();
            frameConsulta.setVisible(true);
            Connection con = null;
            try {
                Class.forName(ConexaoBanco.DRIVER);
                con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
                Statement stm = con.createStatement();
                String SQLSelectServidor = "select * from Servidor, Localizacao where fkLocalizacao = idLocalizacao;";
                ResultSet rsServidor = stm.executeQuery(SQLSelectServidor);
                DefaultListModel dlm = new DefaultListModel();   
                while(rsServidor.next()) {
                    String nomeServidor = rsServidor.getString("nomeServidor");
                    String estadoServidor = rsServidor.getString("estado");
                    String cidadeServidor = rsServidor.getString("cidade");
                    String bairroServidor = rsServidor.getString("bairro");
                    dlm.addElement(nomeServidor +": "+estadoServidor+" - "+cidadeServidor+", "+bairroServidor);
                }
                String SQLSelectEstado = "select distinct (estado) from Localizacao;";
                ResultSet rsEstado = stm.executeQuery(SQLSelectEstado);
                frameConsulta.jcbEstado.addItem("Todos");
                while(rsEstado.next()) {
                    frameConsulta.jcbEstado.addItem(rsEstado.getString("estado"));
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

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btCadastrar3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tfNomeServidor = new javax.swing.JTextField();
        btCadastrar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jcbEstado = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jcbLocalizacao = new javax.swing.JComboBox<>();
        jlCadastrados = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client PNEU - Servidor");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(35, 35, 35));

        jLabel9.setBackground(new java.awt.Color(151, 109, 208));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(3, 173, 227));
        jLabel9.setText("PNEU");

        btCadastrar3.setBackground(new java.awt.Color(3, 173, 227));
        btCadastrar3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btCadastrar3.setForeground(new java.awt.Color(255, 255, 255));
        btCadastrar3.setText("Voltar");
        btCadastrar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCadastrar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrar3ActionPerformed(evt);
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
                .addComponent(btCadastrar3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btCadastrar3)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setBackground(new java.awt.Color(151, 109, 208));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Servidor");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(65, 65, 65));
        jLabel1.setText("Estado:");

        tfNomeServidor.setToolTipText("");
        tfNomeServidor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfNomeServidor.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfNomeServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNomeServidorActionPerformed(evt);
            }
        });

        btCadastrar.setBackground(new java.awt.Color(3, 173, 227));
        btCadastrar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        btCadastrar.setText("Cadastrar");
        btCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(65, 65, 65));
        jLabel8.setText("Nome:");

        jcbEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        jcbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEstadoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(65, 65, 65));
        jLabel11.setText("Localização:");

        jcbLocalizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbLocalizacaoActionPerformed(evt);
            }
        });

        jlCadastrados.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jlCadastrados.setForeground(new java.awt.Color(3, 173, 227));
        jlCadastrados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlCadastrados.setText("Consultar servidores cadastrados");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11)
                            .addComponent(jLabel1)
                            .addComponent(tfNomeServidor)
                            .addComponent(jLabel8)
                            .addComponent(jcbEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbLocalizacao, 0, 235, Short.MAX_VALUE)
                            .addComponent(jlCadastrados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel10)
                .addGap(25, 25, 25)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfNomeServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbLocalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlCadastrados)
                .addContainerGap(24, Short.MAX_VALUE))
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

    private void btCadastrar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrar3ActionPerformed
        // TODO add your handling code here:
        TelaCadastro frame = new TelaCadastro();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_btCadastrar3ActionPerformed

    private void tfNomeServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomeServidorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomeServidorActionPerformed

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
            JOptionPane.showMessageDialog(null,"Nome de servidor já existe","Erro ao cadastrar servidor",JOptionPane.ERROR_MESSAGE);
        }
        comparacao = 0;
        
        
    }//GEN-LAST:event_btCadastrarActionPerformed

    private void jcbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEstadoActionPerformed
        try {
            MudarjcbLocalizacao();
        } catch (SQLException ex) {
            Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jcbEstadoActionPerformed

    private void jcbLocalizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbLocalizacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbLocalizacaoActionPerformed

    /**
     * @param args the command line arguments
//     */
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
//            java.util.logging.Logger.getLogger(TelaServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TelaServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TelaServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TelaServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TelaServidor().setVisible(true);
//            }
//        });
//    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCadastrar;
    private javax.swing.JButton btCadastrar3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel4;
    protected javax.swing.JComboBox<String> jcbEstado;
    private javax.swing.JComboBox<String> jcbLocalizacao;
    private javax.swing.JLabel jlCadastrados;
    private javax.swing.JTextField tfNomeServidor;
    // End of variables declaration//GEN-END:variables
}
