package vista;

import java.util.Scanner;

public class VistaCompuertas {
    private Scanner scanner;
    
    public VistaCompuertas() {
        scanner = new Scanner(System.in);
    }
    
    public void mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("    SIMULADOR DE COMPUERTAS LÓGICAS");
        System.out.println("=".repeat(50));
        System.out.println("1. Operar compuerta NOT");
        System.out.println("2. Operar compuerta AND");
        System.out.println("3. Operar compuerta OR");
        System.out.println("4. Operar compuerta XOR");
        System.out.println("0. Salir del programa");
        System.out.println("=".repeat(50));
        System.out.print("Seleccione una opción: ");
    }
    
    public int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public boolean leerEntradaBinaria(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (0/1): ");
            String entrada = scanner.nextLine().trim();
            if (entrada.equals("0")) return false;
            if (entrada.equals("1")) return true;
            mostrarError("Por favor, ingrese solo 0 o 1");
        }
    }
    
    public String leerTipoCompuerta() {
        System.out.println("\nTipos de compuertas disponibles: NOT, AND, OR, XOR");
        System.out.print("Ingrese el tipo de compuerta: ");
        return scanner.nextLine().trim().toUpperCase();
    }
    
    public void mostrarResultado(String tipoCompuerta, boolean[] entradas, boolean resultado) {
        System.out.println("\n" + "-".repeat(40));
        System.out.println("RESULTADO DE LA OPERACIÓN:");
        System.out.print("Compuerta " + tipoCompuerta + " con entrada(s): ");
        for (int i = 0; i < entradas.length; i++) {
            System.out.print(entradas[i] ? "1" : "0");
            if (i < entradas.length - 1) System.out.print(", ");
        }
        System.out.println("\nResultado: " + (resultado ? "1 (VERDADERO)" : "0 (FALSO)"));
        System.out.println("-".repeat(40));
    }
    
    public void mostrarMensaje(String mensaje) {
        System.out.println("\n" + mensaje);
    }
    
    public void mostrarError(String error) {
        System.out.println("\n ERROR: "+  error);
    }
    
    public void mostrarMensajeSalida() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("¡Gracias por usar el Simulador de Compuertas Lógicas!");
        System.out.println("=".repeat(50));
    }
    
    public void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
    
    public void cerrarScanner() {
        scanner.close();
    }
}