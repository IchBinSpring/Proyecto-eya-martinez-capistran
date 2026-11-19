import java.util.*;

public class Catalogo {
    private final Map<String, Videojuego> porTitulo = new LinkedHashMap<>();
    private final Set<String> generos = new TreeSet<>();

    public boolean agregar(Videojuego v) {
        if (v == null) {
            throw new IllegalArgumentException("El videojuego no puede ser null");
        }
        String clave = v.getTitulo().toLowerCase();
        if (porTitulo.containsKey(clave)) {
            return false; // ya existia
        }
        porTitulo.put(clave, v);
        generos.add(v.getGenero());
        return true;
    }

    public Optional<Videojuego> buscar(String titulo) {
        if (titulo == null) return Optional.empty();
        return Optional.ofNullable(porTitulo.get(titulo.trim().toLowerCase()));
    }

    public List<Videojuego> todas() {
        return new ArrayList<>(porTitulo.values()); // copia
    }

    public Set<String> generosDisponibles() {
        return new TreeSet<>(generos); // copia ordenada
    }
}