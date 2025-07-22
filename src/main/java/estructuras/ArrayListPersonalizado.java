package estructuras;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

public class ArrayListPersonalizado<T> implements Iterable<T> {
    private Object[] elementos;
    private int tamaño;

    public ArrayListPersonalizado() {
        this.elementos = new Object[10];
        this.tamaño = 0;
    }

    public void agregar(T elemento) {
        if (tamaño == elementos.length) {
            elementos = Arrays.copyOf(elementos, (int) (elementos.length * 1.5));
        }
        elementos[tamaño++] = elemento;
    }

    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        return (T) elementos[indice];
    }

    public int tamaño() {
        return tamaño;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        private int indiceActual = 0;

        @Override
        public boolean hasNext() {
            return indiceActual < tamaño;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) elementos[indiceActual++];
        }
    }

    @Override
    public String toString() {
        if (tamaño == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamaño; i++) {
            sb.append(elementos[i]);
            if (i < tamaño - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}