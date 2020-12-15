/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bandtec.projeto.pneu.programas;

import br.com.bandtec.projeto.pneu.clienteJira.ClienteJiraApi;
import br.com.bandtec.projeto.pneu.clienteJira.ConexaoAPIJira;
import br.com.bandtec.projeto.pneu.modelos.Componente;
import br.com.bandtec.projeto.pneu.modelos.ConexaoBanco;
import br.com.bandtec.projeto.pneu.modelos.Issue;
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
 * @author jenni
 */
public class TelaComponente extends javax.swing.JFrame {
    Componente componente = new Componente();
  
    
//    public void analisar() throws SQLException{
//        MudarjcbMetrica();
//        if("Editar".equals(getOperacao())){
//                TelaConsultaComponente frame = new TelaConsultaComponente();
//                jcbServidor.setSelectedItem(frame.getServidor());    
//                jcbMetrica.setSelectedItem(frame.getMetrica());    
//                jcbTipo.setSelectedItem(frame.getTipo());   
//                
//                System.out.println(frame.getServidor());
//                System.out.println(frame.getMetrica());
//                System.out.println(frame.getServidor());
//        }
//    }
      
    Integer comparacao = 0;
    private void verificar() throws SQLException {
        Connection con = null;
        try {
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
            String tipo = jcbTipo.getSelectedItem().toString();
            String servidor = jcbServidor.getSelectedItem().toString();
            Statement stm = con.createStatement();
            String SQLSelect = "select * from Servidor, Componente where fkServidor = idServidor";
            ResultSet rs = stm.executeQuery(SQLSelect);
            Integer fkServidor = 0;
            Integer fkTipoComponente;
            
            if(tipo.equals("CPU (Porcentagem)")){
                    fkTipoComponente = 1;
                } else if(tipo.equals("CPU (Clock)")){
                    fkTipoComponente = 2;
                } else if(tipo.equals("CPU (Temperatura)")){
                    fkTipoComponente = 3;
                } else if(tipo.equals("Memória (Porcentagem)")){
                    fkTipoComponente = 4;
                } else if(tipo.equals("Memória (GB)")){
                    fkTipoComponente = 5;
                } else if(tipo.equals("Disco (Porcentagem)")){
                    fkTipoComponente = 6;
                } else {
                    fkTipoComponente = 7;
                }
            
            while(rs.next()) {
                Statement stm2 = con.createStatement();
                String SQLSelectServidor = "select * from Servidor where nomeServidor like '"+servidor+"';";
                ResultSet rsServidor = stm2.executeQuery(SQLSelectServidor);
                while (rsServidor.next()){
                    fkServidor = rsServidor.getInt("idServidor");
                    if (fkTipoComponente == rs.getInt("fkTipoComponente") && fkServidor == rs.getInt("fkServidor")){
                        comparacao ++;
                    }
                }
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
    }
    
    void logarBanco() throws ClassNotFoundException{
        componente.setServidorComponente(jcbServidor.getSelectedItem().toString());
        
        Connection con = null;
        // Tenta conexão com banco MySQL
        try {                
            Class.forName(ConexaoBanco.DRIVER);
            Integer fkTipoComponente;
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
                
            // Verifica se algum dos campos está vazio
                ResultSet rs = null;
                String tipo = jcbTipo.getSelectedItem().toString();
                
                if(tipo.equals("CPU - Porcentagem")){
                    fkTipoComponente = 1;
                } else if(tipo.equals("CPU - Clock")){
                    fkTipoComponente = 2;
                } else if(tipo.equals("CPU - Temperatura")){
                    fkTipoComponente = 3;
                } else if(tipo.equals("Memória - Porcentagem")){
                    fkTipoComponente = 4;
                } else if(tipo.equals("Memória - GB")){
                    fkTipoComponente = 5;
                } else if(tipo.equals("Disco - Porcentagem")){
                    fkTipoComponente = 6;
                } else {
                    fkTipoComponente = 7;
                }
                
                Statement stm = con.createStatement();
                String SQLSelect = "select * from Servidor where nomeServidor = '"+componente.getServidorComponente()+"';";
                
                rs = stm.executeQuery(SQLSelect);
                
                Integer fkServidor = null;
                while(rs.next()) {
                    // Atribui valores de nick, senha e nome presentes no BD
                    fkServidor = Integer.parseInt(rs.getString("idServidor"));
                    

                    // Verifica se a combinação de senha e login existem na tabelo de Login
                
                    String SQLInsert = "Insert into Componente values (null, ?, ?);";
                
                    // Criando inserção mysql preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(SQLInsert);
                   
                        preparedStmt.setInt(1, fkTipoComponente);
                        preparedStmt.setInt(2, fkServidor);
                        // Executando preparedstatement
                        preparedStmt.execute();
                    
                    JOptionPane.showMessageDialog(null,"Cadastro da Metrica feito com sucesso!","Cadastro bem sucedido",JOptionPane.INFORMATION_MESSAGE);
                    
                    String msg = "O componente "+tipo+" foi cadastrado no servidor "+componente.getServidorComponente();
                    String titulo = "Cadastro de componente executado";
                    try {
                        Issue novaIssue = new Issue();
                        ConexaoAPIJira.criacao(novaIssue, msg, titulo);
                    } catch (IOException ex) {
                        Logger.getLogger(TelaServidor.class.getName()).log(Level.SEVERE, null, ex);
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
    }
    
    /**
     * Creates new form TelaComponente
     */
    public TelaComponente() {
        initComponents();
        jlCadastrados.setText("<html><u>Consultar Componentes cadastrados</u>");
        jlCadastrados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jlCadastrados.addMouseListener(new MouseAdapter()  
{  
        public void mouseClicked(MouseEvent e){  
            TelaConsultaComponente frameConsulta = new TelaConsultaComponente();
            frameConsulta.setVisible(true);
            Connection con = null;
            try {
            Class.forName(ConexaoBanco.DRIVER);
            con = DriverManager.getConnection(ConexaoBanco.URL, ConexaoBanco.USER, ConexaoBanco.PASSWORD);
            Statement stm = con.createStatement();
            String SQLSelectComponente = "select * from Servidor, Componente, TipoComponente where fkServidor = idServidor and fkTipoComponente = idTipoComponente order by nomeServidor asc;";
            ResultSet rsComponente = stm.executeQuery(SQLSelectComponente);
            DefaultListModel dlm = new DefaultListModel();
            while(rsComponente.next()) {
                dlm.addElement(rsComponente.getString("nomeServidor")+": "+ rsComponente.getString("tipo") + " ("+ rsComponente.getString("metrica")+")");
            }
            frameConsulta.jlListaCadastro.setModel(dlm);
            
            String SQLSelectServidor = "select * from Servidor order by nomeServidor asc;";
            ResultSet rsServidor = stm.executeQuery(SQLSelectServidor);
            frameConsulta.jcbFiltro.addItem("Todos");
            while(rsServidor.next()) {
                frameConsulta.jcbFiltro.addItem(rsServidor.getString("nomeServidor"));
            }
            
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

        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnCadastrar = new javax.swing.JButton();
        jcbServidor = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jcbTipo = new javax.swing.JComboBox<>();
        jlCadastrados = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client PNEU - Componente");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(421, 560));

        jPanel10.setBackground(new java.awt.Color(35, 35, 35));

        jLabel9.setBackground(new java.awt.Color(151, 109, 208));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(3, 173, 227));
        jLabel9.setText("PNEU");

        btnVoltar.setBackground(new java.awt.Color(3, 173, 227));
        btnVoltar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnVoltar.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltar.setText("Voltar");
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 274, Short.MAX_VALUE)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVoltar)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setBackground(new java.awt.Color(151, 109, 208));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Componente");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(65, 65, 65));
        jLabel1.setText("Tipo");

        btnCadastrar.setBackground(new java.awt.Color(3, 173, 227));
        btnCadastrar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        jcbServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbServidorActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(65, 65, 65));
        jLabel12.setText("Servidor");

        jcbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Componente a cadastrar--", "CPU - Porcentagem", "CPU - Clock", "CPU - Temperatura", "Memória - Porcentagem", "Memória - GB", "Disco - Porcentagem", "Disco - GB" }));
        jcbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTipoActionPerformed(evt);
            }
        });

        jlCadastrados.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jlCadastrados.setForeground(new java.awt.Color(3, 173, 227));
        jlCadastrados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlCadastrados.setText("Consultar componentes cadastrados");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel10)
                .addContainerGap(166, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 108, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel1)
                    .addComponent(jcbServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(108, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jlCadastrados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel10)
                .addGap(31, 31, 31)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlCadastrados)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        TelaCadastro frame = new TelaCadastro();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed


    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
             String comparar = jcbTipo.getSelectedItem().toString();
            if (!comparar.equals("--Componente a cadastrar--")) {
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
                    JOptionPane.showMessageDialog(null,"Componente já cadastrado nesse servidor","Erro ao cadastrar componente",JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,"Escolha um componente","Erro ao cadastrar componente",JOptionPane.ERROR_MESSAGE);
            }
            comparacao = 0;       
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void jcbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTipoActionPerformed
        
    }//GEN-LAST:event_jcbTipoActionPerformed

    private void jcbServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbServidorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbServidorActionPerformed

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
//            java.util.logging.Logger.getLogger(TelaComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TelaComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TelaComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TelaComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TelaComponente().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    protected javax.swing.JComboBox<String> jcbServidor;
    private javax.swing.JComboBox<String> jcbTipo;
    private javax.swing.JLabel jlCadastrados;
    // End of variables declaration//GEN-END:variables

    private void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
