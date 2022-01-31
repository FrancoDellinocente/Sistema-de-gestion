/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import java.util.ArrayList;
import javax.swing.JTable;
/**
 *
 * @author franc
 */
public class CursadoModelo {
    private int nota;
    private AlumnoModelo alumno;
    private MateriaModelo materia;
    private Datos.CursadoDAO notaDAO = new Datos.CursadoDAO();

    public CursadoModelo(int nota, AlumnoModelo alumno, MateriaModelo materia) {
        this.nota = nota;
        this.alumno = alumno;
        this.materia = materia;
    }

    public CursadoModelo() {
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public AlumnoModelo getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoModelo alumno) {
        this.alumno = alumno;
    }

    public MateriaModelo getMateria() {
        return materia;
    }

    public void setMateria(MateriaModelo materia) {
        this.materia = materia;
    }
    
    public boolean validaCarga(String a){
         if(a.length()==0){
             return true;
         }else{
             return false;
         }
     }
    
    public ArrayList <CursadoModelo> traeNotas(){
        return notaDAO.traeNotasDAO();
    }
    
    public ArrayList<String> traeDNIAlumno(){
        return notaDAO.traeDNIAlumnoDAO();
    }
    
    public Modelo.AlumnoModelo traeAlumno(Long dni) {
        this.alumno = notaDAO.traeAlumnoDAO(dni);
        return this.alumno;
    }
 
    public ArrayList<String> traeCodigoMateria(){
        return notaDAO.traeCodigoMateriaDAO();
    }
    
    public Modelo.MateriaModelo traeMateria(int codigo){
        this.materia = notaDAO.traeMateriaDAO(codigo);
        return this.materia;
    }
    
    
    public boolean cargaDatos(Modelo.CursadoModelo nota) {
        return notaDAO.cargaDatosDAO(nota);
    }
    
    public boolean baja(JTable tabla){
        return notaDAO.bajaDAO(tabla);
    }
    
     public boolean modifica(Modelo.CursadoModelo nota){
         return notaDAO.modificaDAO(nota);
     }
     
      public boolean notaRepetida(Modelo.CursadoModelo nota){
          return !notaDAO.notaRepetida(nota);
      }
      
      public boolean validaNota(int nota){
          if(nota>=1 && nota<=10){
              return true;
          }else{
              return false;
          }
      }
}
