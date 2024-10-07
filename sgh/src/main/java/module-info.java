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
    requires java.desktop;
    requires java.logging;
    
    opens edu.uem.sgh to javafx.fxml;
    opens edu.uem.sgh.controller to javafx.fxml;
    exports edu.uem.sgh;
}
