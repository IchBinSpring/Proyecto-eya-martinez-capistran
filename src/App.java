import java.util.Scanner;

public class App {
    private final Catalogo catalogo = new Catalogo();
    private final Historial historial = new Historial();
    private final Consultas consultas = new Consultas(catalogo);
    private final GrafoSimilitud grafo = new GrafoSimilitud();

    public static void main(String[] args) {
        App app = new App();
        app.cargarDatosDeEjemplo();
        app.menu(new Scanner(System.in));
    }

    private void menu(Scanner in) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== GAMEVAULT ===");
            System.out.println("1) Juegos por genero   2) Top 3 mejor calificados");
            System.out.println("3) Promedio por genero 4) Conteo por genero");
            System.out.println("5) Juego mas reciente  6) Agregar juego");
            System.out.println("7) Recomendaciones     8) Historial");
            System.out.println("0) Salir");
            opcion = leerEntero(in, "Opcion: ", 0, 8);

            switch (opcion) {
                case 1 -> {
                    String genero = leerTexto(in, "Genero " + catalogo.generosDisponibles() + ": ");
                    historial.registrar(genero);
                    var titulos = consultas.titulosDeGenero(genero);
                    if (titulos.isEmpty()) {
                        System.out.println("No hay juegos de ese genero.");
                    } else {
                        titulos.forEach(System.out::println);
                    }
                }
                case 2 -> {
                    var top = consultas.mejoresCalificadas(3);
                    if (top.isEmpty()) {
                        System.out.println("Todavia no hay juegos.");
                    } else {
                        top.forEach(System.out::println);
                    }
                }
                case 3 -> {
                    String genero = leerTexto(in, "Genero: ");
                    if (consultas.titulosDeGenero(genero).isEmpty()) {
                        System.out.println("No hay juegos de ese genero.");
                    } else {
                        System.out.printf("Promedio: %.2f%n", consultas.promedioDeGenero(genero));
                    }
                }
                case 4 -> {
                    var conteo = consultas.conteoPorGenero();
                    if (conteo.isEmpty()) {
                        System.out.println("Todavia no hay juegos.");
                    } else {
                        conteo.forEach((g, n) -> System.out.println(g + ": " + n));
                    }
                }
                case 5 -> consultas.masReciente().ifPresentOrElse(
                        v -> System.out.println("El mas reciente: " + v),
                        () -> System.out.println("Todavia no hay juegos."));
                case 6 -> agregarJuego(in);
                case 7 -> recomendar(in);
                case 8 -> {
                    var ultimos = historial.ultimos(3);
                    if (ultimos.isEmpty()) {
                        System.out.println("El historial esta vacio.");
                    } else {
                        System.out.println(ultimos);
                    }
                }
                case 0 -> System.out.println("Hasta luego.");
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void agregarJuego(Scanner in) {
        String titulo = leerTexto(in, "Titulo: ");
        String genero = leerTexto(in, "Genero: ");
        int anio = leerEntero(in, "Anio (1958-2100): ", 1958, 2100);
        double calif = leerDecimal(in, "Calificacion (0-10): ");
        double precio = leerDecimal(in, "Precio: ");
        try {
            Videojuego v = new Videojuego(titulo, genero, anio, calif, precio);
            System.out.println(catalogo.agregar(v) ? "Agregado." : "Ya existia.");
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo agregar: " + e.getMessage());
        }
    }

    private void recomendar(Scanner in) {
        String titulo = leerTexto(in, "Juego base: ");
        if (catalogo.buscar(titulo).isEmpty()) {
            System.out.println("Ese juego no existe en el catalogo.");
            return;
        }
        var recs = grafo.recomendaciones(catalogo.buscar(titulo).get().getTitulo(), 2);
        if (recs.isEmpty()) {
            System.out.println("No hay recomendaciones para ese juego.");
        } else {
            System.out.println("Si te gusto, prueba: " + recs);
        }
    }

    // ---------- lecturas protegidas ----------
    private int leerEntero(Scanner in, String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(in.nextLine().trim());
                if (valor < min || valor > max) {
                    System.out.println("Debe estar entre " + min + " y " + max + ".");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero entero.");
            }
        }
    }

    private double leerDecimal(Scanner in, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(in.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero (ejemplo: 8.5).");
            }
        }
    }

    private String leerTexto(Scanner in, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = in.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            System.out.println("No puede estar vacio.");
        }
    }

    // ---------- datos (minimo 15) ----------
    private void cargarDatosDeEjemplo() {
        catalogo.agregar(new Videojuego("Hades", "Roguelike", 2020, 9.0, 24.99));
        catalogo.agregar(new Videojuego("Celeste", "Plataformas", 2018, 8.8, 19.99));
        catalogo.agregar(new Videojuego("Hollow Knight", "Metroidvania", 2017, 9.0, 14.99));
        catalogo.agregar(new Videojuego("Stardew Valley", "Simulacion", 2016, 8.9, 13.99));
        catalogo.agregar(new Videojuego("Portal 2", "Puzzle", 2011, 9.5, 9.99));
        catalogo.agregar(new Videojuego("Minecraft", "Sandbox", 2011, 9.3, 26.95));
        catalogo.agregar(new Videojuego("The Witcher 3", "RPG", 2015, 9.7, 39.99));
        catalogo.agregar(new Videojuego("Elden Ring", "RPG", 2022, 9.5, 59.99));
        catalogo.agregar(new Videojuego("Dark Souls", "RPG", 2011, 8.9, 39.99));
        catalogo.agregar(new Videojuego("Dead Cells", "Roguelike", 2018, 8.7, 24.99));
        catalogo.agregar(new Videojuego("Ori and the Blind Forest", "Metroidvania", 2015, 8.8, 19.99));
        catalogo.agregar(new Videojuego("Terraria", "Sandbox", 2011, 9.0, 9.99));
        catalogo.agregar(new Videojuego("Celeste Jr", "Plataformas", 2018, 8.8, 0.0));
        catalogo.agregar(new Videojuego("The Room", "Puzzle", 2012, 8.2, 4.99));
        catalogo.agregar(new Videojuego("Pong", "Arcade", 1972, 6.5, 0.0));
        catalogo.agregar(new Videojuego("Disco Elysium", "RPG", 2019, 9.4, 39.99));

        grafo.conectar("Hades", "Dead Cells");
        grafo.conectar("Hades", "Hollow Knight");
        grafo.conectar("Hollow Knight", "Ori and the Blind Forest");
        grafo.conectar("Hollow Knight", "Celeste");
        grafo.conectar("Celeste", "Ori and the Blind Forest");
        grafo.conectar("Elden Ring", "Dark Souls");
        grafo.conectar("Elden Ring", "The Witcher 3");
        grafo.conectar("The Witcher 3", "Disco Elysium");
        grafo.conectar("Minecraft", "Terraria");
        grafo.conectar("Minecraft", "Stardew Valley");
        grafo.conectar("Portal 2", "The Room");
    }
}