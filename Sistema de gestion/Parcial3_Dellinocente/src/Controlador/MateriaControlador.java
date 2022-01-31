/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ProfesorModelo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author franc
 */
public class MateriaControlador implements ActionListener, MouseListener ,  ItemListener{
    
    private Vista.MateriaVista materiaVista;
    private Modelo.MateriaModelo materiaModelo;
    private DefaultTableModel modeloTabla;
    Modelo.ProfesorModelo profesor = new Modelo.ProfesorModelo();
                    
    public Vista.MateriaVista getMateriaVista() {
        return materiaVista;
    }

    public MateriaControlador(Vista.MateriaVista vista, Modelo.MateriaModelo modelo) {
        this.materiaVista = vista;
        this.materiaModelo = modelo;
        this.materiaVista.setVisible(true);
        llenaComboBox();
        llenarFilas(this.materiaVista.getjTableMat());
        escucharBotones();
        this.materiaModelo.setProfesor(profesor);
        this.materiaVista.getjTFCodMat().setEditable(false);
        this.materiaVista.getjTFNomProf().setEditable(false);
       
    }

    public void escucharBotones() {
        this.materiaVista.getjBVolver().addActionListener(this);
        this.materiaVista.getjBGuardar().addActionListener(this);
        this.materiaVista.getjTableMat().addMouseListener(this);
        this.materiaVista.getjBEliminar().addActionListener(this);
        this.materiaVista.getjBModificar().addActionListener(this);
        this.materiaVista.getjComboBox1().addItemListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource().equals(this.materiaVista.getjBGuardar())) {
            if (materiaModelo.validaCarga(this.materiaVista.getjTFNomMat().getText())) {
                JOptionPane.showMessageDialog(null, "Los campos nombre no pueden estar vacios");

            } else {
                this.materiaModelo.setNombre(this.materiaVista.getjTFNomMat().getText());
                if(!this.materiaVista.getjComboBox1().getSelectedItem().toString().equals("")){
                    this.materiaModelo.getProfesor().setDni(Long.parseLong(this.materiaVista.getjComboBox1().getSelectedItem().toString()));
                } 
                if (this.materiaModelo.cargaDatos(materiaModelo)) {
                    JOptionPane.showMessageDialog(null, "Materia cargada correctamente");
                }
                this.limpiarTabla(this.materiaVista.getjTableMat());
                llenarFilas(this.materiaVista.getjTableMat());
                limpiaCuadros();
                

            }
        } else if (evento.getSource().equals(this.materiaVista.getjBVolver())) {
            Vista.MenuPrincipalVista menuVista = new Vista.MenuPrincipalVista();
            MenuPrincipalControlador menuControlador = new MenuPrincipalControlador(menuVista);
            this.materiaVista.dispose();

        } else if (evento.getSource().equals(this.materiaVista.getjBEliminar())) {
            if (this.materiaModelo.baja(this.materiaVista.getjTableMat())) {
                limpiarTabla(this.materiaVista.getjTableMat());
                llenarFilas(this.materiaVista.getjTableMat());
                JOptionPane.showMessageDialog(null, "Materia eliminada correctamente");
                limpiaCuadros();

            } 

        } else if (evento.getSource().equals(this.materiaVista.getjBModificar())) {
            this.materiaModelo.setCodigo(Integer.parseInt(this.materiaVista.getjTFCodMat().getText()));
            this.materiaModelo.setNombre(this.materiaVista.getjTFNomMat().getText());
            if(this.materiaVista.getjComboBox1().getSelectedItem().toString().equals("")){
                this.materiaModelo.getProfesor().setDni(0);
            }else{
                this.materiaModelo.getProfesor().setDni(Long.parseLong(this.materiaVista.getjComboBox1().getSelectedItem().toString()));
            }
            if (this.materiaModelo.modifica(materiaModelo)) {
                JOptionPane.showMessageDialog(null, "Materia modificada correctamente");
            }
            this.limpiarTabla(this.materiaVista.getjTableMat());
            llenarFilas(this.materiaVista.getjTableMat());
            limpiaCuadros();

        }

    }
    
    public void llenaComboBox(){
        ArrayList<String> dni = this.materiaModelo.traeDNI();
        Iterator<String> dniIterator = dni.iterator();
        while(dniIterator.hasNext()){
            this.materiaVista.getjComboBox1().addItem(dniIterator.next());
        }
    }
   

    public String[] dameColumnas() {
        String[] Columna = {"Codigo", "Nombre", "DNI Profesor"};
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
        ArrayList<Modelo.MateriaModelo> materias;
        materias = this.materiaModelo.traeMaterias();
        this.limpiarTabla(this.materiaVista.getjTableMat());
        Object datos[] = new Object[4];
        if (materias.size() > 0) {
            for (int i = 0; i < materias.size(); i++) {
                datos[0] = materias.get(i).getCodigo();
                datos[1] = materias.get(i).getNombre();
                datos[2] = materias.get(i).getProfesor().getDni();
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        materias.clear();

    }

    public void limpiaCuadros() {
        this.materiaVista.getjTFNomMat().setText("");
        this.materiaVista.getjTFCodMat().setText("");
        this.materiaVista.getjComboBox1().setSelectedIndex(0);
        this.materiaVista.getjTFNomProf().setText("");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            int fila = this.materiaVista.getjTableMat().rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.materiaVista.getjTFCodMat().setText(String.valueOf(this.materiaVista.getjTableMat().getValueAt(fila, 0)));
                this.materiaVista.getjTFNomMat().setText(String.valueOf(this.materiaVista.getjTableMat().getValueAt(fila, 1)));
                this.materiaVista.getjComboBox1().setSelectedItem(String.valueOf(this.materiaVista.getjTableMat().getValueAt(fila, 2)));
                
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
         
        if(ie.getItemSelectable().equals(this.materiaVista.getjComboBox1()) && !this.materiaVista.getjComboBox1().getSelectedItem().equals("")){
            Long eleccion = Long.parseLong(this.materiaVista.getjComboBox1().getSelectedItem().toString());
            this.profesor = this.materiaModelo.traeProfesor(eleccion);
            this.materiaVista.getjTFNomProf().setText(this.profesor.getNombre()+" "+this.profesor.getApellido());
        }
    }
}
