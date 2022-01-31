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
public class CarreraControlador implements ActionListener, MouseListener {
    private Vista.CarreraVista carreraVista;
    private Modelo.CarreraModelo carreraModelo;
    private DefaultTableModel modeloTabla;

    public CarreraControlador(Modelo.CarreraModelo modelo,Vista.CarreraVista vista) {
        this.carreraVista = vista;
        this.carreraModelo = modelo;
        this.carreraVista.setVisible(true);        
        llenarFilas(this.carreraVista.getjTable1());
        escucharBotones();
        
    }
    
    public void escucharBotones(){
        this.carreraVista.getjBGuardar().addActionListener(this);
        this.carreraVista.getjBVolver().addActionListener(this);
        this.carreraVista.getjBEliminar().addActionListener(this);
        this.carreraVista.getjTable1().addMouseListener(this);
        this.carreraVista.getjBModificar().addActionListener(this);
    }
    
      

    
    
    public String[] dameColumnas() {
        String[] Columna = {"Codigo", "Nombre", "Duracion"};
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
        ArrayList<Modelo.CarreraModelo> carreras;
        carreras = this.carreraModelo.traeCarreras();
        this.limpiarTabla(this.carreraVista.getjTable1());
        Object datos[] = new Object[3];
        if (carreras.size() > 0) {
            for (int i = 0; i < carreras.size(); i++) {
                datos[0] = carreras.get(i).getCodigo();
                datos[1] = carreras.get(i).getNombre();
                datos[2] = carreras.get(i).getDuracion();
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        carreras.clear();

    }
    
    public void limpiaCuadros(){
        this.carreraVista.getjTFCodCar().setText("");
        this.carreraVista.getjTFNomCar().setText("");
        this.carreraVista.getjTFDurCar().setText("");
    }

    
    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource().equals(this.carreraVista.getjBGuardar())) {
            
            if (carreraModelo.validaCarga(this.carreraVista.getjTFNomCar().getText()) || carreraModelo.validaCarga(this.carreraVista.getjTFDurCar().getText()) || carreraModelo.validaCarga(this.carreraVista.getjTFCodCar().getText())) {
               
                JOptionPane.showMessageDialog(null, "Los campos de codigo, nombre y duracion son obligatorios");
            
            } else if (carreraModelo.validaDNI(this.carreraVista.getjTFCodCar().getText())) {
                JOptionPane.showMessageDialog(null, "El Codigo no es válido");
            
            }else{
                this.carreraModelo.setCodigo(Long.parseLong(this.carreraVista.getjTFCodCar().getText()));
                this.carreraModelo.setNombre(this.carreraVista.getjTFNomCar().getText());
                this.carreraModelo.setDuracion(Long.parseLong(this.carreraVista.getjTFDurCar().getText()));
                if (carreraModelo.dniRepetido(carreraModelo)){
                    if(this.carreraModelo.cargaDatos(carreraModelo)){
                        JOptionPane.showMessageDialog(null, "Carrera cargado correctamente");
                    }
                this.limpiarTabla(this.carreraVista.getjTable1());
                llenarFilas(this.carreraVista.getjTable1());
                limpiaCuadros();                
                
                }else{
                    JOptionPane.showMessageDialog(null, "Carrera repetida");
                }
            }
        }else if(evento.getSource().equals(this.carreraVista.getjBVolver())){
            Vista.MenuPrincipalVista menuVista = new Vista.MenuPrincipalVista();
            MenuPrincipalControlador menuControlador = new MenuPrincipalControlador(menuVista);
            this.carreraVista.dispose();
           
            
        }else if (evento.getSource().equals(this.carreraVista.getjBEliminar())) {
            if(this.carreraModelo.baja(this.carreraVista.getjTable1())){
                limpiarTabla(this.carreraVista.getjTable1());
                llenarFilas(this.carreraVista.getjTable1());
                JOptionPane.showMessageDialog(null, "Carrera eliminada correctamente");
                limpiaCuadros();
            }
            
        }else if(evento.getSource().equals(this.carreraVista.getjBModificar())){
           
                this.carreraModelo.setCodigo(Long.parseLong(this.carreraVista.getjTFCodCar().getText()));
                this.carreraModelo.setNombre(this.carreraVista.getjTFNomCar().getText());
                this.carreraModelo.setDuracion(Long.parseLong(this.carreraVista.getjTFDurCar().getText()));
                if(this.carreraModelo.modifica(carreraModelo)){
                     JOptionPane.showMessageDialog(null, "Carrera modificada correctamente");
                }
                this.limpiarTabla(this.carreraVista.getjTable1());
                llenarFilas(this.carreraVista.getjTable1());
                limpiaCuadros();
                this.carreraVista.getjTFCodCar().setEditable(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
            if(e.getButton() ==1){
            int fila = this.carreraVista.getjTable1().rowAtPoint(e.getPoint());
            if (fila > -1){
                this.carreraVista.getjTFCodCar().setText(String.valueOf(this.carreraVista.getjTable1().getValueAt(fila, 0)));
                this.carreraVista.getjTFCodCar().setEditable(false);
                this.carreraVista.getjTFNomCar().setText(String.valueOf(this.carreraVista.getjTable1().getValueAt(fila, 1)));
                this.carreraVista.getjTFDurCar().setText(String.valueOf(this.carreraVista.getjTable1().getValueAt(fila, 2)));
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
