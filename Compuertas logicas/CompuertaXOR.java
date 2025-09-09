package modelo;

//Compuerta XOR
public class CompuertaXOR extends CompuertaLogica {
    public CompuertaXOR() {
        super("XOR");
    }
    
    @Override
    public boolean operar(boolean... entradas) {
        validarEntradas(2, entradas.length);
        return entradas[0] ^ entradas[1]; // Operador XOR en Java
    }
}
