/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;
import java.sql.*;
/**
 *
 * @author franc
 */
public class SQLQuery {
    protected Connection conn;
    protected PreparedStatement consulta;
    protected ResultSet datos;
    
    public void conectar(String servidor, String bd, String usuario, String password) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://"+servidor+"/"+bd,usuario,password);
    }
    
    public void desconectar() throws SQLException{
        this.conn.close();
        this.consulta.close();
    }
    
    public void desconectar(ResultSet rs) throws SQLException{
        this.desconectar();
        rs.close();
    }
}
