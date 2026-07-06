package org.epn.crudproductosmelvasuarez.modelo;

import java.util.Map;

public interface Crud {
    Map<Integer, Producto> seleccionarTodo();
    Producto buscar(String codigo);
    void insertar(Producto producto);
    void actualizar(Producto producto);
    void eliminar(int id);
}