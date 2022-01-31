/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;
import Modelo.CarreraModelo;
import java.sql.PreparedStatement;
//import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
/**
 *
 * @author franc
 */
public class CarreraDAO  extends SQLQuery{
    private ArrayList<Modelo.CarreraModelo> carreras;
    private Modelo.CarreraModelo carrera;

    public ArrayList<CarreraModelo> getCarreras() {
        return carreras;
    }

    public void setProfesores(ArrayList<CarreraModelo> profesores) {
        this.carreras = carreras;
    }
    
    public boolean cargaDatosDAO(Modelo.CarreraModelo carrera) {
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql = "INSERT INTO carrera (car_cod, car_nombre, car_duracion) VALUES (?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1, carrera.getCodigo());
            preparedStmt.setString(2, carrera.getNombre());
            preparedStmt.setLong(3, carrera.getDuracion());
            preparedStmt.execute();

            this.desconectar();
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    public ArrayList<Modelo.CarreraModelo> traeCarrerasDAO() {
        carreras= new ArrayList();
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from carrera");
            ResultSet hojadeResultados = consulta.executeQuery();

            while (hojadeResultados.next()) {
                carrera = new Modelo.CarreraModelo();
                carrera.setCodigo(hojadeResultados.getLong(1));
                carrera.setNombre(hojadeResultados.getString(2));
                carrera.setDuracion(hojadeResultados.getLong(3));
                carreras.add(carrera);
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return carreras;

    }

    public Modelo.CarreraModelo traeUNCarreraDAO(Long dni) {
        Modelo.CarreraModelo carre = new Modelo.CarreraModelo();
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from carrera where car_cod=?");
            this.consulta.setLong(1, dni);
            ResultSet hojadeResultados = consulta.executeQuery();
            carre.setCodigo(hojadeResultados.getLong(1));
            carre.setNombre(hojadeResultados.getString(2));
            carre.setDuracion(hojadeResultados.getLong(3));
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return carre;

    }



    public boolean bajaDAO(JTable tabla) {
        int seleccion;
        carreras = new ArrayList(traeCarrerasDAO());
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            this.consulta = this.conn.prepareStatement("DELETE FROM Carrera WHERE car_cod = ?");
            seleccion = tabla.getSelectedRow();
            this.consulta.setLong(1, carreras.get(seleccion).getCodigo());
            consulta.executeUpdate();
            this.desconectar();
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modificaDAO(Modelo.CarreraModelo carrera) {
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("UPDATE Carrera SET car_nombre=?, car_duracion=? WHERE car_cod=?");
            this.consulta.setString(1, carrera.getNombre());
            this.consulta.setLong(2, carrera.getDuracion());
            this.consulta.setLong(3, carrera.getCodigo());
            this.consulta.executeUpdate();
            this.desconectar();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    public boolean dniRepetidoDAO(Modelo.CarreraModelo carrera){
       
       try {
           this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from carrera where car_cod=?");
            this.consulta.setLong(1, carrera.getCodigo());
            ResultSet hojadeResultados = consulta.executeQuery();
            if(hojadeResultados.next()){
                return true;              
            }
            this.desconectar();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return false;
   }
}
