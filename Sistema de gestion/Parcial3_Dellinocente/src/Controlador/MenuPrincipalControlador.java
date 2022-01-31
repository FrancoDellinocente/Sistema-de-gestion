/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Vista.AlumnoVista;
import Vista.MenuPrincipalVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author franc
 */
public class MenuPrincipalControlador implements ActionListener{
    private MenuPrincipalVista menu;

    public MenuPrincipalControlador(MenuPrincipalVista menu) {
        this.menu = menu;
        menu.setVisible(true);
        escucharBotones();
        
    }
    
    public void escucharBotones(){
        this.menu.getjBAlu().addActionListener(this);
        this.menu.getjBMat().addActionListener(this);
        this.menu.getjBProf().addActionListener(this);
        this.menu.getjBNot().addActionListener(this);
        this.menu.getjBCar().addActionListener(this);
        this.menu.getjBInsc().addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.menu.getjBAlu())){
            
            Modelo.AlumnoModelo alumnoModelo = new Modelo.AlumnoModelo();
            AlumnoVista alumnoVista = new Vista.AlumnoVista();
            AlumnoControlador alumnoControlador = new Controlador.AlumnoControlador(alumnoModelo, alumnoVista);
            this.menu.dispose();
            
        }else if(e.getSource().equals(this.menu.getjBMat())){
            Modelo.MateriaModelo materiaModelo = new Modelo.MateriaModelo();
            Vista.MateriaVista materiaVista = new Vista.MateriaVista();
            MateriaControlador materiaControlador = new Controlador.MateriaControlador(materiaVista, materiaModelo);
            this.menu.dispose();
            
        }else if(e.getSource().equals(this.menu.getjBProf())){
            Modelo.ProfesorModelo profesorModelo = new Modelo.ProfesorModelo();
            Vista.ProfesorVista profesorVista = new Vista.ProfesorVista();
            ProfesorControlador profesorControlador = new Controlador.ProfesorControlador(profesorVista, profesorModelo);
            this.menu.dispose();
        }else if(e.getSource().equals(this.menu.getjBNot())){
            Modelo.CursadoModelo notaModelo = new Modelo.CursadoModelo();
            Vista.CursadoVista notaVista = new Vista.CursadoVista();
            CursadoControlador notaControlador = new Controlador.CursadoControlador(notaModelo, notaVista);
            this.menu.dispose();
            
        }else if(e.getSource().equals(this.menu.getjBCar())){
            Modelo.CarreraModelo carreraModelo = new Modelo.CarreraModelo();
            Vista.CarreraVista carreraVista = new Vista.CarreraVista();
            CarreraControlador carreraControlador = new Controlador.CarreraControlador(carreraModelo, carreraVista);
            this.menu.dispose();
            
        }else if(e.getSource().equals(this.menu.getjBInsc())){
            Modelo.InscripcionModelo inscripcionModelo = new Modelo.InscripcionModelo();
            Vista.InscripcionVista inscripcionVista = new Vista.InscripcionVista();
            InscripcionControlador inscripcionControlador = new Controlador.InscripcionControlador(inscripcionModelo, inscripcionVista);
            this.menu.dispose();
            
        }
        
    }
    
}
