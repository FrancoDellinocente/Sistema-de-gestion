/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;
import Modelo.AlumnoModelo;
import Modelo.CarreraModelo;
import Modelo.InscripcionModelo;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
//import com.mysql.jdbc.PreparedStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
/**
 *
 * @author franc
 */
public class AlumnoDAO extends SQLQuery {
    private ArrayList <Modelo.AlumnoModelo> alumnos;
    private Modelo.AlumnoModelo alumno;
    public ArrayList<AlumnoModelo> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<AlumnoModelo> alumnos) {
        this.alumnos = alumnos;
    }

    public boolean cargaDatosDAO(Modelo.AlumnoModelo alumno) {
        try {      
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql ="INSERT INTO alumno (alu_dni, alu_nombre, alu_apellido, alu_fec_nac, alu_domicilio, alu_telefono, alu_insc_cod) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1, alumno.getDni());
            preparedStmt.setString(2, alumno.getNombre());
            preparedStmt.setString(3, alumno.getApellido());
            preparedStmt.setDate(4, (Date) alumno.getFechnac());
            preparedStmt.setString(5, alumno.getDomicilio());
            preparedStmt.setString(6, alumno.getTelefono());
            preparedStmt.setLong(7, alumno.getCodinsc());
            preparedStmt.execute();
            this.desconectar();
           return true;
            

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
       
    }
    public ArrayList<Modelo.AlumnoModelo> traeAlumnosDAO() {
        alumnos = new ArrayList();
        try {     
             this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from alumno");
            ResultSet hojadeResultados = consulta.executeQuery();

            while (hojadeResultados.next()) {
                alumno = new Modelo.AlumnoModelo();
                InscripcionModelo inscri = new InscripcionModelo();
                alumno.setInscripcion(inscri);
                alumno.setDni(hojadeResultados.getLong(1));
                alumno.setNombre(hojadeResultados.getString(2));
                alumno.setApellido(hojadeResultados.getString(3));
                alumno.setFechnac(hojadeResultados.getDate(4));
                alumno.setDomicilio(hojadeResultados.getString(5));
                alumno.setTelefono(hojadeResultados.getString(6));
                alumno.getInscripcion().setCodigo(hojadeResultados.getInt(7));
                alumnos.add(alumno);
               }
            this.desconectar();
           
           
        } catch (ClassNotFoundException |SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       return alumnos;
        
    }

        
    public boolean bajaDAO(JTable tabla){
        int seleccion;
        alumnos = new ArrayList(traeAlumnosDAO());
        try {
               this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
           this.consulta = this.conn.prepareStatement("DELETE FROM Alumno WHERE alu_dni = ?");
            seleccion = tabla.getSelectedRow();
            this.consulta.setLong(1, alumnos.get(seleccion).getDni());
            consulta.executeUpdate();
            
            this.desconectar();
            return true;
            

        } catch (ClassNotFoundException |SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
               return false;
        }
    }
    
    public boolean modificaDAO(Modelo.AlumnoModelo alumno){
        try{
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE Alumno SET alu_nombre=?, alu_apellido=?, alu_fec_nac=?, alu_domicilio=?, alu_telefono=?, alu_insc_cod=? WHERE alu_dni=?");
            preparedStmt.setString(1, alumno.getNombre());
            preparedStmt.setString(2, alumno.getApellido());
            preparedStmt.setDate(3, (Date) alumno.getFechnac());
            preparedStmt.setString(4, alumno.getDomicilio());  
            preparedStmt.setString(5, alumno.getTelefono());
            preparedStmt.setLong(6, alumno.getCodinsc());
            preparedStmt.setLong(7, alumno.getDni());
            preparedStmt.executeUpdate();
            this.desconectar();
             return true;
        }catch(ClassNotFoundException |SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
               return false;
        }
        
       
    }
    
    public boolean dniRepetidoDAO(Modelo.AlumnoModelo alumno){
       
       try {
           this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from alumno where alu_dni=?");
            this.consulta.setLong(1, alumno.getDni());
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
  
    public ArrayList<String> traeDNIInscripcionDAO(){
       ArrayList<String> dni = new ArrayList();
       try {
           this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select insc_cod from inscripcion");
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
    
    public boolean cargaDniAAlumnoDAO(Long dni, int codigo){
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql ="UPDATE Alumno SET alu_insc_cod=? WHERE alu_dni=?";
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
    public Modelo.InscripcionModelo traeInscripcionDAO(Long dni) {
        Modelo.InscripcionModelo inscripcion = new Modelo.InscripcionModelo();        
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from inscripcion where insc_cod=?");
            this.consulta.setLong(1, dni);
            
            ResultSet hojadeResultados = consulta.executeQuery();
            if(hojadeResultados.next()){
               CarreraModelo carrera = new CarreraModelo();
               inscripcion.setCarrera(carrera);
                inscripcion.setCodigo((int) hojadeResultados.getLong(1));
                inscripcion.setNombre(hojadeResultados.getString(2));
                inscripcion.setFecInsc(hojadeResultados.getDate(3));
                inscripcion.getCarrera().setCodigo(hojadeResultados.getLong(4));
            }
            
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       return inscripcion;
    }
}
