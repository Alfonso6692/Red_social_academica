package estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación de una Lista Doblemente Enlazada.
 * Permite la inserción y eliminación eficiente de elementos en cualquier posición.
 * @param <T> El tipo de dato que almacenará la lista.
 */
public class ListaDoblementeEnlazada<T> implements Iterable<T> {

    // --- CLASE NODO INTERNA (CORREGIDA) ---
    private static class NodoDoble<T> {
        T elemento; // El dato que guarda el nodo
        NodoDoble<T> siguiente;
        NodoDoble<T> anterior;

        public NodoDoble(T elemento) {
            this.elemento = elemento;
            this.siguiente = null;
            this.anterior = null;
        }
    }

    // --- ATRIBUTOS DE LA LISTA ---
    private NodoDoble<T> cabeza;
    private NodoDoble<T> cola;
    private int tamano;

    // --- CONSTRUCTOR ---
    public ListaDoblementeEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    // --- MÉTODOS PÚBLICOS (CORREGIDOS) ---

    public void agregar(T elemento) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(elemento);
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.siguiente = nuevoNodo;
            nuevoNodo.anterior = cola;
            cola = nuevoNodo;
        }
        tamano++;
    }

    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        NodoDoble<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.elemento;
    }

    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        NodoDoble<T> nodoAEliminar;

        if (tamano == 1) {
            nodoAEliminar = cabeza;
            cabeza = null;
            cola = null;
        } else if (indice == 0) {
            nodoAEliminar = cabeza;
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        } else if (indice == tamano - 1) {
            nodoAEliminar = cola;
            cola = cola.anterior;
            cola.siguiente = null;
        } else {
            NodoDoble<T> actual = cabeza;
            for (int i = 0; i < indice; i++) {
                actual = actual.siguiente;
            }
            nodoAEliminar = actual;
            actual.anterior.siguiente = actual.siguiente;
            actual.siguiente.anterior = actual.anterior;
        }
        
        tamano--;
        return nodoAEliminar.elemento;
    }

    // --- MÉTODOS AUXILIARES ---

    public int tamano() {
        return tamano;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }
    
    // --- ITERADOR ---
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private NodoDoble<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T elemento = actual.elemento;
                actual = actual.siguiente;
                return elemento;
            }
        };
    }
}