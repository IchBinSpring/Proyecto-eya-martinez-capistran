import java.util.*;

public class Historial {
    private final Deque<String> consultados = new ArrayDeque<>(); // pila

    public void registrar(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto del historial no puede estar vacio");
        }
        consultados.push(texto);
    }

    public Optional<String> ultimo() {
        return Optional.ofNullable(consultados.peek());
    }

    public List<String> ultimos(int n) {
        return consultados.stream().limit(n).toList();
    }
}
