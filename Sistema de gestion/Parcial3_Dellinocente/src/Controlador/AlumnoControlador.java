/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author franc
 */
public class AlumnoControlador implements ActionListener, MouseListener, ItemListener{
    private Modelo.AlumnoModelo alumnoModelo;
    private Vista.AlumnoVista alumnoVista;
    private DefaultTableModel modeloTabla;
    Modelo.InscripcionModelo inscripcion = new Modelo.InscripcionModelo();

    public AlumnoControlador(Modelo.AlumnoModelo modelo, Vista.AlumnoVista vista) {
        this.alumnoModelo = modelo;
        this.alumnoVista = vista;
        this.alumnoVista.setVisible(true);
        llenaComboBox();
        this.llenarFilas(this.alumnoVista.getjTableAlu());
        escucharBotones();
        this.alumnoModelo.setInscripcion(inscripcion);
        this.alumnoVista.getjTFNomInsc().setEditable(false);
    }
    
    public void escucharBotones() {
        this.alumnoVista.getjBGuardar().addActionListener(this);
        this.alumnoVista.getjBVolver().addActionListener(this);
        this.alumnoVista.getjBEliminar().addActionListener(this);
        this.alumnoVista.getjTableAlu().addMouseListener(this);
        this.alumnoVista.getjBModificar().addActionListener(this);
        this.alumnoVista.getjComboBox1().addItemListener(this);
    }
    
