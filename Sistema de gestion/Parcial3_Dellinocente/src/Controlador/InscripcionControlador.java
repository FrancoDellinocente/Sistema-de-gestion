/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Modelo.CarreraModelo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author franc
 */
public class InscripcionControlador implements ActionListener, MouseListener ,  ItemListener{
    private Vista.InscripcionVista inscripcionVista;
    private Modelo.InscripcionModelo inscripcionModelo;
    private DefaultTableModel modeloTabla;
    Modelo.CarreraModelo carrera = new Modelo.CarreraModelo();
                    
    public Vista.InscripcionVista getInscripcionVista() {
        return inscripcionVista;
    }

    public InscripcionControlador(Modelo.InscripcionModelo modelo, Vista.InscripcionVista vista) {
        this.inscripcionVista = vista;
        this.inscripcionModelo = modelo;
        this.inscripcionVista.setVisible(true);
        llenaComboBox();
        llenarFilas(this.inscripcionVista.getjTable1());
        escucharBotones();
        this.inscripcionModelo.setCarrera(carrera);
        this.inscripcionVista.getjTFInscCod().setEditable(false);
        this.inscripcionVista.getjTFNomCar().setEditable(false);
       
    }

    public void escucharBotones() {
        this.inscripcionVista.getjBVolver().addActionListener(this);
        this.inscripcionVista.getjBGuardar().addActionListener(this);
        this.inscripcionVista.getjTable1().addMouseListener(this);
        this.inscripcionVista.getjBEliminar().addActionListener(this);
        this.inscripcionVista.getjBModificar().addActionListener(this);
        this.inscripcionVista.getjComboBox1().addItemListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource().equals(this.inscripcionVista.getjBGuardar())) {
            if (inscripcionModelo.validaCarga(this.inscripcionVista.getjTFInscNom().getText())) {
                JOptionPane.showMessageDialog(null, "Los campos nombre no pueden estar vacios");

            } else {
                this.inscripcionModelo.setNombre(this.inscripcionVista.getjTFInscNom().getText());
                this.inscripcionModelo.setFecInsc(Date.valueOf(this.inscripcionVista.getDateChooserCombo1().getText()));
                if(!this.inscripcionVista.getjComboBox1().getSelectedItem().toString().equals("")){
                    this.inscripcionModelo.getCarrera().setCodigo(Long.parseLong(this.inscripcionVista.getjComboBox1().getSelectedItem().toString()));
                } 
                if (this.inscripcionModelo.cargaDatos(inscripcionModelo)) {
                    JOptionPane.showMessageDialog(null, "Inscripcion cargada correctamente");
                }
                this.limpiarTabla(this.inscripcionVista.getjTable1());
                llenarFilas(this.inscripcionVista.getjTable1());
                limpiaCuadros();
                

            }
        } else if (evento.getSource().equals(this.inscripcionVista.getjBVolver())) {
            Vista.MenuPrincipalVista menuVista = new Vista.MenuPrincipalVista();
            MenuPrincipalControlador menuControlador = new MenuPrincipalControlador(menuVista);
            this.inscripcionVista.dispose();

        } else if (evento.getSource().equals(this.inscripcionVista.getjBEliminar())) {
            if (this.inscripcionModelo.baja(this.inscripcionVista.getjTable1())) {
                limpiarTabla(this.inscripcionVista.getjTable1());
                llenarFilas(this.inscripcionVista.getjTable1());
                JOptionPane.showMessageDialog(null, "inscripcion eliminada correctamente");
                limpiaCuadros();

            } 

        } else if (evento.getSource().equals(this.inscripcionVista.getjBModificar())) {
            this.inscripcionModelo.setCodigo(Integer.parseInt(this.inscripcionVista.getjTFInscCod().getText()));
            this.inscripcionModelo.setNombre(this.inscripcionVista.getjTFInscNom().getText());
            this.inscripcionModelo.setFecInsc(Date.valueOf(this.inscripcionVista.getDateChooserCombo1().getText()));
            if(this.inscripcionVista.getjComboBox1().getSelectedItem().toString().equals("")){
                this.inscripcionModelo.getCarrera().setCodigo(0);
            }else{
                this.inscripcionModelo.getCarrera().setCodigo(Long.parseLong(this.inscripcionVista.getjComboBox1().getSelectedItem().toString()));
            }
            if (this.inscripcionModelo.modifica(inscripcionModelo)) {
                JOptionPane.showMessageDialog(null, "inscripcion modificada correctamente");
            }
            this.limpiarTabla(this.inscripcionVista.getjTable1());
            llenarFilas(this.inscripcionVista.getjTable1());
            limpiaCuadros();

        }

    }
    
    public void llenaComboBox(){
        ArrayList<String> dni = this.inscripcionModelo.traeDNI();
        Iterator<String> dniIterator = dni.iterator();
        while(dniIterator.hasNext()){
            this.inscripcionVista.getjComboBox1().addItem(dniIterator.next());
        }
    }
   

    public String[] dameColumnas() {
        String[] Columna = {"Codigo", "Nombre", "Fecha De Inscripcion", "Codigo Carrera"};
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
        ArrayList<Modelo.InscripcionModelo> inscripciones;
        inscripciones = this.inscripcionModelo.traeInscripciones();
        this.limpiarTabla(this.inscripcionVista.getjTable1());
        Object datos[] = new Object[4];
        if (inscripciones.size() > 0) {
            for (int i = 0; i < inscripciones.size(); i++) {
                datos[0] = inscripciones.get(i).getCodigo();
                datos[1] = inscripciones.get(i).getNombre();
                datos[2] = inscripciones.get(i).getFecInsc();
                datos[3] = inscripciones.get(i).getCarrera().getCodigo();
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        inscripciones.clear();

    }

    public void limpiaCuadros() {
        this.inscripcionVista.getjTFInscNom().setText("");
        this.inscripcionVista.getjTFInscCod().setText("");
        this.inscripcionVista.getjComboBox1().setSelectedIndex(0);
        this.inscripcionVista.getDateChooserCombo1().setText("");
        this.inscripcionVista.getjTFNomCar().setText("");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            int fila = this.inscripcionVista.getjTable1().rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.inscripcionVista.getjTFInscCod().setText(String.valueOf(this.inscripcionVista.getjTable1().getValueAt(fila, 0)));
                this.inscripcionVista.getjTFInscNom().setText(String.valueOf(this.inscripcionVista.getjTable1().getValueAt(fila, 1)));
                this.inscripcionVista.getDateChooserCombo1().setText(String.valueOf(this.inscripcionVista.getjTable1().getValueAt(fila, 2)));
                this.inscripcionVista.getjComboBox1().setSelectedItem(String.valueOf(this.inscripcionVista.getjTable1().getValueAt(fila, 3)));
                
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {

    }
    
     @Override
    public void itemStateChanged(ItemEvent ie){
         
        if(ie.getItemSelectable().equals(this.inscripcionVista.getjComboBox1()) && !this.inscripcionVista.getjComboBox1().getSelectedItem().equals("")){
            Long eleccion = Long.parseLong(this.inscripcionVista.getjComboBox1().getSelectedItem().toString());
            this.carrera = this.inscripcionModelo.traeCarrera(eleccion);
            this.inscripcionVista.getjTFNomCar().setText(this.carrera.getNombre());
        }
    }
}
