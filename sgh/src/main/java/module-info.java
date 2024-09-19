module edu.uem.sgh {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires org.xerial.sqlitejdbc;
    requires java.sql;
    requires mysql.connector.j;
    
    opens edu.uem.sgh to javafx.fxml;
    exports edu.uem.sgh;
}
