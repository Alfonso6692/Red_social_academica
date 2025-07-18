package estructuras;

import java.util.*;
import java.util.function.Predicate;

public class ArrayListPersonalizado<T> implements Iterable<T> {
    private static final int CAPACIDAD_INICIAL = 10;
    private static final double FACTOR_CRECIMIENTO = 1.5;
    
    private Object[] elementos;
    private int tamaño;
    private int capacidad;
    
    // Constructores
    public ArrayListPersonalizado() {
        this(CAPACIDAD_INICIAL);
    }
    
    public ArrayListPersonalizado(int capacidadInicial) {
        if (capacidadInicial < 0) {
            throw new IllegalArgumentException("Capacidad inicial no puede ser negativa");
        }
        this.capacidad = Math.max(capacidadInicial, CAPACIDAD_INICIAL);
        this.elementos = new Object[this.capacidad];
        this.tamaño = 0;
    }
    
    // Agregar elemento al final
    public boolean agregar(T elemento) {
        asegurarCapacidad(tamaño + 1);
        elementos[tamaño] = elemento;
        tamaño++;
        return true;
    }
    
    // Agregar elemento en posición específica
    public void agregar(int indice, T elemento) {
        verificarIndiceParaInsercion(indice);
        asegurarCapacidad(tamaño + 1);
        
        // Desplazar elementos hacia la derecha
        System.arraycopy(elementos, indice, elementos, indice + 1, tamaño - indice);
        elementos[indice] = elemento;
        tamaño++;
    }
    
    // Obtener elemento por índice
    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        verificarIndice(indice);
        return (T) elementos[indice];
    }
    
    // Establecer elemento en posición específica
    @SuppressWarnings("unchecked")
    public T establecer(int indice, T elemento) {
        verificarIndice(indice);
        T elementoAnterior = (T) elementos[indice];
        elementos[indice] = elemento;
        return elementoAnterior;
    }
    
    // Eliminar elemento por índice
    @SuppressWarnings("unchecked")
    public T eliminar(int indice) {
        verificarIndice(indice);
        T elementoEliminado = (T) elementos[indice];
        
        // Desplazar elementos hacia la izquierda
        int numeroElementosAMover = tamaño - indice - 1;
        if (numeroElementosAMover > 0) {
            System.arraycopy(elementos, indice + 1, elementos, indice, numeroElementosAMover);
        }
        
        elementos[--tamaño] = null; // Limpiar referencia
        return elementoEliminado;
    }
    
    // Eliminar elemento específico
    public boolean eliminar(T elemento) {
        int indice = buscar(elemento);
        if (indice >= 0) {
            eliminar(indice);
            return true;
        }
        return false;
    }
    
    // Buscar índice de un elemento
    public int buscar(T elemento) {
        if (elemento == null) {
            for (int i = 0; i < tamaño; i++) {
                if (elementos[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < tamaño; i++) {
                if (elemento.equals(elementos[i])) {
                    return i;
                }
            }
        }
        return -1; // No encontrado
    }
    
    // Verificar si contiene un elemento
    public boolean contiene(T elemento) {
        return buscar(elemento) >= 0;
    }
    
    // Obtener tamaño
    public int tamaño() {
        return tamaño;
    }
    
    // Verificar si está vacío
    public boolean estaVacio() {
        return tamaño == 0;
    }
    
    // Limpiar todos los elementos
    public void limpiar() {
        for (int i = 0; i < tamaño; i++) {
            elementos[i] = null;
        }
        tamaño = 0;
    }
    
    // Filtrar elementos según un predicado
    public ArrayListPersonalizado<T> filtrar(Predicate<T> predicado) {
        ArrayListPersonalizado<T> resultado = new ArrayListPersonalizado<>();
        
        for (int i = 0; i < tamaño; i++) {
            @SuppressWarnings("unchecked")
            T elemento = (T) elementos[i];
            if (predicado.test(elemento)) {
                resultado.agregar(elemento);
            }
        }
        
        return resultado;
    }
    
    // Ordenar elementos usando Comparator
    public void ordenar(Comparator<T> comparador) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Arrays.copyOf(elementos, tamaño);
        Arrays.sort(array, comparador);
        
        for (int i = 0; i < tamaño; i++) {
            elementos[i] = array[i];
        }
    }
    
    // Ordenar elementos (requiere que T implemente Comparable)
    @SuppressWarnings("unchecked")
    public void ordenar() {
        T[] array = (T[]) Arrays.copyOf(elementos, tamaño);
        Arrays.sort(array);
        
        for (int i = 0; i < tamaño; i++) {
            elementos[i] = array[i];
        }
    }
    
    // Convertir a array
    @SuppressWarnings("unchecked")
    public T[] aArray() {
        return (T[]) Arrays.copyOf(elementos, tamaño);
    }
    
    // Obtener sublista
    public ArrayListPersonalizado<T> subLista(int desde, int hasta) {
        if (desde < 0 || hasta > tamaño || desde > hasta) {
            throw new IndexOutOfBoundsException("Índices inválidos");
        }
        
        ArrayListPersonalizado<T> sublista = new ArrayListPersonalizado<>(hasta - desde);
        for (int i = desde; i < hasta; i++) {
            @SuppressWarnings("unchecked")
            T elemento = (T) elementos[i];
            sublista.agregar(elemento);
        }
        
        return sublista;
    }
    
    // Métodos auxiliares privados
    
    private void asegurarCapacidad(int capacidadMinima) {
        if (capacidadMinima > capacidad) {
            int nuevaCapacidad = (int) (capacidad * FACTOR_CRECIMIENTO);
            if (nuevaCapacidad < capacidadMinima) {
                nuevaCapacidad = capacidadMinima;
            }
            elementos = Arrays.copyOf(elementos, nuevaCapacidad);
            capacidad = nuevaCapacidad;
        }
    }
    
    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", Tamaño: " + tamaño);
        }
    }
    
    private void verificarIndiceParaInsercion(int indice) {
        if (indice < 0 || indice > tamaño) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", Tamaño: " + tamaño);
        }
    }
    
    // Implementación de Iterator
    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }
    
    private class ArrayListIterator implements Iterator<T> {
        private int indiceActual = 0;
        private int ultimoIndiceDevuelto = -1;
        
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
            ultimoIndiceDevuelto = indiceActual;
            return (T) elementos[indiceActual++];
        }
        
        @Override
        public void remove() {
            if (ultimoIndiceDevuelto < 0) {
                throw new IllegalStateException();
            }
            
            ArrayListPersonalizado.this.eliminar(ultimoIndiceDevuelto);
            indiceActual = ultimoIndiceDevuelto;
            ultimoIndiceDevuelto = -1;
        }
    }
    
    @Override
    public String toString() {
        if (estaVacio()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        for (int i = 0; i < tamaño; i++) {
            sb.append(elementos[i]);
            if (i < tamaño - 1) {
                sb.append(", ");
            }
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ArrayListPersonalizado<?> otro = (ArrayListPersonalizado<?>) obj;
        
        if (tamaño != otro.tamaño) return false;
        
        for (int i = 0; i < tamaño; i++) {
            if (!Objects.equals(elementos[i], otro.elementos[i])) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int resultado = 1;
        for (int i = 0; i < tamaño; i++) {
            resultado = 31 * resultado + (elementos[i] == null ? 0 : elementos[i].hashCode());
        }
        return resultado;
    }
}

