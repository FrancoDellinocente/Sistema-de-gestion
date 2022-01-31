/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import Datos.ProfesorDAO;
import java.util.Date;
import java.util.ArrayList;
import javax.swing.JTable;
/**
 *
 * @author franc
 */
public class ProfesorModelo {
    private long dni;
    private String nombre;
    private String apellido;
    private Date fecnac;
    private String dom;
    private String tel;
    private ProfesorDAO profesorDAO = new ProfesorDAO();

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFecnac() {
        return fecnac;
    }

    public void setFecnac(Date fecnac) {
        this.fecnac = fecnac;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    
    public boolean validaDNI(String a) {
        try {
            Long.parseLong(a);
            return false;
        } catch (NumberFormatException nfe) {
            return true;
        }
    }

    public boolean validaCarga(String a) {
        if (a.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean cargaDatos(ProfesorModelo profesor) {

        return profesorDAO.cargaDatosDAO(profesor);

    }

    public ArrayList<Modelo.ProfesorModelo> traeProfesores() {
        return profesorDAO.traeProfesoresDAO();
    }

    public boolean baja(JTable tabla) {
        return profesorDAO.bajaDAO(tabla);

    }

    public boolean modifica(ProfesorModelo profesor) {
        return profesorDAO.modificaDAO(profesor);
    }

    public Modelo.ProfesorModelo traeUNProfesor(Long dni) {
        return profesorDAO.traeUNProfesorDAO(dni);
    }

    public boolean dniRepetido(ProfesorModelo profesor) {
        return !profesorDAO.dniRepetidoDAO(profesor);
    }
}
