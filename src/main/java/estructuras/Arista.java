package estructuras;

public class Arista<T> {
    private T destino;
    private int peso;
    
    public Arista(T destino) {
        this.destino = destino;
        this.peso = 1; // Peso por defecto
    }
    
    public Arista(T destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }
    
    // Getters y Setters
    public T getDestino() {
        return destino;
    }
    
    public void setDestino(T destino) {
        this.destino = destino;
    }
    
    public int getPeso() {
        return peso;
    }
    
    public void setPeso(int peso) {
        this.peso = peso;
    }
    
    @Override
    public String toString() {
        return "Arista{destino=" + destino + ", peso=" + peso + "}";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Arista<?> arista = (Arista<?>) obj;
        return destino.equals(arista.destino);
    }
    
    @Override
    public int hashCode() {
        return destino.hashCode();
    }
}