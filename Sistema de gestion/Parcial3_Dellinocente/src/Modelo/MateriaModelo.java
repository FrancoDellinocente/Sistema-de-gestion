/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Datos.MateriaDAO;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author franc
 */
public class MateriaModelo {
    private int codigo;
    private String nombre;
    private MateriaDAO materiaDAO = new MateriaDAO();
    private ProfesorModelo profesor;
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ProfesorModelo getProfesor() {
        return profesor;
    }

    public void setProfesor(ProfesorModelo profesor) {
        this.profesor = profesor;
    }
    
    public boolean validaDNI(String a){
         try{
           Long.parseLong(a);
           return true;
        }catch(NumberFormatException nfe){
            return false;
        }
    }
    
   public boolean validaCarga(String a){
         if(a.length()==0){
             return true;
         }else{
             return false;
         }
     }
   
   public ArrayList<Modelo.MateriaModelo> traeMaterias() {
       return materiaDAO.traeMateriasDAO();
   }
   
   
  public ArrayList<String> traeDNI(){
       return materiaDAO.traeDNIProfesorDAO();      
   }
  
   public Modelo.ProfesorModelo traeProfesor(Long dni) {
       this.profesor = materiaDAO.traeProfesorDAO(dni);
       return this.profesor;
   }
   
   public boolean cargaDatos(MateriaModelo materia) {
       return materiaDAO.cargaDatosDAO(materia);
   }
   
   public boolean baja(JTable tabla){
       return materiaDAO.bajaDAO(tabla);
   }
   
   public boolean modifica(Modelo.MateriaModelo materia){
       return materiaDAO.modificaDAO(materia);
   }
   
   public boolean cargaDniAMateria(Long dni, int codigo){
       return materiaDAO.cargaDniAMateriaDAO(dni,codigo);
   }
}
