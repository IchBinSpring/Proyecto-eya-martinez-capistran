import java.util.*;

public class GrafoSimilitud {
    private final Map<String, List<String>> vecinos = new HashMap<>();

    public void conectar(String a, String b) {
        if (a == null || b == null || a.isBlank() || b.isBlank()) {
            throw new IllegalArgumentException("Los nombres no pueden estar vacios");
        }
        vecinos.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
        vecinos.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
    }

    public List<String> recomendaciones(String origen, int maxSaltos) {
        Map<String, Integer> distancia = new LinkedHashMap<>();
        Queue<String> cola = new LinkedList<>();
        distancia.put(origen, 0);
        cola.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            int d = distancia.get(actual);
            if (d == maxSaltos) continue;
            for (String v : vecinos.getOrDefault(actual, List.of())) {
                if (!distancia.containsKey(v)) {
                    distancia.put(v, d + 1);
                    cola.add(v);
                }
            }
        }
        distancia.remove(origen);
        return new ArrayList<>(distancia.keySet());
    }
}