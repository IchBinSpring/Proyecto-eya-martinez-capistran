# GameVault
Catalogo de videojuegos en consola (Java).

## Integrantes
- Tabata Elizabeth Capistran Gomez (capistrantabatae)
- Juan Pablo Martinez (IchBinSpring)

## Estructuras usadas
- Map (LinkedHashMap): busqueda por titulo
- Set (TreeSet): generos unicos y ordenados
- Pila (ArrayDeque): historial de consultas
- Grafo (Map + BFS): recomendaciones (extra)

## Como ejecutar
javac src/*.java
java -cp src App

## Funciones del menu
1. Juegos por genero
2. Top 3 mejor calificados
3. Promedio por genero
4. Conteo por genero
5. Juego mas reciente
6. Agregar juego
7. Recomendaciones
8. Historial
9. Salir