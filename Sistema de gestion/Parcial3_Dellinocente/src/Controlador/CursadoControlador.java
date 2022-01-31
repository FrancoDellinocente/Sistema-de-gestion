/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Modelo.CursadoModelo;
import Vista.CursadoVista;
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
public class CursadoControlador implements ActionListener, MouseListener,  ItemListener{
    private Modelo.CursadoModelo notaModelo;
    private Vista.CursadoVista notaVista;
    private DefaultTableModel modeloTabla;
    private Modelo.AlumnoModelo alumno = new Modelo.AlumnoModelo();
    private Modelo.MateriaModelo materia = new Modelo.MateriaModelo();

    public CursadoControlador(CursadoModelo notaModelo, CursadoVista notaVista) {
        this.notaModelo = notaModelo;
        this.notaVista = notaVista;
        this.notaVista.setVisible(true);
        this.llenarFilas(this.notaVista.getjTNotas());
        llenaComboBoxAlumno();
        this.notaVista.getjTFNomAlu().setEditable(false);
        llenaComboBoxMateria();
        this.notaVista.getjTFNomMat().setEditable(false);
        this.notaModelo.setAlumno(alumno);
        this.notaModelo.setMateria(materia);
        escucharBotones();
    }
    
    public void escucharBotones(){
       this.notaVista.getjBGuardar().addActionListener(this);
       this.notaVista.getjBEliminar().addActionListener(this);
       this.notaVista.getjBModificar().addActionListener(this);
       this.notaVista.getjTNotas().addMouseListener(this);
       this.notaVista.getjBVolver().addActionListener(this);
       this.notaVista.getjComboBoxMat().addItemListener(this);
       this.notaVista.getjComboBoxAlu().addItemListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource().equals(this.notaVista.getjBGuardar())) {
            if(notaModelo.validaCarga(this.notaVista.getjComboBoxMat().toString()) || notaModelo.validaCarga(this.notaVista.getjComboBoxAlu().toString())){
                JOptionPane.showMessageDialog(null, "Debe seleccionar un alumno y una materia");
            }else if(notaModelo.validaNota(Integer.parseInt(this.notaVista.getjTFNota().getText()))){
                this.notaModelo.getAlumno().setDni(Long.parseLong(this.notaVista.getjComboBoxAlu().getSelectedItem().toString()));
                this.notaModelo.getMateria().setCodigo(Integer.parseInt(this.notaVista.getjComboBoxMat().getSelectedItem().toString()));
                this.notaModelo.setNota(Integer.parseInt(this.notaVista.getjTFNota().getText()));
                if(notaModelo.notaRepetida(notaModelo)){
                    if(this.notaModelo.cargaDatos(notaModelo)){
                        JOptionPane.showMessageDialog(null, "Nota cargada");
                    }
                    this.limpiarTabla(this.notaVista.getjTNotas());
                    llenarFilas(this.notaVista.getjTNotas());
                    limpiaCuadros();
                
                }else{
                    JOptionPane.showMessageDialog(null, "Nota repetida");
                }
            }else{
                JOptionPane.showMessageDialog(null, "La nota debe estar entre 1 y 10");
            }
        }else if(evento.getSource().equals(this.notaVista.getjBEliminar())){
            if(this.notaModelo.baja(this.notaVista.getjTNotas())){
                limpiarTabla(this.notaVista.getjTNotas());
                llenarFilas(this.notaVista.getjTNotas());
                JOptionPane.showMessageDialog(null, "Nota eliminada correctamente");
                limpiaCuadros();    
            }
            
        }else if(evento.getSource().equals(this.notaVista.getjBModificar())){
            this.notaModelo.getAlumno().setDni(Long.parseLong(this.notaVista.getjComboBoxAlu().getSelectedItem().toString()));
                this.notaModelo.getMateria().setCodigo(Integer.parseInt(this.notaVista.getjComboBoxMat().getSelectedItem().toString()));
                this.notaModelo.setNota(Integer.parseInt(this.notaVista.getjTFNota().getText()));
                if(this.notaModelo.modifica(notaModelo)){
                    JOptionPane.showMessageDialog(null, "Nota modificada");
                }
                this.limpiarTabla(this.notaVista.getjTNotas());
                llenarFilas(this.notaVista.getjTNotas());
                limpiaCuadros();
            
        }else if(evento.getSource().equals(this.notaVista.getjBVolver())){
            Vista.MenuPrincipalVista menuVista = new Vista.MenuPrincipalVista();
            Controlador.MenuPrincipalControlador menuControlador = new MenuPrincipalControlador(menuVista);
            this.notaVista.dispose();
        }
    }
    
    
    public void llenaComboBoxAlumno(){
        ArrayList<String> dni = this.notaModelo.traeDNIAlumno();
        Iterator<String> dniIterator = dni.iterator();
        while(dniIterator.hasNext()){
            this.notaVista.getjComboBoxAlu().addItem(dniIterator.next());
        }
    }
    
    public void llenaComboBoxMateria(){
        ArrayList<String> materia = this.notaModelo.traeCodigoMateria();
        Iterator<String> materiaIterator = materia.iterator();
        while(materiaIterator.hasNext()){
            this.notaVista.getjComboBoxMat().addItem(materiaIterator.next());
        }
    }

    public void llenarFilas(JTable tabla) {
        modeloTabla = new DefaultTableModel(null, dameColumnas());
        ArrayList<Modelo.CursadoModelo> notas;
        notas = this.notaModelo.traeNotas();
        this.limpiarTabla(this.notaVista.getjTNotas());
        Object datos[] = new Object[3];
        if (notas.size() > 0) {
            for (int i = 0; i < notas.size(); i++) {
                datos[0] = notas.get(i).getAlumno().getDni();
                datos[1] = notas.get(i).getMateria().getCodigo();
                datos[2] = notas.get(i).getNota();
                modeloTabla.addRow(datos);
            }
        }
        tabla.setModel(modeloTabla);
        notas.clear();

    }
    
     public void limpiarTabla(JTable tabla) {
        DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
        int a = tabla.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
    }
    
     public String[] dameColumnas() {
        String[] Columna = {"DNI Alumno", "Codigo Materia", "Nota"};
        return Columna;
    }
     
      public void limpiaCuadros() {
          this.notaVista.getjTFNota().setText("");
          this.notaVista.getjComboBoxMat().setSelectedIndex(0);
          this.notaVista.getjComboBoxMat().setEnabled(true);
          this.notaVista.getjComboBoxAlu().setSelectedIndex(0);
          this.notaVista.getjComboBoxAlu().setEnabled(true);
          this.notaVista.getjTFNomMat().setText("");
          this.notaVista.getjTFNomAlu().setText("");
       }
      
       @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            int fila = this.notaVista.getjTNotas().rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.notaVista.getjComboBoxAlu().setSelectedItem(String.valueOf(this.notaVista.getjTNotas().getValueAt(fila, 0)));
                this.notaVista.getjComboBoxAlu().setEnabled(false);
                this.notaVista.getjComboBoxMat().setSelectedItem(String.valueOf(this.notaVista.getjTNotas().getValueAt(fila, 1)));
                this.notaVista.getjComboBoxMat().setEnabled(false);
                this.notaVista.getjTFNota().setText(String.valueOf(this.notaVista.getjTNotas().getValueAt(fila, 2)));
               
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
        if(ie.getItemSelectable().equals(this.notaVista.getjComboBoxMat()) && !this.notaVista.getjComboBoxMat().getSelectedItem().equals("")){
            int eleccion = Integer.parseInt(this.notaVista.getjComboBoxMat().getSelectedItem().toString());
            this.materia = this.notaModelo.traeMateria(eleccion);
            this.notaVista.getjTFNomMat().setText(this.materia.getNombre());
        }else if(ie.getItemSelectable().equals(this.notaVista.getjComboBoxAlu()) && !this.notaVista.getjComboBoxAlu().getSelectedItem().equals("")){
            Long eleccion = Long.parseLong(this.notaVista.getjComboBoxAlu().getSelectedItem().toString());
            this.alumno = this.notaModelo.traeAlumno(eleccion);
            this.notaVista.getjTFNomAlu().setText(this.alumno.getNombre()+" "+this.alumno.getApellido());
        }
    }
    
   

}