        public String[] dameColumnas() {
        String[] Columna = {"DNI", "Nombre", "Apellido", "Fecha De Nacimiento", "Domicilio", "Telefono", "Codigo De Inscripcion"};
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
        ArrayList<Modelo.AlumnoModelo> alumnos;
        alumnos = this.alumnoModelo.traeAlumnos();
        this.limpiarTabla(this.alumnoVista.getjTableAlu());
        Object datos[] = new Object[7];
        if (alumnos.size() > 0) {
            for (int i = 0; i < alumnos.size(); i++) {
                datos[0] = alumnos.get(i).getDni();
                datos[1] = alumnos.get(i).getNombre();
                datos[2] = alumnos.get(i).getApellido();
                datos[3] = alumnos.get(i).getFechnac();
                datos[4] = alumnos.get(i).getDomicilio();
                datos[5] = alumnos.get(i).getTelefono();
                datos[6] = alumnos.get(i).getInscripcion().getCodigo();
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        alumnos.clear();

    }

    public void limpiaCuadros() {
        this.alumnoVista.getjTFNomAlu().setText("");
        this.alumnoVista.getjTFApeAlu().setText("");
        this.alumnoVista.getjTFDni().setText("");
        this.alumnoVista.getDateChooserCombo1().setText("");
        this.alumnoVista.getjTFDomAlu().setText("");
        this.alumnoVista.getjTFTelAlu().setText("");
        this.alumnoVista.getjComboBox1().setSelectedIndex(0);
        this.alumnoVista.getjTFNomInsc().setText("");
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
            if (e.getButton() == 1) {
            int fila = this.alumnoVista.getjTableAlu().rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.alumnoVista.getjTFDni().setText(String.valueOf(this.alumnoVista.getjTableAlu().getValueAt(fila, 0)));
                this.alumnoVista.getjTFDni().setEditable(false);
                this.alumnoVista.getjTFNomAlu().setText(String.valueOf(this.alumnoVista.getjTableAlu().getValueAt(fila, 1)));
                this.alumnoVista.getjTFApeAlu().setText(String.valueOf(this.alumnoVista.getjTableAlu().getValueAt(fila, 2)));
                this.alumnoVista.getDateChooserCombo1().setText(String.valueOf(this.alumnoVista.getjTableAlu().getValueAt(fila, 3)));
                this.alumnoVista.getjTFDomAlu().setText(String.valueOf(this.alumnoVista.getjTableAlu().getValueAt(fila, 4)));
                this.alumnoVista.getjTFTelAlu().setText(String.valueOf(this.alumnoVista.getjTableAlu().getValueAt(fila, 5)));
                this.alumnoVista.getjComboBox1().setSelectedItem(String.valueOf(this.alumnoVista.getjTableAlu().getValueAt(fila, 6)));
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {

        if (evento.getSource().equals(this.alumnoVista.getjBGuardar())) {

            if (alumnoModelo.validaCarga(this.alumnoVista.getjTFNomAlu().getText()) || alumnoModelo.validaCarga(this.alumnoVista.getjTFApeAlu().getText()) || alumnoModelo.validaCarga(this.alumnoVista.getjTFDni().getText())) {

                JOptionPane.showMessageDialog(null, "Los campos de DNI, nombre y apellido son obligatorios");

            } else if (alumnoModelo.validaDNI(this.alumnoVista.getjTFDni().getText())) {
                JOptionPane.showMessageDialog(null, "El DNI no es válido");

            } else{
                this.alumnoModelo.setDni(Long.parseLong(this.alumnoVista.getjTFDni().getText()));
                this.alumnoModelo.setNombre(this.alumnoVista.getjTFNomAlu().getText());
                this.alumnoModelo.setApellido(this.alumnoVista.getjTFApeAlu().getText());
                this.alumnoModelo.setFechnac(Date.valueOf(this.alumnoVista.getDateChooserCombo1().getText()));
                this.alumnoModelo.setDomicilio(this.alumnoVista.getjTFDomAlu().getText());
                this.alumnoModelo.setTelefono(this.alumnoVista.getjTFTelAlu().getText());
                if(!this.alumnoVista.getjComboBox1().getSelectedItem().toString().equals("")){
                    this.alumnoModelo.getInscripcion().setCodigo(Integer.parseInt(this.alumnoVista.getjComboBox1().getSelectedItem().toString()));
                }
                if (alumnoModelo.dniRepetido(alumnoModelo)){
                    if (this.alumnoModelo.cargaDatos(alumnoModelo)) {
                        JOptionPane.showMessageDialog(null, "Alumno cargado correctamente");
                    }
                    this.limpiarTabla(this.alumnoVista.getjTableAlu());
                    llenarFilas(this.alumnoVista.getjTableAlu());
                    limpiaCuadros();
                } else {
                JOptionPane.showMessageDialog(null, "Alumno repetido");
                }
            }
        } else if (evento.getSource().equals(this.alumnoVista.getjBVolver())) {
            Vista.MenuPrincipalVista menuVista = new Vista.MenuPrincipalVista();
            MenuPrincipalControlador menuControlador = new MenuPrincipalControlador(menuVista);
            this.alumnoVista.dispose();

        } else if (evento.getSource().equals(this.alumnoVista.getjBEliminar())) {
            if (this.alumnoModelo.baja(this.alumnoVista.getjTableAlu())) {
                limpiarTabla(this.alumnoVista.getjTableAlu());
                llenarFilas(this.alumnoVista.getjTableAlu());
                JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente");
                limpiaCuadros();
                this.alumnoVista.getjTFDni().setEditable(true);
            }

        } else if (evento.getSource().equals(this.alumnoVista.getjBModificar())) {

            this.alumnoModelo.setDni(Long.parseLong(this.alumnoVista.getjTFDni().getText()));
            this.alumnoModelo.setNombre(this.alumnoVista.getjTFNomAlu().getText());
            this.alumnoModelo.setApellido(this.alumnoVista.getjTFApeAlu().getText());
            this.alumnoModelo.setFechnac(Date.valueOf(this.alumnoVista.getDateChooserCombo1().getText()));
            this.alumnoModelo.setDomicilio(this.alumnoVista.getjTFDomAlu().getText());
            this.alumnoModelo.setTelefono(this.alumnoVista.getjTFTelAlu().getText());
            if(this.alumnoVista.getjComboBox1().getSelectedItem().toString().equals("")){
                this.alumnoModelo.getInscripcion().setCodigo(0);
            }else{
                this.alumnoModelo.getInscripcion().setCodigo(Integer.parseInt(this.alumnoVista.getjComboBox1().getSelectedItem().toString()));
            }
            if (this.alumnoModelo.modifica(alumnoModelo)) {
                JOptionPane.showMessageDialog(null, "Alumno modificado correctamente");
            }
            this.limpiarTabla(this.alumnoVista.getjTableAlu());
            llenarFilas(this.alumnoVista.getjTableAlu());
            limpiaCuadros();
            this.alumnoVista.getjTFDni().setEditable(true);
        }

    }
    
    public void llenaComboBox(){
        ArrayList<String> dni = this.alumnoModelo.traeDNI();
        Iterator<String> dniIterator = dni.iterator();
        while(dniIterator.hasNext()){
            this.alumnoVista.getjComboBox1().addItem(dniIterator.next());
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

    @Override
    public void itemStateChanged(ItemEvent ie) {
        if(ie.getItemSelectable().equals(this.alumnoVista.getjComboBox1()) && !this.alumnoVista.getjComboBox1().getSelectedItem().equals("")){
            Long eleccion = Long.parseLong(this.alumnoVista.getjComboBox1().getSelectedItem().toString());
            this.inscripcion = this.alumnoModelo.traeInscripcion(eleccion);
            this.alumnoVista.getjTFNomInsc().setText(this.inscripcion.getNombre());
        }
    }
    
}
