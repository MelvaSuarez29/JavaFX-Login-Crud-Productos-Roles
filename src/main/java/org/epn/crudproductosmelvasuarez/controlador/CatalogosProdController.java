package org.epn.crudproductosmelvasuarez.controlador;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.epn.crudproductosmelvasuarez.modelo.Crud;
import org.epn.crudproductosmelvasuarez.modelo.ImplCrud;
import org.epn.crudproductosmelvasuarez.modelo.Producto;

import java.io.IOException;
import java.util.Map;

public class CatalogosProdController {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, Integer> colId;
    @FXML
    private TableColumn<Producto, String> colCodigo;
    @FXML
    private TableColumn<Producto, String> colProducto;
    @FXML
    private TableColumn<Producto, Double> colPrecio;

    // Botones
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnMostrar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnImprimirReporte;
    @FXML
    private Button btnCerrarSesion;

    private Crud crud = new ImplCrud();
    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
    private String rolUsuario = "CLIENTE";

    @FXML
    public void initialize() {
        // Configurar columnas
        colId.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getId()).asObject());

        colCodigo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCodigo()));

        colProducto.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNombre()));

        colPrecio.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPrecio()).asObject());

        // Cargar datos
        mostrarTodos();

        // Selección de fila
        tablaProductos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        cargarProductoEnFormulario(newSelection);
                    }
                }
        );
    }

    public void setRolUsuario(String rol) {
        this.rolUsuario = rol;
        System.out.println("Rol configurado: " + rol);
    }

    public void aplicarRestriccionesPorRol() {
        System.out.println("Aplicando restricciones para rol: " + rolUsuario);

        // Verificar que los botones no sean null
        if (btnInsertar == null || btnModificar == null || btnEliminar == null) {
            System.out.println("Botones no inicializados, intentando recuperarlos...");
            return;
        }

        // Deshabilitar todos los botones primero
        btnInsertar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);

        // Habilitar según el rol
        switch (rolUsuario) {
            case "Administrador":
                btnInsertar.setDisable(false);
                btnModificar.setDisable(false);
                btnEliminar.setDisable(false);
                System.out.println("Administrador: Todos los botones habilitados");
                break;
            case "Vendedor":
                btnInsertar.setDisable(false);
                btnEliminar.setDisable(false);
                System.out.println("Vendedor: Insertar y Eliminar habilitados");
                break;
            case "Cliente":
                System.out.println("Cliente: Solo visualización");
                break;
            default:
                System.out.println("Rol desconocido: " + rolUsuario);
                break;
        }
    }

    @FXML
    private void buscar() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            mostrarAlerta("Error", "Ingrese un código para buscar.");
            return;
        }

        Producto p = crud.buscar(codigo);

        if (p != null) {
            cargarProductoEnFormulario(p);
            mostrarAlerta("Información", "Producto encontrado.");
        } else {
            mostrarAlerta("Información", "Producto no encontrado.");
        }
    }

    @FXML
    private void insertar() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            if (codigo.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta("Error", "Código y nombre son obligatorios.");
                return;
            }

            Producto nuevo = new Producto(codigo, nombre, precio);
            crud.insertar(nuevo);

            mostrarTodos();
            limpiarFormulario();

            mostrarAlerta("Éxito", "Producto insertado correctamente.");

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo insertar: " + e.getMessage());
        }
    }

    @FXML
    private void modificar() {
        try {
            if (txtId.getText().isEmpty()) {
                mostrarAlerta("Error", "Seleccione un producto de la tabla.");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            Producto producto = new Producto(id, codigo, nombre, precio);
            crud.actualizar(producto);

            mostrarTodos();
            limpiarFormulario();

            mostrarAlerta("Éxito", "Producto actualizado correctamente.");

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo modificar: " + e.getMessage());
        }
    }

    @FXML
    private void eliminar() {
        try {
            if (txtId.getText().isEmpty()) {
                mostrarAlerta("Error", "Seleccione un producto de la tabla.");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());

            // Confirmación
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar Eliminación");
            confirm.setHeaderText("¿Está seguro que desea eliminar este producto?");
            confirm.setContentText("Esta acción no se puede deshacer.");

            if (confirm.showAndWait().get() == ButtonType.OK) {
                crud.eliminar(id);
                mostrarTodos();
                limpiarFormulario();
                mostrarAlerta("Éxito", "Producto eliminado correctamente.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Seleccione un producto válido.");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo eliminar: " + e.getMessage());
        }
    }

    @FXML
    private void mostrarTodos() {
        Map<Integer, Producto> mapa = crud.seleccionarTodo();

        listaProductos.clear();

        if (mapa != null && !mapa.isEmpty()) {
            listaProductos.addAll(mapa.values());
            System.out.println("Productos cargados: " + listaProductos.size());
        } else {
            System.out.println("No hay productos en la base de datos");
        }

        tablaProductos.setItems(listaProductos);
        tablaProductos.refresh();
    }

    @FXML
    private void imprimirReporte() {

        System.out.println("\n        REPORTE DE PRODUCTOS            ");
        System.out.println("Usuario: " + rolUsuario);
        System.out.println("Fecha: " + new java.util.Date());
        System.out.println("-----------------------------------------");

        if (listaProductos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            System.out.printf("%-5s %-10s %-25s %10s%n", "ID", "Código", "Producto", "Precio");
            System.out.println("-----------------------------------------");
            for (Producto p : listaProductos) {
                System.out.printf("%-5d %-10s %-25s $%9.2f%n",
                        p.getId(), p.getCodigo(), p.getNombre(), p.getPrecio());
            }
        }
        System.out.println("-----------------------------------------");
        System.out.println("Total productos: " + listaProductos.size());
        mostrarAlerta("Reporte", "Reporte impreso en consola.");
    }

    @FXML
    private void cerrarSesion() {
        try {
            // Confirmar cierre de sesión
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Cerrar Sesión");
            confirm.setHeaderText("¿Está seguro que desea cerrar sesión?");
            confirm.setContentText("Volverá a la pantalla de login.");

            if (confirm.showAndWait().get() != ButtonType.OK) {
                return;
            }

            System.out.println("Cerrando sesión...");

            // Cargar el Login
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/epn/crudproductosmelvasuarez/Login.fxml")
            );

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);

            // Obtener el stage actual
            Stage currentStage = (Stage) btnCerrarSesion.getScene().getWindow();

            // Crear nuevo stage para el login
            Stage loginStage = new Stage();
            loginStage.setTitle("Sistema de Login");
            loginStage.setScene(scene);
            loginStage.show();

            // Cerrar el catálogo
            currentStage.close();

            System.out.println("Sesión cerrada, regresando al login");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cargando Login FXML");
            mostrarAlerta("Error", "No se pudo regresar al login.");
        }
    }

    private void cargarProductoEnFormulario(Producto p) {
        txtId.setText(String.valueOf(p.getId()));
        txtCodigo.setText(p.getCodigo());
        txtNombre.setText(p.getNombre());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
    }

    private void limpiarFormulario() {
        txtId.clear();
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}