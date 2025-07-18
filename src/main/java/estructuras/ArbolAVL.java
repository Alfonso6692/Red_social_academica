package estructuras;

/**
 * Implementación de un Árbol AVL (un árbol binario de búsqueda autobalanceado).
 * Usa genéricos para poder almacenar cualquier tipo de dato que sea comparable.
 * @param <T> El tipo de dato a almacenar, debe extender de Comparable.
 */
public class ArbolAVL<T extends Comparable<T>> {

    // --- Nodo Interno del Árbol ---
    private static class Nodo<T> {
        T elemento;
        int altura;
        Nodo<T> izquierdo;
        Nodo<T> derecho;

        Nodo(T elemento) {
            this.elemento = elemento;
            this.altura = 1; // Un nuevo nodo siempre tiene altura 1
        }
    }

    private Nodo<T> raiz;

    // --- Métodos de Ayuda para Altura y Balance ---

    private int altura(Nodo<T> nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    private int obtenerBalance(Nodo<T> nodo) {
        return (nodo == null) ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    private void actualizarAltura(Nodo<T> nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }

    // --- Rotaciones para el Autobalanceo ---

    private Nodo<T> rotacionDerecha(Nodo<T> y) {
        Nodo<T> x = y.izquierdo;
        Nodo<T> T2 = x.derecho;

        // Realizar rotación
        x.derecho = y;
        y.izquierdo = T2;

        // Actualizar alturas
        actualizarAltura(y);
        actualizarAltura(x);

        return x; // Nueva raíz
    }

    private Nodo<T> rotacionIzquierda(Nodo<T> x) {
        Nodo<T> y = x.derecho;
        Nodo<T> T2 = y.izquierdo;

        // Realizar rotación
        y.izquierdo = x;
        x.derecho = T2;

        // Actualizar alturas
        actualizarAltura(x);
        actualizarAltura(y);

        return y; // Nueva raíz
    }

    // --- Operaciones Públicas (Insertar, Eliminar, Buscar) ---

    public void insertar(T elemento) {
        raiz = insertarRecursivo(raiz, elemento);
    }

    private Nodo<T> insertarRecursivo(Nodo<T> nodo, T elemento) {
        // 1. Inserción normal de un árbol binario de búsqueda
        if (nodo == null) {
            return new Nodo<>(elemento);
        }

        if (elemento.compareTo(nodo.elemento) < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, elemento);
        } else if (elemento.compareTo(nodo.elemento) > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, elemento);
        } else {
            return nodo; // Elementos duplicados no son permitidos
        }

        // 2. Actualizar la altura del nodo actual
        actualizarAltura(nodo);

        // 3. Obtener el factor de balance y aplicar rotaciones si es necesario
        int balance = obtenerBalance(nodo);

        // Caso Izquierda-Izquierda
        if (balance > 1 && elemento.compareTo(nodo.izquierdo.elemento) < 0) {
            return rotacionDerecha(nodo);
        }

        // Caso Derecha-Derecha
        if (balance < -1 && elemento.compareTo(nodo.derecho.elemento) > 0) {
            return rotacionIzquierda(nodo);
        }

        // Caso Izquierda-Derecha
        if (balance > 1 && elemento.compareTo(nodo.izquierdo.elemento) > 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && elemento.compareTo(nodo.derecho.elemento) < 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo; // Devolver el nodo (posiblemente nueva raíz)
    }

    public boolean buscar(T elemento) {
        return buscarRecursivo(raiz, elemento);
    }

    private boolean buscarRecursivo(Nodo<T> nodo, T elemento) {
        if (nodo == null) {
            return false;
        }

        if (elemento.equals(nodo.elemento)) {
            return true;
        }

        return elemento.compareTo(nodo.elemento) < 0
               ? buscarRecursivo(nodo.izquierdo, elemento)
               : buscarRecursivo(nodo.derecho, elemento);
    }
    
    // NOTA: El método de eliminación en un árbol AVL es considerablemente más complejo
    // y usualmente se omite en implementaciones básicas de proyectos académicos,
    // a menos que sea un requisito específico.
}