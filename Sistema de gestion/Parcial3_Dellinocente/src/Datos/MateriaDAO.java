/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Modelo.MateriaModelo;
import Modelo.ProfesorModelo;
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
public class MateriaDAO extends SQLQuery{
    private Modelo.MateriaModelo materia;
    private ArrayList<Modelo.MateriaModelo> materias;


    
    public ArrayList<MateriaModelo> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<MateriaModelo> materias) {
        this.materias = materias;
    }
    
    
public ArrayList<Modelo.MateriaModelo> traeMateriasDAO() {
        materias = new ArrayList();
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from materia");
            ResultSet hojadeResultados = consulta.executeQuery();

            while (hojadeResultados.next()) {
                materia = new Modelo.MateriaModelo();
                ProfesorModelo profesor = new ProfesorModelo();
                materia.setProfesor(profesor);
                materia.setCodigo(hojadeResultados.getInt(1));
                materia.setNombre(hojadeResultados.getString(2));
                materia.getProfesor().setDni(hojadeResultados.getLong(3));
                materias.add(materia);
            }
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       return materias;
        
    }

 
   public ArrayList<String> traeDNIProfesorDAO(){
       ArrayList<String> dni = new ArrayList();
       try {
           this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select prof_dni from profesor");
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
   
    

   
    public boolean cargaDatosDAO(Modelo.MateriaModelo materia) {
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql ="INSERT INTO materia (mat_cod, mat_nombre, mat_prof_dni) VALUES (null,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setString(1, materia.getNombre());
            preparedStmt.setLong(2, materia.getProfesor().getDni());
            preparedStmt.execute();
            this.desconectar();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    public boolean cargaDniAMateriaDAO(Long dni, int codigo){
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql ="UPDATE Materia SET mat_prof_dni=? WHERE mat_cod=?";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1,dni);
            preparedStmt.setLong(2,codigo);
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
        materias = new ArrayList(traeMateriasDAO());
        try {
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
           this.consulta = this.conn.prepareStatement("DELETE FROM Materia WHERE mat_cod = ?");
            seleccion = tabla.getSelectedRow();
            this.consulta.setLong(1, materias.get(seleccion).getCodigo());
            consulta.executeUpdate();
            this.desconectar();        
            return true;
            

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
               return false;
        }
    }
    
    public boolean modificaDAO(Modelo.MateriaModelo materia){
        try{
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE Materia SET mat_nombre=?, mat_prof_dni=? WHERE mat_cod=?");
            preparedStmt.setString(1, materia.getNombre());
            preparedStmt.setLong(2, materia.getProfesor().getDni());
            preparedStmt.setInt(3, materia.getCodigo());           
            preparedStmt.executeUpdate();
            this.desconectar();
             return true;
        }catch(ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
               return false;
        }
    
}
    
    public Modelo.ProfesorModelo traeProfesorDAO(Long dni) {
        Modelo.ProfesorModelo profesor=new Modelo.ProfesorModelo();        
        try {    
            this.conectar("127.0.0.1", "proyectodb", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from profesor where prof_dni=?");
            this.consulta.setLong(1, dni);
            
            ResultSet hojadeResultados = consulta.executeQuery();
            if(hojadeResultados.next()){
               
                profesor.setDni(hojadeResultados.getLong(1));
                profesor.setNombre(hojadeResultados.getString(2));
                profesor.setApellido(hojadeResultados.getString(3));
                profesor.setFecnac(hojadeResultados.getDate(4));
                profesor.setDom(hojadeResultados.getString(5));
                profesor.setTel(hojadeResultados.getString(6));
            }
            
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       return profesor;
    }
}
