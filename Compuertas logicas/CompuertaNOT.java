package modelo;

// Compuerta NOT
public class CompuertaNOT extends CompuertaLogica {
    public CompuertaNOT() {
        super("NOT");
    }
    
    @Override
    public boolean operar(boolean... entradas) {
        validarEntradas(1, entradas.length);
        return !entradas[0];
    }
}