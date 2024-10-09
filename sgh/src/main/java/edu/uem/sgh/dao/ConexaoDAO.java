package edu.uem.sgh.dao;

import javax.swing.*;
import java.sql.*;
public class ConexaoDAO {
    public Connection conectaBD(){
        Connection conn = null;

        try {
            String url= "jdbc:mysql://localhost:3306/mydb?user=root&password=1234";
            conn=DriverManager.getConnection(url);


        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Conexao"+e.getMessage());
        }
        return  conn;
    }

}
