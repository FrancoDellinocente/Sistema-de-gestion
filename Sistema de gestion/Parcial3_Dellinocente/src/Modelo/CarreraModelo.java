/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import Datos.CarreraDAO;
import java.util.Date;
import java.util.ArrayList;
import javax.swing.JTable;
/**
 *
 * @author franc
 */
public class CarreraModelo {
    private long codigo;
    private String nombre;
    private long duracion;
    private CarreraDAO carreraDAO = new CarreraDAO();

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getDuracion() {
        return duracion;
    }

    public void setDuracion(long duracion) {
        this.duracion = duracion;
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

    public boolean cargaDatos(CarreraModelo carrera) {

        return carreraDAO.cargaDatosDAO(carrera);

    }

    public ArrayList<Modelo.CarreraModelo> traeCarreras() {
        return carreraDAO.traeCarrerasDAO();
    }

    public boolean baja(JTable tabla) {
        return carreraDAO.bajaDAO(tabla);

    }

    public boolean modifica(CarreraModelo carrera) {
        return carreraDAO.modificaDAO(carrera);
    }

    public Modelo.CarreraModelo traeUNCarrera(Long dni) {
        return carreraDAO.traeUNCarreraDAO(dni);
    }

    public boolean dniRepetido(CarreraModelo carrera) {
        return !carreraDAO.dniRepetidoDAO(carrera);
    }
}
