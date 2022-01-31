/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;
import Modelo.CursadoModelo;
import Modelo.ProfesorModelo;
import Modelo.AlumnoModelo;
import Modelo.InscripcionModelo;
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
public class CursadoDAO extends SQLQuery{
    private ArrayList<CursadoModelo> notas;
    private CursadoModelo nota;



    public ArrayList<CursadoModelo> traeNotasDAO() {
        notas = new ArrayList();
        try {  
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from Cursado");
            ResultSet hojadeResultados = consulta.executeQuery();

            while (hojadeResultados.next()) {
                nota = new CursadoModelo();
                Modelo.MateriaModelo materia = new Modelo.MateriaModelo();
                nota.setMateria(materia);
                Modelo.AlumnoModelo alumno = new Modelo.AlumnoModelo();
                nota.setAlumno(alumno);
                nota.getAlumno().setDni(hojadeResultados.getLong(1));
                nota.getMateria().setCodigo(hojadeResultados.getInt(2));
                nota.setNota(hojadeResultados.getInt(3));
                notas.add(nota);
               }
            
           this.desconectar();
           
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       return notas;
        
    }
        

     public ArrayList<String> traeDNIAlumnoDAO(){
        ArrayList<String> dni= new ArrayList();
       try {
           this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select alu_dni from alumno");
            ResultSet hojadeResultados = consulta.executeQuery();
            dni.add("");
            while (hojadeResultados.next()) {
               dni.add(Long.toString(hojadeResultados.getLong(1)));
              }
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
      return dni;
   }
    

     public ArrayList<String> traeCodigoMateriaDAO(){
       ArrayList<String> codigo = new ArrayList();
       try {
           this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select mat_cod from materia");
            ResultSet hojadeResultados = consulta.executeQuery();
            codigo.add("");
            while (hojadeResultados.next()) {
               codigo.add(Integer.toString(hojadeResultados.getInt(1)));
              }
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
      return codigo;
   }
     

     public Modelo.MateriaModelo traeMateriaDAO(int codigo) {
        
        Modelo.MateriaModelo materia=new Modelo.MateriaModelo();        
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from materia where mat_cod=?");
            this.consulta.setInt(1, codigo);
            
            ResultSet hojadeResultados = consulta.executeQuery();
            if(hojadeResultados.next()){
                ProfesorModelo profesor = new ProfesorModelo();
                materia.setProfesor(profesor);
                materia.setCodigo(hojadeResultados.getInt(1));
                materia.setNombre(hojadeResultados.getString(2));                
                materia.getProfesor().setDni(hojadeResultados.getLong(3));           
            }
            
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       return materia;
        
    }
     
      public Modelo.AlumnoModelo traeAlumnoDAO(Long dni) {
        
        Modelo.AlumnoModelo alumno=new Modelo.AlumnoModelo();        
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from alumno where alu_dni=?");
            this.consulta.setLong(1, dni);
            
            ResultSet hojadeResultados = consulta.executeQuery();
            if(hojadeResultados.next()){
                InscripcionModelo inscripcion = new InscripcionModelo();
                alumno.setInscripcion(inscripcion);
                alumno.setDni(hojadeResultados.getLong(1));
                alumno.setNombre(hojadeResultados.getString(2));
                alumno.setApellido(hojadeResultados.getString(3));
                alumno.setFechnac(hojadeResultados.getDate(4));
                alumno.setDomicilio(hojadeResultados.getString(5));
                alumno.setTelefono(hojadeResultados.getString(6));
                alumno.getInscripcion().setCodigo(hojadeResultados.getInt(7)); 
                
            }
            
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       return alumno;
               }
       
    public boolean cargaDatosDAO(Modelo.CursadoModelo nota) {
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql ="INSERT INTO cursado (cur_alu_dni, cur_mat_cod, cur_nota) VALUES (?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1, nota.getAlumno().getDni());
            preparedStmt.setInt(2, nota.getMateria().getCodigo());
            preparedStmt.setInt(3, nota.getNota());
            preparedStmt.execute();
            this.desconectar();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
        
    public boolean bajaDAO(JTable tabla){
        int seleccion;
        notas = new ArrayList(traeNotasDAO());
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            this.consulta = this.conn.prepareStatement("DELETE FROM Cursado WHERE cur_alu_dni = ? AND cur_mat_cod = ?");
            seleccion = tabla.getSelectedRow();
            this.consulta.setLong(1, notas.get(seleccion).getAlumno().getDni());
            this.consulta.setInt(2, notas.get(seleccion).getMateria().getCodigo());
            consulta.executeUpdate();
            this.desconectar();           
            return true;
            

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
               return false;
        }
    }
    
     public boolean modificaDAO(Modelo.CursadoModelo nota){
        try{
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE Cursado SET cur_nota=? WHERE cur_alu_dni=? AND cur_mat_cod=?");
            preparedStmt.setInt(1, nota.getNota());
            preparedStmt.setLong(2, nota.getAlumno().getDni());
            preparedStmt.setInt(3, nota.getMateria().getCodigo());      
            preparedStmt.executeUpdate();
            this.desconectar();
             return true;
        }catch(ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
               return false;
        }
    
}
    public boolean notaRepetida(Modelo.CursadoModelo nota){
       
       try {
           this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from cursado where cur_alu_dni=? AND cur_mat_cod=?");
            this.consulta.setLong(1, nota.getAlumno().getDni());
            this.consulta.setInt(2, nota.getMateria().getCodigo());
            ResultSet hojadeResultados = consulta.executeQuery();
            if(hojadeResultados.next()){
                return true;              
            }
            this.desconectar();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return false;
   }
}
