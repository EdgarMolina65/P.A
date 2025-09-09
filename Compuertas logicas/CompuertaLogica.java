package modelo;

// Clase abstracta base para todas las compuertas
public abstract class CompuertaLogica {
    protected String nombre;
    
    public CompuertaLogica(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    // Método abstracto que implementarán las subclases
    public abstract boolean operar(boolean... entradas);
    
    // Método para validar número de entradas
    protected void validarEntradas(int esperadas, int recibidas) {
        if (recibidas != esperadas) {
            throw new IllegalArgumentException(
                "La compuerta " + nombre + " requiere " + esperadas + 
                " entrada(s), pero recibió " + recibidas
            );
        }
    }
}
