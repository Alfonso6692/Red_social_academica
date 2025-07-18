package estructuras;

public class NodoAVL<T extends Comparable<T>> {
    private T elemento;
    private NodoAVL<T> izquierdo;
    private NodoAVL<T> derecho;
    private int altura;
    
    public NodoAVL(T elemento) {
        this.elemento = elemento;
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 1;
    }
    
    // Getters y Setters
    public T getElemento() {
        return elemento;
    }
    
    public void setElemento(T elemento) {
        this.elemento = elemento;
    }
    
    public NodoAVL<T> getIzquierdo() {
        return izquierdo;
    }
    
    public void setIzquierdo(NodoAVL<T> izquierdo) {
        this.izquierdo = izquierdo;
    }
    
    public NodoAVL<T> getDerecho() {
        return derecho;
    }
    
    public void setDerecho(NodoAVL<T> derecho) {
        this.derecho = derecho;
    }
    
    public int getAltura() {
        return altura;
    }
    
    public void setAltura(int altura) {
        this.altura = altura;
    }
    
    @Override
    public String toString() {
        return "NodoAVL{elemento=" + elemento + ", altura=" + altura + "}";
    }
}