package controlador;

import modelo.*;
import vista.*;

public class ControladorCompuertas {
    private CompuertaNOT compuertaNOT;
    private CompuertaAND compuertaAND;
    private CompuertaOR compuertaOR;
    private CompuertaXOR compuertaXOR;
    private VistaCompuertas vista;
    
    public ControladorCompuertas() {
        // Inicialización de las compuertas del modelo
        compuertaNOT = new CompuertaNOT();
        compuertaAND = new CompuertaAND();
        compuertaOR = new CompuertaOR();
        compuertaXOR = new CompuertaXOR();
        
        // Inicialización de la vista
        vista = new VistaCompuertas();
    }
    
    public void iniciar() {
        boolean continuar = true;
        
        vista.mostrarMensaje("Bienvenido al Simulador de Compuertas Lógicas");
        
        while (continuar) {
            try {
                vista.mostrarMenuPrincipal();
                int opcion = vista.leerOpcion();
                
                switch (opcion) {
                    case 1:
                        operarNOT();
                        break;
                    case 2:
                        operarAND();
                        break;
                    case 3:
                        operarOR();
                        break;
                    case 4:
                        operarXOR();
                        break;
                    case 0:
                        continuar = false;
                        vista.mostrarMensajeSalida();
                        break;
                    default:
                        vista.mostrarError("Opción no válida. Por favor, seleccione una opción del menú (0-4).");
                }
                
                // Pausar solo si no es salida y la opción es válida
                if (continuar && opcion >= 0 && opcion <= 5) {
                    vista.pausar();
                }
                
            } catch (IllegalArgumentException e) {
                vista.mostrarError("Error en la operación: " + e.getMessage());
                vista.pausar();
            } catch (Exception e) {
                vista.mostrarError("Ha ocurrido un error inesperado: " + e.getMessage());
                vista.pausar();
            }
        }
        
        // Cerrar recursos al finalizar
        vista.cerrarScanner();
    }
    
    private void operarNOT() {
        vista.mostrarMensaje("=== OPERACIÓN NOT ===");
        vista.mostrarMensaje("La compuerta NOT invierte el valor de entrada");
        
        boolean entrada = vista.leerEntradaBinaria("Ingrese el valor de entrada");
        
        try {
            boolean resultado = compuertaNOT.operar(entrada);
            vista.mostrarResultado("NOT", new boolean[]{entrada}, resultado);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }
    
    private void operarAND() {
        vista.mostrarMensaje("=== OPERACIÓN AND ===");
        vista.mostrarMensaje("La compuerta AND devuelve 1 solo si ambas entradas son 1");
        
        boolean entradaA = vista.leerEntradaBinaria("Ingrese el valor de A");
        boolean entradaB = vista.leerEntradaBinaria("Ingrese el valor de B");
        
        try {
            boolean resultado = compuertaAND.operar(entradaA, entradaB);
            vista.mostrarResultado("AND", new boolean[]{entradaA, entradaB}, resultado);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }
    
    private void operarOR() {
        vista.mostrarMensaje("=== OPERACIÓN OR ===");
        vista.mostrarMensaje("La compuerta OR devuelve 1 si al menos una entrada es 1");
        
        boolean entradaA = vista.leerEntradaBinaria("Ingrese el valor de A");
        boolean entradaB = vista.leerEntradaBinaria("Ingrese el valor de B");
        
        try {
            boolean resultado = compuertaOR.operar(entradaA, entradaB);
            vista.mostrarResultado("OR", new boolean[]{entradaA, entradaB}, resultado);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }
    
    private void operarXOR() {
        vista.mostrarMensaje("=== OPERACIÓN XOR ===");
        vista.mostrarMensaje("La compuerta XOR devuelve 1 solo si las entradas son diferentes");
        
        boolean entradaA = vista.leerEntradaBinaria("Ingrese el valor de A");
        boolean entradaB = vista.leerEntradaBinaria("Ingrese el valor de B");
        
        try {
            boolean resultado = compuertaXOR.operar(entradaA, entradaB);
            vista.mostrarResultado("XOR", new boolean[]{entradaA, entradaB}, resultado);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }
}
