package modelo;

// Compuerta AND
public class CompuertaAND extends CompuertaLogica {
    public CompuertaAND() {
        super("AND");
    }
    
    @Override
    public boolean operar(boolean... entradas) {
        validarEntradas(2, entradas.length);
        return entradas[0] && entradas[1];
    }
}
