package org.epn.crudproductosmelvasuarez.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.epn.crudproductosmelvasuarez.modelo.Seguridad;
import org.epn.crudproductosmelvasuarez.modelo.Usuario;
import org.epn.crudproductosmelvasuarez.modelo.UsuarioDAO;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private ComboBox<String> cmbRol;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrarse;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        // Cargar roles en el ComboBox
        cmbRol.getItems().addAll("Administrador", "Vendedor", "Cliente");
        cmbRol.setValue("Seleccionar Rol");

        // Limpiar campos al iniciar
        txtUsuario.clear();
        txtContrasena.clear();
    }

    @FXML
    private void iniciarSesion() {
        String username = txtUsuario.getText().trim();
        String password = txtContrasena.getText().trim();
        String rolSeleccionado = cmbRol.getValue();

        System.out.println("-------------------Datos ingresados--------------");
        System.out.println("Usuario: " + username);
        System.out.println("Contraseña ingresada: " + password);
        System.out.println("Rol seleccionado: " + rolSeleccionado);

        // Validar campos vacíos
        if (username.isEmpty() || password.isEmpty() || rolSeleccionado == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        // Buscar usuario en la base de datos
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            System.out.println("El Usuario no se encuentra registrado");
            mostrarAlerta("Error", "El usuario no registrado intente nuevamente.");
            return;
        }

        System.out.println("Usuario registrado en el sistema");
        System.out.println("Hash almacenado: " + usuario.getPasswordHash());
        System.out.println("Rol en BD: " + usuario.getRol());

        // Validar contraseña con BCrypt
        boolean passwordValida = Seguridad.validarPassword(password, usuario.getPasswordHash());
        System.out.println("¿Contraseña válida? " + passwordValida);

        if (!passwordValida) {
            System.out.println("La Contraseña es incorrecta");
            mostrarAlerta("Error", "Contraseña incorrecta.");
            return;
        }

        // Validar rol
        boolean rolValido = usuario.getRol().equals(rolSeleccionado);
        System.out.println("¿Rol válido? " + rolValido);

        if (!rolValido) {
            System.out.println("Rol incorrecto - Esperado: " + rolSeleccionado + ", Real: " + usuario.getRol());
            mostrarAlerta("Error", "Rol no autorizado. Esta cuenta es de tipo: " + usuario.getRol());
            return;
        }
        System.out.println("Los datos son correctos. Abriendo catálogo...");
        abrirCatalogo(usuario.getRol());
    }

    @FXML
    private void registrarse() {
        String username = txtUsuario.getText().trim();
        String password = txtContrasena.getText().trim();
        String rolSeleccionado = cmbRol.getValue();

        // Validar campos vacíos
        if (username.isEmpty() || password.isEmpty() || rolSeleccionado == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        // Verificar si el usuario ya existe
        Usuario existente = usuarioDAO.buscarPorUsername(username);
        if (existente != null) {
            mostrarAlerta("Error", "El usuario '" + username + "' ya existe.");
            return;
        }

        // Generar hash de la contraseña
        String passwordHash = Seguridad.generarHash(password);

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario(username, passwordHash, rolSeleccionado);

        // Registrar en la base de datos
        if (usuarioDAO.registrarUsuario(nuevoUsuario)) {
            mostrarAlerta("Éxito", "Usuario '" + username + "' registrado exitosamente como " + rolSeleccionado);
            System.out.println("El usuario se encuentra registrado: " + username + " - Rol: " + rolSeleccionado);

            txtUsuario.clear();
            txtContrasena.clear();
            cmbRol.setValue("Cliente");
        } else {
            mostrarAlerta("Error", "No se pudo registrar el usuario.");
        }
    }

    private void abrirCatalogo(String rolUsuario) {
        try {
            System.out.println("Abriendo catálogo para rol: " + rolUsuario);

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/epn/crudproductosmelvasuarez/Catalogo_Productos.fxml")
            );

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 580, 637);

            // Obtener el controlador del catálogo
            CatalogosProdController catalogoController = fxmlLoader.getController();

            if (catalogoController != null) {
                // Configurar el rol
                catalogoController.setRolUsuario(rolUsuario);
                catalogoController.aplicarRestriccionesPorRol();
                System.out.println(" Rol configurado en el catálogo");
            } else {
                System.out.println("Controlador del catálogo es null");
            }

            Stage stage = new Stage();
            stage.setTitle("Catálogo Productos acceso: " + rolUsuario + "\tMelva Suarez");
            stage.setScene(scene);
            stage.show();

            // Cerrar la ventana de login
            Stage loginStage = (Stage) btnIniciarSesion.getScene().getWindow();
            loginStage.close();

            System.out.println("Abriendo el sistema de Catálogo de Productos");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error El sistema no encuentra Catálogo de Productos");
            mostrarAlerta("Error", "No se pudo abrir el catálogo: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}