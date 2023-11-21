/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author USUARIO
 */
public class ConexionDB {

    private static Connection con;
    // Declaramos los datos de conexión a la bd
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "2222";
    private static final String url = "jdbc:mysql://localhost:3306/sys";

    // Función que va a conectarse a mi bd de MySQL
    public Connection conectar() {
        con = null;
        try {
            con = DriverManager.getConnection(url, user, pass);
            if (con != null) {
                System.out.println("Conexión exitosa");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
        return con;
    }

    // Método que devuelve la conexión
    public Connection conexion() {
        return conectar();
    }
}