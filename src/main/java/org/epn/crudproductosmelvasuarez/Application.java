package org.epn.crudproductosmelvasuarez;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/epn/crudproductosmelvasuarez/Login.fxml")
            );

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            stage.setTitle("Sistema Productos Melva Suarez");
            stage.setScene(scene);
            stage.show();

            System.out.println("Login cargado correctamente");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cargando Login FXML");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}