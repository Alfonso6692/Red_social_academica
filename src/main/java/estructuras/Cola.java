package estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Cola<T> implements Iterable<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
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
    public Cola() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }
    
    // Encolar elemento (offer/enqueue)
    public void encolar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            cola = nuevoNodo;
        }
        
        tamaño++;
    }
    
    // Desencolar elemento (poll/dequeue)
    public T desencolar() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola está vacía");
        }
        
        T elemento = cabeza.getElemento();
        cabeza = cabeza.getSiguiente();
        
        if (cabeza == null) {
            cola = null; // La cola quedó vacía
        }
        
        tamaño--;
        return elemento;
    }
    
    // Ver primer elemento sin eliminarlo (peek)
    public T primero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola está vacía");
        }
        
        return cabeza.getElemento();
    }
    
    // Ver último elemento sin eliminarlo
    public T ultimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola está vacía");
        }
        
        return cola.getElemento();
    }
    
    // Verificar si la cola está vacía
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    // Obtener tamaño de la cola
    public int tamaño() {
        return tamaño;
    }
    
    // Limpiar la cola
    public void limpiar() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }
    
    // Buscar elemento en la cola
    public boolean contiene(T elemento) {
        Nodo<T> actual = cabeza;
        
        while (actual != null) {
            if (actual.getElemento().equals(elemento)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        
        return false;
    }
    
    // Obtener posición de un elemento (0 = primero)
    public int buscar(T elemento) {
        Nodo<T> actual = cabeza;
        int posicion = 0;
        
        while (actual != null) {
            if (actual.getElemento().equals(elemento)) {
                return posicion;
            }
            actual = actual.getSiguiente();
            posicion++;
        }
        
        return -1; // No encontrado
    }
    
    // Implementación de Iterator (desde cabeza hacia cola)
    @Override
    public Iterator<T> iterator() {
        return new ColaIterator();
    }
    
    private class ColaIterator implements Iterator<T> {
        private Nodo<T> actual = cabeza;
        
        @Override
        public boolean hasNext() {
            return actual != null;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T elemento = actual.getElemento();
            actual = actual.getSiguiente();
            return elemento;
        }
    }
    
    @Override
    public String toString() {
        if (estaVacia()) {
            return "Cola vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Cola [cabeza -> cola]: ");
        
        Nodo<T> actual = cabeza;
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