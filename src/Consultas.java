import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Consultas {
    private final Catalogo catalogo;

    public Consultas(Catalogo catalogo) { this.catalogo = catalogo; }

    // filter + map
    public List<String> titulosDeGenero(String genero) {
        return catalogo.todas().stream()
                .filter(v -> v.getGenero().equalsIgnoreCase(genero))
                .map(Videojuego::getTitulo)
                .toList();
    }

    // sorted + limit
    public List<Videojuego> mejoresCalificadas(int n) {
        return catalogo.todas().stream()
                .sorted((a, b) -> Double.compare(b.getCalificacion(), a.getCalificacion()))
                .limit(n)
                .toList();
    }

    // reduccion: promedio (0.0 si no hay datos)
    public double promedioDeGenero(String genero) {
        return catalogo.todas().stream()
                .filter(v -> v.getGenero().equalsIgnoreCase(genero))
                .mapToDouble(Videojuego::getCalificacion)
                .average()
                .orElse(0.0);
    }

    // groupingBy
    public Map<String, Long> conteoPorGenero() {
        return catalogo.todas().stream()
                .collect(Collectors.groupingBy(Videojuego::getGenero, Collectors.counting()));
    }

    // Optional
    public Optional<Videojuego> masReciente() {
        return catalogo.todas().stream()
                .max((a, b) -> Integer.compare(a.getAnio(), b.getAnio()));
    }
}