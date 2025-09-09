package modelo;

// Compuerta OR
public class CompuertaOR extends CompuertaLogica {
    public CompuertaOR() {
        super("OR");
    }
    
    @Override
    public boolean operar(boolean... entradas) {
        validarEntradas(2, entradas.length);
        return entradas[0] || entradas[1];
    }
}