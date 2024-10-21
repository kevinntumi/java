module edu.uem.sgh {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires org.slf4j;
    requires org.xerial.sqlitejdbc;
    requires java.sql;
    requires mysql.connector.j;
    requires atlantafx.base;
    requires com.gluonhq.charm.glisten;
    requires org.controlsfx.controls;
    requires java.base;
    requires com.dlsc.formsfx;
    requires org.apache.commons.io;
    requires javafx.swing;
    
    opens edu.uem.sgh to javafx.fxml;
    opens edu.uem.sgh.model.table to javafx.base;
    opens edu.uem.sgh.controller to javafx.fxml;
    opens edu.uem.sgh.controller.administrador to javafx.fxml;
    opens edu.uem.sgh.controller.cliente to javafx.fxml;
    opens edu.uem.sgh.controller.funcionario to javafx.fxml;
    opens edu.uem.sgh.controller.gerente to javafx.fxml;
    exports edu.uem.sgh;
}