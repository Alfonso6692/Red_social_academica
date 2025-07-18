package estructuras;

import java.util.*;

public class Grafo<T> {
    private Map<T, List<Arista<T>>> listaAdyacencia;
    private boolean esDirigido;
    
    // Constructor
    public Grafo(boolean esDirigido) {
        this.listaAdyacencia = new HashMap<>();
        this.esDirigido = esDirigido;
    }
    
    public Grafo() {
        this(false); // Por defecto no dirigido
    }
    
    // Agregar vértice
    public void agregarVertice(T vertice) {
        if (!listaAdyacencia.containsKey(vertice)) {
            listaAdyacencia.put(vertice, new ArrayList<>());
        }
    }
    
    // Agregar arista
    public void agregarArista(T origen, T destino) {
        agregarArista(origen, destino, 1);
    }
    
    public void agregarArista(T origen, T destino, int peso) {
        // Asegurar que ambos vértices existan
        agregarVertice(origen);
        agregarVertice(destino);
        
        // Agregar arista de origen a destino
        listaAdyacencia.get(origen).add(new Arista<>(destino, peso));
        
        // Si el grafo no es dirigido, agregar arista de destino a origen
        if (!esDirigido && !origen.equals(destino)) {
            listaAdyacencia.get(destino).add(new Arista<>(origen, peso));
        }
    }
    
    // Eliminar vértice
    public boolean eliminarVertice(T vertice) {
        if (!listaAdyacencia.containsKey(vertice)) {
            return false;
        }
        
        // Eliminar todas las aristas que apuntan a este vértice
        for (List<Arista<T>> aristas : listaAdyacencia.values()) {
            aristas.removeIf(arista -> arista.getDestino().equals(vertice));
        }
        
        // Eliminar el vértice
        listaAdyacencia.remove(vertice);
        return true;
    }
    
    // Eliminar arista
    public boolean eliminarArista(T origen, T destino) {
        if (!listaAdyacencia.containsKey(origen)) {
            return false;
        }
        
        boolean eliminado = listaAdyacencia.get(origen)
            .removeIf(arista -> arista.getDestino().equals(destino));
        
        // Si el grafo no es dirigido, eliminar también la arista inversa
        if (!esDirigido && eliminado && listaAdyacencia.containsKey(destino)) {
            listaAdyacencia.get(destino)
                .removeIf(arista -> arista.getDestino().equals(origen));
        }
        
        return eliminado;
    }
    
    // Obtener vecinos de un vértice
    public List<T> obtenerVecinos(T vertice) {
        if (!listaAdyacencia.containsKey(vertice)) {
            return new ArrayList<>();
        }
        
        List<T> vecinos = new ArrayList<>();
        for (Arista<T> arista : listaAdyacencia.get(vertice)) {
            vecinos.add(arista.getDestino());
        }
        return vecinos;
    }
    
    // Verificar si existe arista entre dos vértices
    public boolean existeArista(T origen, T destino) {
        if (!listaAdyacencia.containsKey(origen)) {
            return false;
        }
        
        return listaAdyacencia.get(origen).stream()
            .anyMatch(arista -> arista.getDestino().equals(destino));
    }
    
    // Obtener peso de arista
    public int obtenerPesoArista(T origen, T destino) {
        if (!listaAdyacencia.containsKey(origen)) {
            return -1;
        }
        
        return listaAdyacencia.get(origen).stream()
            .filter(arista -> arista.getDestino().equals(destino))
            .mapToInt(Arista::getPeso)
            .findFirst()
            .orElse(-1);
    }
    
    // Obtener todos los vértices
    public Set<T> obtenerVertices() {
        return new HashSet<>(listaAdyacencia.keySet());
    }
    
    // Verificar si el grafo contiene un vértice
    public boolean contieneVertice(T vertice) {
        return listaAdyacencia.containsKey(vertice);
    }
    
    // Obtener número de vértices
    public int numeroVertices() {
        return listaAdyacencia.size();
    }
    
    // Obtener número de aristas
    public int numeroAristas() {
        int contador = 0;
        for (List<Arista<T>> aristas : listaAdyacencia.values()) {
            contador += aristas.size();
        }
        
        // Si el grafo no es dirigido, cada arista se cuenta dos veces
        return esDirigido ? contador : contador / 2;
    }
    
    // Búsqueda en anchura (BFS)
    public List<T> buscarRuta(T origen, T destino) {
        if (!contieneVertice(origen) || !contieneVertice(destino)) {
            return new ArrayList<>();
        }
        
        Queue<T> cola = new LinkedList<>();
        Map<T, T> padres = new HashMap<>();
        Set<T> visitados = new HashSet<>();
        
        cola.offer(origen);
        visitados.add(origen);
        padres.put(origen, null);
        
        while (!cola.isEmpty()) {
            T actual = cola.poll();
            
            if (actual.equals(destino)) {
                return reconstruirRuta(padres, origen, destino);
            }
            
            for (T vecino : obtenerVecinos(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    padres.put(vecino, actual);
                    cola.offer(vecino);
                }
            }
        }
        
        return new ArrayList<>(); // No se encontró ruta
    }
    
    // Sugerir conexiones (amigos de amigos)
    public Map<T, Integer> sugerirConexiones(T vertice) {
        Map<T, Integer> sugerencias = new HashMap<>();
        Set<T> amigos = new HashSet<>(obtenerVecinos(vertice));
        
        // Para cada amigo, revisar sus amigos
        for (T amigo : amigos) {
            for (T amigoDelAmigo : obtenerVecinos(amigo)) {
                // No sugerir a sí mismo ni a amigos actuales
                if (!amigoDelAmigo.equals(vertice) && !amigos.contains(amigoDelAmigo)) {
                    sugerencias.put(amigoDelAmigo, 
                        sugerencias.getOrDefault(amigoDelAmigo, 0) + 1);
                }
            }
        }
        
        return sugerencias;
    }
    
    // Método auxiliar para reconstruir ruta
    private List<T> reconstruirRuta(Map<T, T> padres, T origen, T destino) {
        List<T> ruta = new ArrayList<>();
        T actual = destino;
        
        while (actual != null) {
            ruta.add(0, actual);
            actual = padres.get(actual);
        }
        
        return ruta;
    }
    
    // Verificar si el grafo está vacío
    public boolean estaVacio() {
        return listaAdyacencia.isEmpty();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grafo ").append(esDirigido ? "dirigido" : "no dirigido").append(":\n");
        
        for (Map.Entry<T, List<Arista<T>>> entrada : listaAdyacencia.entrySet()) {
            sb.append(entrada.getKey()).append(" -> ");
            sb.append(entrada.getValue()).append("\n");
        }
        
        return sb.toString();
    }
}