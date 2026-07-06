package org.epn.crudproductosmelvasuarez.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/control_productos";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("Conexión a MySQL OK");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("¡Error al conectarse a la base de datos! " + ex.getMessage());
        }
        return conn;
    }
}