package br.com.bandtec.projeto.pneu.programas;

import br.com.bandtec.projeto.pneu.clienteJira.ConexaoAPIJira;
import br.com.bandtec.projeto.pneu.modelos.ConexaoBanco;
import br.com.bandtec.projeto.pneu.modelos.Issue;
import br.com.bandtec.projeto.pneu.modelos.Localizacao;
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


public class TelaLocalizacao extends javax.swing.JFrame {
    Localizacao localizacao = new Localizacao();
    Integer comparacao = 0;
    private void verificar() throws SQLException {
        Connection con = null;
        try {
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
            String estado = jcbEstado.getSelectedItem().toString();
            String cidade = tfCidade.getText();
            String bairro = tfBairro.getText();
            String rua = tfRua.getText();
            String numero = tfNumero.getText();
            Statement stm = con.createStatement();
            String SQLSelect = "select * from Localizacao;";
            ResultSet rs = stm.executeQuery(SQLSelect);
            while(rs.next()) {
                if (estado.equals(rs.getString("estado")) && cidade.equals(rs.getString("cidade")) && bairro.equals(rs.getString("bairro")) && rua.equals(rs.getString("rua")) && numero.equals(rs.getString("numero"))){
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
        
        String estado = jcbEstado.getSelectedItem().toString();
        String cidade = tfCidade.getText();
        String bairro = tfBairro.getText();
        String rua = tfRua.getText();
        String numero = tfNumero.getText();
        
        localizacao.setEstado(estado);
        localizacao.setCidade(cidade);
        localizacao.setBairro(bairro);
        localizacao.setRua(rua);
        localizacao.setNumero(numero);
        
        Connection con = null;
        // Tenta conexão com banco MySQL
        try {                
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
                
            // Verifica se algum dos campos está vazio
            if(cidade.equals("") || bairro.equals("") || rua.equals("") || numero.equals("")){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            } else {       
                String SQL = "insert into Localizacao(estado, cidade, bairro, rua, numero) values (?, ?, ?, ?, ?)";

                // Criando inserção mysql preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(SQL);
                preparedStmt.setString(1, localizacao.getEstado());
                preparedStmt.setString(2, localizacao.getCidade());
                preparedStmt.setString(3, localizacao.getBairro());
                preparedStmt.setString(4, localizacao.getRua());
                preparedStmt.setString(5, localizacao.getNumero());   

                // Executando preparedstatement
                preparedStmt.execute();
                    
                JOptionPane.showMessageDialog(null,"Cadastro de Localização feito com sucesso!","Cadastro bem sucedido",JOptionPane.INFORMATION_MESSAGE);
                
                String msg = "A localização "+localizacao.getEstado()+" - "+localizacao.getCidade()+", "+localizacao.getCidade()+", "+localizacao.getBairro()+", "+localizacao.getNumero()+" foi cadastrada.";
                String titulo = "Cadastro de localização executado";
                try {
                    Issue novaIssue = new Issue();
                    ConexaoAPIJira.criacao(novaIssue, msg, titulo);
                } catch (IOException ex) {
                    Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // Limpa campos após cadastro feito com sucesso
                tfCidade.setText("");
                tfBairro.setText("");
                tfRua.setText("");
                tfNumero.setText("");
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
    public TelaLocalizacao() {
        initComponents();
        jlCadastrados.setText("<html><u>Consultar Localizações cadastrados</u>");
        jlCadastrados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jlCadastrados.addMouseListener(new MouseAdapter()  
{  
        public void mouseClicked(MouseEvent e){  
            TelaConsultaLocalizacao frameConsulta = new TelaConsultaLocalizacao();
            frameConsulta.setVisible(true);
            Connection con = null;
            try {
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
            Statement stm = con.createStatement();
            String SQLSelect = "select * from Localizacao;";
            ResultSet rs = stm.executeQuery(SQLSelect);
            DefaultListModel dlm = new DefaultListModel();   
            while(rs.next()) {
                dlm.addElement(rs.getString("estado")+" - "+rs.getString("cidade")+", "+rs.getString("bairro")+", "+rs.getString("rua")+", "+rs.getString("numero"));
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btVoltar = new javax.swing.JButton();
        jcbEstado = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tfCidade = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tfBairro = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfRua = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tfNumero = new javax.swing.JTextField();
        btCadastrar = new javax.swing.JButton();
        jlCadastrados = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client PNEU - Localização");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 276, Short.MAX_VALUE)
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

        jcbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        jcbEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        jcbEstado.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jcbEstadoAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jcbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEstadoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(65, 65, 65));
        jLabel1.setText("Estado");

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Localização");

        tfCidade.setToolTipText("Insira a cidade");
        tfCidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfCidade.setMinimumSize(new java.awt.Dimension(2, 22));
        tfCidade.setSelectionColor(new java.awt.Color(3, 173, 227));
        tfCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCidadeActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(65, 65, 65));
        jLabel11.setText("Cidade");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(65, 65, 65));
        jLabel12.setText("Bairro");

        tfBairro.setToolTipText("Insira a cidade");
        tfBairro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfBairro.setMinimumSize(new java.awt.Dimension(2, 22));
        tfBairro.setPreferredSize(new java.awt.Dimension(2, 22));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(65, 65, 65));
        jLabel13.setText("Rua");

        tfRua.setToolTipText("Insira a cidade");
        tfRua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3, 173, 227)));
        tfRua.setPreferredSize(new java.awt.Dimension(2, 22));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(65, 65, 65));
        jLabel14.setText("Número");

        tfNumero.setToolTipText("Insira a cidade");
        tfNumero.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(3, 173, 227), 1, true));
        tfNumero.setPreferredSize(new java.awt.Dimension(2, 22));
        tfNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNumeroActionPerformed(evt);
            }
        });

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

        jlCadastrados.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jlCadastrados.setForeground(new java.awt.Color(3, 173, 227));
        jlCadastrados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlCadastrados.setText("Consultar localizações cadastradas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jlCadastrados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfRua, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel14)
                                .addComponent(jLabel12)
                                .addComponent(jLabel11)
                                .addComponent(jLabel1)
                                .addComponent(jLabel13)
                                .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGap(4, 4, 4)
                .addComponent(tfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addGap(1, 1, 1)
                .addComponent(tfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addGap(1, 1, 1)
                .addComponent(tfRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addGap(1, 1, 1)
                .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlCadastrados, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jcbEstadoAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jcbEstadoAncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbEstadoAncestorMoved

    private void jcbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEstadoActionPerformed
             
    }//GEN-LAST:event_jcbEstadoActionPerformed

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
            JOptionPane.showMessageDialog(null,"Localização já existe","Erro ao cadastrar localização",JOptionPane.ERROR_MESSAGE);
        }
        comparacao = 0;
    }//GEN-LAST:event_btCadastrarActionPerformed

    private void btVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVoltarActionPerformed
        TelaCadastro frame = new TelaCadastro();
        frame.setVisible(true);
        dispose();
        
    }//GEN-LAST:event_btVoltarActionPerformed

    private void tfCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCidadeActionPerformed

    private void tfNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNumeroActionPerformed

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
//            java.util.logging.Logger.getLogger(TelaLocalizacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TelaLocalizacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TelaLocalizacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TelaLocalizacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TelaLocalizacao().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCadastrar;
    private javax.swing.JButton btVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    protected javax.swing.JComboBox<String> jcbEstado;
    private javax.swing.JLabel jlCadastrados;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JTextField tfCidade;
    private javax.swing.JTextField tfNumero;
    private javax.swing.JTextField tfRua;
    // End of variables declaration//GEN-END:variables
}
