/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import Datos.InscripcionDAO;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;
/**
 *
 * @author franc
 */
public class InscripcionModelo {
    private int codigo;
    private String nombre;
    private Date fecInsc;
    private InscripcionDAO inscripcionDAO = new InscripcionDAO();
    private CarreraModelo carrera;

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

    public Date getFecInsc() {
        return fecInsc;
    }

    public void setFecInsc(Date fecInsc) {
        this.fecInsc = fecInsc;
    }

    public CarreraModelo getCarrera() {
        return carrera;
    }

    public void setCarrera(CarreraModelo carrera) {
        this.carrera = carrera;
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
   
   public ArrayList<Modelo.InscripcionModelo> traeInscripciones() {
       return inscripcionDAO.traeInscripcionesDAO();
   }
   
   
  public ArrayList<String> traeDNI(){
       return inscripcionDAO.traeDNICarreraDAO();      
   }
  
   public Modelo.CarreraModelo traeCarrera(Long dni) {
       this.carrera = inscripcionDAO.traeCarreraDAO(dni);
       return this.carrera;
   }
   
   public boolean cargaDatos(InscripcionModelo inscripcion) {
       return inscripcionDAO.cargaDatosDAO(inscripcion);
   }
   
   public boolean baja(JTable tabla){
       return inscripcionDAO.bajaDAO(tabla);
   }
   
   public boolean modifica(Modelo.InscripcionModelo inscripcion){
       return inscripcionDAO.modificaDAO(inscripcion);
   }
   
   public boolean cargaDniAInscripcion(Long dni, int codigo){
       return inscripcionDAO.cargaDniAInscripcionDAO(dni,codigo);
   }
}
