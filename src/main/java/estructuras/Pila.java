package estructuras;

import java.util.EmptyStackException;
import java.util.Iterator;

public class Pila<T> implements Iterable<T> {
    private Nodo<T> tope;
    private int tamaño;
    
    // Clase interna para el nodo
    private static class Nodo<T> {
        private T elemento;
        private Nodo<T> siguiente;
        
        public Nodo(T elemento) {
            this.elemento = elemento;
            this.siguiente = null;
        }
        
        public T getElemento() {
            return elemento;
        }
        
        public void setElemento(T elemento) {
            this.elemento = elemento;
        }
        
        public Nodo<T> getSiguiente() {
            return siguiente;
        }
        
        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }
    }
    
    // Constructor
    public Pila() {
        this.tope = null;
        this.tamaño = 0;
    }
    
    // Apilar elemento (push)
    public void push(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        nuevoNodo.setSiguiente(tope);
        tope = nuevoNodo;
        tamaño++;
    }
    
    // Desapilar elemento (pop)
    public T pop() {
        if (estaVacia()) {
            throw new EmptyStackException();
        }
        
        T elemento = tope.getElemento();
        tope = tope.getSiguiente();
        tamaño--;
        return elemento;
    }
    
    // Ver elemento del tope sin eliminarlo (peek)
    public T peek() {
        if (estaVacia()) {
            throw new EmptyStackException();
        }
        
        return tope.getElemento();
    }
    
    // Verificar si la pila está vacía
    public boolean estaVacia() {
        return tope == null;
    }
    
    // Obtener tamaño de la pila
    public int tamaño() {
        return tamaño;
    }
    
    // Limpiar la pila
    public void limpiar() {
        tope = null;
        tamaño = 0;
    }
    
    // Buscar elemento en la pila (retorna posición desde el tope)
    public int buscar(T elemento) {
        Nodo<T> actual = tope;
        int posicion = 1; // Posición 1 es el tope
        
        while (actual != null) {
            if (actual.getElemento().equals(elemento)) {
                return posicion;
            }
            actual = actual.getSiguiente();
            posicion++;
        }
        
        return -1; // No encontrado
    }
    
    // Verificar si contiene un elemento
    public boolean contiene(T elemento) {
        return buscar(elemento) != -1;
    }
    
    // Implementación de Iterator (desde el tope hacia abajo)
    @Override
    public Iterator<T> iterator() {
        return new PilaIterator();
    }
    
    private class PilaIterator implements Iterator<T> {
        private Nodo<T> actual = tope;
        
        @Override
        public boolean hasNext() {
            return actual != null;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new EmptyStackException();
            }
            T elemento = actual.getElemento();
            actual = actual.getSiguiente();
            return elemento;
        }
    }
    
    @Override
    public String toString() {
        if (estaVacia()) {
            return "Pila vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Pila [tope -> base]: ");
        
        Nodo<T> actual = tope;
        while (actual != null) {
            sb.append(actual.getElemento());
            if (actual.getSiguiente() != null) {
                sb.append(" -> ");
            }
            actual = actual.getSiguiente();
        }
        
        return sb.toString();
    }
}