package org.epn.crudproductosmelvasuarez.modelo;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImplCrud implements Crud {
    private final String SELECT = "SELECT * FROM producto";
    private final String SELECT_BY_ID = "SELECT * FROM producto WHERE codigo=?";
    private final String INSERT = "INSERT INTO producto(codigo, nombre, precio) VALUES(?,?,?)";
    private final String UPDATE = "UPDATE producto SET codigo=?, nombre=?, precio=? WHERE id=?";
    private final String DELETE = "DELETE FROM producto WHERE id=?";

    @Override
    public Map<Integer, Producto> seleccionarTodo() {
        Map<Integer, Producto> map = new LinkedHashMap<>();
        try (Connection conn = new Conexion().conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT)) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")
                );
                map.put(p.getId(), p);
            }
            System.out.println("Registros obtenidos: " + map.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Producto buscar(String codigo) {
        Producto producto = null;
        try (Connection conn = new Conexion().conectar();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto(
                            rs.getInt("id"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getDouble("precio")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    @Override
    public void insertar(Producto producto) {
        try (Connection conn = new Conexion().conectar();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setString(1, producto.getCodigo());
            stmt.setString(2, producto.getNombre());
            stmt.setDouble(3, producto.getPrecio());
            stmt.executeUpdate();
            System.out.println("Producto insertado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Producto producto) {
        try (Connection conn = new Conexion().conectar();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, producto.getCodigo());
            stmt.setString(2, producto.getNombre());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getId());
            stmt.executeUpdate();
            System.out.println("Producto actualizado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection conn = new Conexion().conectar();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Producto eliminado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}