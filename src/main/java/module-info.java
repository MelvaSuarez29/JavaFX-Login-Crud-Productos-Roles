module org.epn.crudproductosmelvasuarez {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jbcrypt;

    exports org.epn.crudproductosmelvasuarez;
    opens org.epn.crudproductosmelvasuarez.modelo to javafx.fxml;
    opens org.epn.crudproductosmelvasuarez.controlador to javafx.fxml;
}