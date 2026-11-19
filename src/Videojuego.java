import java.util.Objects;

public class Videojuego {
    private final String titulo;
    private final String genero;
    private final int anio;
    private final double calificacion;
    private final double precio;

    public Videojuego(String titulo, String genero, int anio,
                      double calificacion, double precio) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio");
        }
        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException("El genero no puede estar vacio");
        }
        if (anio < 1958) {
            throw new IllegalArgumentException("El anio debe ser 1958 o posterior: " + anio);
        }
        if (calificacion < 0 || calificacion > 10) {
            throw new IllegalArgumentException(
                "La calificacion debe estar entre 0 y 10: " + calificacion);
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo: " + precio);
        }
        this.titulo = titulo.trim();
        this.genero = genero.trim();
        this.anio = anio;
        this.calificacion = calificacion;
        this.precio = precio;
    }

    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public int getAnio() { return anio; }
    public double getCalificacion() { return calificacion; }
    public double getPrecio() { return precio; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Videojuego)) return false;
        return titulo.equalsIgnoreCase(((Videojuego) o).titulo);
    }

    @Override
    public int hashCode() { return Objects.hash(titulo.toLowerCase()); }

    @Override
    public String toString() {
        return titulo + " (" + anio + ") - " + genero
             + " - " + calificacion + " - $" + precio;
    }
}