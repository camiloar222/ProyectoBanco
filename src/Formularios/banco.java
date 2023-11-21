/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class banco extends javax.swing.JFrame {

    ConexionDB db = new ConexionDB();
    Connection con = db.conexion();

    /**
     * Creates new form banco
     */
    public banco() {
        initComponents();
        this.setLocationRelativeTo(null);
       // mostrarDatos(); // Mostrar datos al inicio
    }
    
    //Se busca el usuer en la base de datos
public void buscar(String valor) {
        String[] titulos = {"Cuenta", "Nombre y apellido", "Banco", "Monto"};
        String[] registro = new String[4];

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String consulta = "SELECT * FROM `tbbanco` WHERE Cuenta like '%" + valor + "%'";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(consulta)) {

            while (rs.next()) {
                registro[0] = rs.getString("Cuenta");
                registro[1] = rs.getString("Nombre y apellido");
                registro[2] = rs.getString("Banco");
                registro[3] = rs.getString("Monto actual");

                modelo.addRow(registro);
            }

            tablaCliente.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR: " + e.getMessage());
        }
        limpieza();
    }

    public void mostrarDatos() {
        String[] titulos = {"Cuenta", "Nombre y apellido", "Banco", "Monto"};
        String[] registro = new String[4];

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String consulta = "SELECT * FROM `tbbanco`";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(consulta)) {

            while (rs.next()) {
                registro[0] = rs.getString("Cuenta");
                registro[1] = rs.getString("Nombre y apellido");
                registro[2] = rs.getString("Banco");
                registro[3] = rs.getString("Monto actual");

                modelo.addRow(registro);
            }

            tablaCliente.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR: " + e.getMessage());
        }
    }

    public void eliminarDatos() {
        int filaSel = tablaCliente.getSelectedRow();

        try {
            String consulta = "DELETE FROM `tbbanco` WHERE Cuenta = '" + tablaCliente.getValueAt(filaSel, 0) + "'";
            try (Statement st = con.createStatement()) {
                int n = st.executeUpdate(consulta);

                if (n >= 0) {
                    JOptionPane.showMessageDialog(null, "Eliminado exitosamente");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al eliminar dato: " + e.getMessage());
        }
    }

    public void depositar() {
        try {
            String consulta = "UPDATE `tbbanco` SET `Cuenta`=?,`Nombre y apellido`=?,`Banco`=?,`Monto actual`=? WHERE Cuenta=?";

            int filaSel = tablaCliente.getSelectedRow();
            String cuentaSeleccionada = (String) tablaCliente.getValueAt(filaSel, 0);

            try (PreparedStatement pst = con.prepareStatement(consulta)) {
                pst.setString(1, txtCuenta.getText());
                pst.setString(2, txtNombre.getText());
                int seleccionado = cbBanco.getSelectedIndex();
                pst.setString(3, cbBanco.getItemAt(seleccionado));

                int numeroA = Integer.parseInt(txtMonto.getText());
                int numeroB = Integer.parseInt(txtMontoNuevo.getText());

                int respuesta = numeroA + numeroB;
                pst.setInt(4, respuesta);

                pst.setString(5, cuentaSeleccionada);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Se ha depositado correctamente " + numeroB + " pesos");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR deposito: " + e.getMessage());
        }
        mostrarDatos(); // Actualizar la tabla después del depósito
    }
    
    
     public void actualizarDatos(){
    try {
        String MYSQL="UPDATE `tbbanco` SET `Cuenta`=?,`Nombre y apellido`=?,`Banco`=?,`Monto actual`=? WHERE cuenta=?";
        
        int filaSel=tablaCliente.getSelectedRow();
        String oo=(String)tablaCliente.getValueAt(filaSel,0);
        
        PreparedStatement pst= con.prepareStatement(MYSQL);
        
        pst.setString(1, txtCuenta.getText());
        pst.setString(2, txtNombre.getText());
        int seleccionado=cbBanco.getSelectedIndex();
        pst.setString(3,cbBanco.getItemAt(seleccionado) );
        pst.setString(4, txtMonto.getText());
        
        pst.setString(5, oo);
        pst.execute();
        
        JOptionPane.showMessageDialog(null,"Se ha editado correctamente");
        

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"ERROR de edicion"+e.getMessage());
    }
}
     
      public void insertarDatos(){
    try {
        //String MYSQL="INSERT INTO 'tbBanco'(`Cuenta`, `Nombre y apellido`, `Banco`, `Monto actual`) values(?,?,?,?)";
        String MYSQL="INSERT INTO `tbbanco`(`Cuenta`, `Nombre y apellido`, `Banco`, `Monto actual`) VALUES (?,?,?,?)";
        
        PreparedStatement pst= con.prepareStatement(MYSQL);
        
        pst.setString(1, txtCuenta.getText());
        pst.setString(2, txtNombre.getText());
        int seleccionado=cbBanco.getSelectedIndex();
        pst.setString(3,cbBanco.getItemAt(seleccionado) );
        pst.setString(4, txtMonto.getText());
        
        pst.execute();
        
        JOptionPane.showMessageDialog(null,"Se ha registrado correctamente");
        

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"ERROR"+e.getMessage());
    }
}
       public void retirar(){
    try {
        String MYSQL="UPDATE `tbbanco` SET `Cuenta`=?,`Nombre y apellido`=?,`Banco`=?,`Monto actual`=? WHERE cuenta=?";
        
        int filaSel=tablaCliente.getSelectedRow();
        String oo=(String)tablaCliente.getValueAt(filaSel,0);
        
        PreparedStatement pst= con.prepareStatement(MYSQL);
        
        pst.setString(1, txtCuenta.getText());
        pst.setString(2, txtNombre.getText());
        int seleccionado=cbBanco.getSelectedIndex();
        pst.setString(3,cbBanco.getItemAt(seleccionado) );
        
        int numeroA=Integer.parseInt(txtMonto.getText());
        int numeroB=Integer.parseInt(txtMontoNuevo.getText());
        
        int respuesta = numeroA-numeroB;
        pst.setInt(4,respuesta);
        
        //pst.setString(4, txtMonto.getText()+txtMontoNuevo.getText());

        
        pst.setString(5, oo);
        pst.execute();
        
        JOptionPane.showMessageDialog(null,"Se ha retirado correctamente "+numeroB+" pesos");
        

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"ERROR retiro"+e.getMessage());
    }
}

    public void limpieza() {
        txtCuenta.setText("");
        txtNombre.setText("");
        cbBanco.setSelectedItem(null);
        txtMonto.setText("");
        txtMontoNuevo.setText("");
    }

    

   
  
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCuenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbBanco = new javax.swing.JComboBox<String>();
        jLabel4 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCliente = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtMontoNuevo = new javax.swing.JTextField();
        btnDepositar = new javax.swing.JToggleButton();
        btnRetirar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Felix Titling", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("SISTEMA BANCARIO ELICA");

        jLabel1.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cuenta:");

        txtCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCuentaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre y apellido:");

        jLabel3.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Banco:");

        cbBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ahorros", "Corriente" }));
        cbBanco.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBancoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Monto actual:");

        btnNuevo.setBackground(new java.awt.Color(0, 0, 0));
        btnNuevo.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardar.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(0, 0, 0));
        btnEliminar.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(0, 0, 0));
        btnActualizar.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Buscar:");

        txtBuscar.setForeground(new java.awt.Color(0, 102, 102));
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        tablaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCliente);

        jLabel8.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel8.setText("Monto:");

        txtMontoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoNuevoActionPerformed(evt);
            }
        });

        btnDepositar.setBackground(new java.awt.Color(0, 102, 102));
        btnDepositar.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        btnDepositar.setText("Depositar");
        btnDepositar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositarActionPerformed(evt);
            }
        });

        btnRetirar.setBackground(new java.awt.Color(0, 102, 102));
        btnRetirar.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        btnRetirar.setText("Retirar");
        btnRetirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetirarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(379, 379, 379)
                        .addComponent(jLabel8)
                        .addGap(26, 26, 26)
                        .addComponent(txtMontoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(383, 383, 383)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDepositar)
                .addGap(231, 231, 231)
                .addComponent(btnRetirar)
                .addGap(323, 323, 323))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMontoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDepositar)
                    .addComponent(btnRetirar))
                .addGap(37, 37, 37))
        );

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/money-bag.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel1)
                        .addGap(62, 62, 62)
                        .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btnNuevo)
                        .addGap(51, 51, 51)
                        .addComponent(btnGuardar)
                        .addGap(65, 65, 65)
                        .addComponent(btnEliminar)
                        .addGap(67, 67, 67)
                        .addComponent(btnActualizar)))
                .addContainerGap(163, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(399, 399, 399))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo)
                            .addComponent(btnGuardar)
                            .addComponent(btnEliminar)
                            .addComponent(btnActualizar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCuentaActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        limpieza();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        insertarDatos();
        limpieza();
        mostrarDatos();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminarDatos();
        mostrarDatos();
        limpieza();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        actualizarDatos();
        limpieza();
        mostrarDatos();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void cbBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBancoActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        buscar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tablaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClienteMouseClicked
        // TODO add your handling code here:
        int filaSel = tablaCliente.rowAtPoint(evt.getPoint());
        txtCuenta.setText(tablaCliente.getValueAt(filaSel, 0).toString());
        txtNombre.setText(tablaCliente.getValueAt(filaSel, 1).toString());
        cbBanco.setSelectedItem(tablaCliente.getValueAt(filaSel, 2));
        txtMonto.setText(tablaCliente.getValueAt(filaSel, 3).toString());
    }//GEN-LAST:event_tablaClienteMouseClicked

    private void txtMontoNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoNuevoActionPerformed

    private void btnDepositarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepositarActionPerformed

        depositar();
    }//GEN-LAST:event_btnDepositarActionPerformed

 
    private void btnRetirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirarActionPerformed
        retirar();
    }//GEN-LAST:event_btnRetirarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(banco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(banco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(banco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(banco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new banco().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JToggleButton btnDepositar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRetirar;
    private javax.swing.JComboBox<String> cbBanco;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaCliente;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCuenta;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtMontoNuevo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
