/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;
import Modelo.InscripcionModelo;
import Modelo.CarreraModelo;
import java.sql.PreparedStatement;
//import com.mysql.jdbc.PreparedStatement;
import java.sql.Date;
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
public class InscripcionDAO extends SQLQuery{
    private Modelo.InscripcionModelo inscripcion;
    private ArrayList<Modelo.InscripcionModelo> inscripciones;

    public ArrayList<InscripcionModelo> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(ArrayList<InscripcionModelo> Inscripciones) {
        this.inscripciones = Inscripciones;
    }
    
    
public ArrayList<Modelo.InscripcionModelo> traeInscripcionesDAO() {
        inscripciones = new ArrayList();
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from inscripcion");
            ResultSet hojadeResultados = consulta.executeQuery();

            while (hojadeResultados.next()) {
                inscripcion = new Modelo.InscripcionModelo();
                CarreraModelo carrera = new CarreraModelo();
                inscripcion.setCarrera(carrera);
                inscripcion.setCodigo(hojadeResultados.getInt(1));
                inscripcion.setNombre(hojadeResultados.getString(2));
                inscripcion.setFecInsc(hojadeResultados.getDate(3));                
                inscripcion.getCarrera().setCodigo(hojadeResultados.getLong(4));
                inscripciones.add(inscripcion);
            }
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       return inscripciones;
        
    }

 
   public ArrayList<String> traeDNICarreraDAO(){
       ArrayList<String> dni = new ArrayList();
       try {
           this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select car_cod from carrera");
            ResultSet hojadeResultados = consulta.executeQuery();
            dni.add("");
            while (hojadeResultados.next()) {
               dni.add(Long.toString(hojadeResultados.getLong(1)));
              }
            this.desconectar();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return dni;
   }
   
    

   
    public boolean cargaDatosDAO(Modelo.InscripcionModelo inscripcion) {
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql ="INSERT INTO inscripcion (insc_cod, insc_nombre, insc_fecha, insc_car_cod) VALUES (null,?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setString(1, inscripcion.getNombre());
            preparedStmt.setDate(2, (Date) inscripcion.getFecInsc());
            preparedStmt.setLong(3, inscripcion.getCarrera().getCodigo());
            preparedStmt.execute();
            this.desconectar();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    public boolean cargaDniAInscripcionDAO(Long dni, int codigo){
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql ="UPDATE Inscripcion SET insc_car_cod=? WHERE insc_cod=?";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1,dni);
            preparedStmt.setInt(2, codigo);
            preparedStmt.execute();
            this.desconectar();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean bajaDAO(JTable tabla){
        int seleccion;
        inscripciones = new ArrayList(traeInscripcionesDAO());
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
           this.consulta = this.conn.prepareStatement("DELETE FROM Inscripcion WHERE insc_cod = ?");
            seleccion = tabla.getSelectedRow();
            this.consulta.setLong(1,inscripciones.get(seleccion).getCodigo());
            consulta.executeUpdate();
            this.desconectar();        
            return true;
            

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
               return false;
        }
    }
    
    public boolean modificaDAO(Modelo.InscripcionModelo inscripcion){
        try{
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE Inscripcion SET insc_nombre=?, insc_fecha=?, insc_car_cod=? WHERE insc_cod=?");
            preparedStmt.setString(1, inscripcion.getNombre());
            preparedStmt.setDate(2, (Date) inscripcion.getFecInsc());
            preparedStmt.setLong(3, inscripcion.getCarrera().getCodigo());
            preparedStmt.setInt(4, inscripcion.getCodigo());           
            preparedStmt.executeUpdate();
            this.desconectar();
             return true;
        }catch(ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
               return false;
        }
    
}
    
    public Modelo.CarreraModelo traeCarreraDAO(Long dni) {
        Modelo.CarreraModelo carrera=new Modelo.CarreraModelo();        
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from carrera where car_cod=?");
            this.consulta.setLong(1, dni);
            
            ResultSet hojadeResultados = consulta.executeQuery();
            if(hojadeResultados.next()){
               
                carrera.setCodigo(hojadeResultados.getLong(1));
                carrera.setNombre(hojadeResultados.getString(2));
                carrera.setDuracion(hojadeResultados.getLong(3));

            }
            
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       return carrera;
    }
}
