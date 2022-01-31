/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author franc
 */
public class ProfesorControlador implements ActionListener, MouseListener{
    private Vista.ProfesorVista profesorVista;
    private Modelo.ProfesorModelo profesorModelo;
    private DefaultTableModel modeloTabla;

    public ProfesorControlador(Vista.ProfesorVista vista, Modelo.ProfesorModelo modelo) {
        this.profesorVista = vista;
        this.profesorModelo = modelo;
        this.profesorVista.setVisible(true);        
        llenarFilas(this.profesorVista.getjTableProf());
        escucharBotones();
        
    }
    
    public void escucharBotones(){
        this.profesorVista.getjBGuardar().addActionListener(this);
        this.profesorVista.getjBVolver().addActionListener(this);
        this.profesorVista.getjBEliminar().addActionListener(this);
        this.profesorVista.getjTableProf().addMouseListener(this);
        this.profesorVista.getjBModificar().addActionListener(this);
    }
    
      

    
    
    public String[] dameColumnas() {
        String[] Columna = {"DNI", "Nombre", "Apellido", "Fecha De Nacimiento", "Domicilio", "Telefono"};
        return Columna;
    }

    public void limpiarTabla(JTable tabla) {
        DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
        int a = tabla.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
    }

    public void llenarFilas(JTable tabla) {
        modeloTabla = new DefaultTableModel(null, dameColumnas());
        ArrayList<Modelo.ProfesorModelo> profesores;
        profesores = this.profesorModelo.traeProfesores();
        this.limpiarTabla(this.profesorVista.getjTableProf());
        Object datos[] = new Object[6];
        if (profesores.size() > 0) {
            for (int i = 0; i < profesores.size(); i++) {
                datos[0] = profesores.get(i).getDni();
                datos[1] = profesores.get(i).getNombre();
                datos[2] = profesores.get(i).getApellido();
                datos[3] = profesores.get(i).getFecnac();
                datos[4] = profesores.get(i).getDom();
                datos[5] = profesores.get(i).getTel();
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        profesores.clear();

    }
    
    public void limpiaCuadros(){
        this.profesorVista.getjTFNomProf().setText("");
        this.profesorVista.getjTFApeProf().setText("");
        this.profesorVista.getjTFDniProf().setText("");
        this.profesorVista.getjTFDomProf().setText("");
        this.profesorVista.getjTFTelProf().setText("");
    }

    
         @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource().equals(this.profesorVista.getjBGuardar())) {
            
            if (profesorModelo.validaCarga(this.profesorVista.getjTFNomProf().getText()) || profesorModelo.validaCarga(this.profesorVista.getjTFApeProf().getText()) || profesorModelo.validaCarga(this.profesorVista.getjTFDniProf().getText())) {
               
                JOptionPane.showMessageDialog(null, "Los campos de DNI, nombre y apellido son obligatorios");
            
            } else if (profesorModelo.validaDNI(this.profesorVista.getjTFDniProf().getText())) {
                JOptionPane.showMessageDialog(null, "El DNI no es válido");
            
            }else{
                this.profesorModelo.setDni(Long.parseLong(this.profesorVista.getjTFDniProf().getText()));
                this.profesorModelo.setNombre(this.profesorVista.getjTFNomProf().getText());
                this.profesorModelo.setApellido(this.profesorVista.getjTFApeProf().getText());
                this.profesorModelo.setFecnac(Date.valueOf(this.profesorVista.getDateChooserCombo2().getText()));
                this.profesorModelo.setDom(this.profesorVista.getjTFDomProf().getText());
                this.profesorModelo.setTel(this.profesorVista.getjTFTelProf().getText());
                if (profesorModelo.dniRepetido(profesorModelo)){
                    if(this.profesorModelo.cargaDatos(profesorModelo)){
                        JOptionPane.showMessageDialog(null, "Profesor cargado correctamente");
                    }
                this.limpiarTabla(this.profesorVista.getjTableProf());
                llenarFilas(this.profesorVista.getjTableProf());
                limpiaCuadros();                
                
                }else{
                    JOptionPane.showMessageDialog(null, "Profesor repetido");
                }
            }
        }else if(evento.getSource().equals(this.profesorVista.getjBVolver())){
            Vista.MenuPrincipalVista menuVista = new Vista.MenuPrincipalVista();
            MenuPrincipalControlador menuControlador = new MenuPrincipalControlador(menuVista);
            this.profesorVista.dispose();
           
            
        }else if (evento.getSource().equals(this.profesorVista.getjBEliminar())) {
            if(this.profesorModelo.baja(this.profesorVista.getjTableProf())){
                limpiarTabla(this.profesorVista.getjTableProf());
                llenarFilas(this.profesorVista.getjTableProf());
                JOptionPane.showMessageDialog(null, "Profesor eliminado correctamente");
                limpiaCuadros();
                this.profesorVista.getjTFDomProf().setEditable(true);
            }
            
        }else if(evento.getSource().equals(this.profesorVista.getjBModificar())){
           
                this.profesorModelo.setDni(Long.parseLong(this.profesorVista.getjTFDniProf().getText()));
                this.profesorModelo.setNombre(this.profesorVista.getjTFNomProf().getText());
                this.profesorModelo.setApellido(this.profesorVista.getjTFApeProf().getText());
                this.profesorModelo.setFecnac(Date.valueOf(this.profesorVista.getDateChooserCombo2().getText()));
                this.profesorModelo.setDom(this.profesorVista.getjTFDomProf().getText());
                this.profesorModelo.setTel(this.profesorVista.getjTFTelProf().getText());
                if(this.profesorModelo.modifica(profesorModelo)){
                     JOptionPane.showMessageDialog(null, "Profesor modificado correctamente");
                }
                this.limpiarTabla(this.profesorVista.getjTableProf());
                llenarFilas(this.profesorVista.getjTableProf());
                limpiaCuadros();
                this.profesorVista.getjTFDniProf().setEditable(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
            if(e.getButton() ==1){
            int fila = this.profesorVista.getjTableProf().rowAtPoint(e.getPoint());
            if (fila > -1){
                this.profesorVista.getjTFDniProf().setText(String.valueOf(this.profesorVista.getjTableProf().getValueAt(fila, 0)));
                this.profesorVista.getjTFDniProf().setEditable(false);
                this.profesorVista.getjTFNomProf().setText(String.valueOf(this.profesorVista.getjTableProf().getValueAt(fila, 1)));
                this.profesorVista.getjTFApeProf().setText(String.valueOf(this.profesorVista.getjTableProf().getValueAt(fila, 2)));
                this.profesorVista.getDateChooserCombo2().setText(String.valueOf(this.profesorVista.getjTableProf().getValueAt(fila, 3)));
                this.profesorVista.getjTFDomProf().setText(String.valueOf(this.profesorVista.getjTableProf().getValueAt(fila, 4)));
                this.profesorVista.getjTFTelProf().setText(String.valueOf(this.profesorVista.getjTableProf().getValueAt(fila, 5)));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
     
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
