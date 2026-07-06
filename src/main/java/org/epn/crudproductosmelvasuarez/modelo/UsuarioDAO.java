package org.epn.crudproductosmelvasuarez.modelo;

import java.sql.*;

public class UsuarioDAO {

    private final String SELECT_BY_USERNAME = "SELECT * FROM usuarios WHERE username=?";
    private final String INSERT = "INSERT INTO usuarios(username, password_hash, rol) VALUES(?,?,?)";

    public Usuario buscarPorUsername(String username) {
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_USERNAME)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setRol(rs.getString("rol"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean registrarUsuario(Usuario usuario) {
        try (Connection conn = new Conexion().conectar();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPasswordHash());
            ps.setString(3, usuario.getRol());

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}