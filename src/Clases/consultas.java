package Clases;

import Formularios.banco;
import Formularios.login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;



public class consultas {
    public void guardarUsuario(String usuario, String password){
        ConexionDB db = new ConexionDB();
        String sql = "insert into usuarios(nombre, clave) values ('" + usuario +"', '" + password +"');";
        Statement st;
        Connection conexion = db.conectar();
        try
        {
            st = conexion.createStatement();
            int rs = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Guardado correctamente");
        }catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
   public void consultarUsuario(String user, String pass) {
        ConexionDB db = new ConexionDB();
        String usuarioCorrecto = null;
        String passCorrecto = null;

        try {
            Connection cn = db.conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT nombre, clave FROM usuarios");
            ResultSet rs = pst.executeQuery();

            boolean encontrado = false;

            while (rs.next()) {
                usuarioCorrecto = rs.getString("nombre");
                passCorrecto = rs.getString("clave");

                if (user.equals(usuarioCorrecto) && pass.equals(passCorrecto)) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión correcto. Bienvenido " + user);
                    encontrado = true;

                    // Abre la ventana del banco
                    SwingUtilities.invokeLater(() -> {
                        banco ventanaBanco = new banco();
                        ventanaBanco.setVisible(true);
                    });

                    break;
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }




}
