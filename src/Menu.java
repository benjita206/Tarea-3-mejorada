import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Menu {
    private final Plato entrada;
    private final Plato principal;
    private final Plato postre;

    // Base de datos inmutable de platos (consolidada desde BaseDatosPlatos)
    private static final List<Plato> PLATOS = List.of(
            // Entradas
            new Plato("Paella", 200, Plato.TipoPlato.ENTRADA),
            new Plato("Gazpacho", 150, Plato.TipoPlato.ENTRADA),
            new Plato("Pasta", 300, Plato.TipoPlato.ENTRADA),
            new Plato("Ensalada César", 180, Plato.TipoPlato.ENTRADA),
            new Plato("Sopa de verduras", 120, Plato.TipoPlato.ENTRADA),

            // Platos principales
            new Plato("Filete de cerdo", 400, Plato.TipoPlato.PRINCIPAL),
            new Plato("Pollo asado", 280, Plato.TipoPlato.PRINCIPAL),
            new Plato("Bistec a lo pobre", 500, Plato.TipoPlato.PRINCIPAL),
            new Plato("Trucha", 160, Plato.TipoPlato.PRINCIPAL),
            new Plato("Bacalao", 300, Plato.TipoPlato.PRINCIPAL),
            new Plato("Salmón a la plancha", 350, Plato.TipoPlato.PRINCIPAL),
            new Plato("Lasaña", 450, Plato.TipoPlato.PRINCIPAL),

            // Postres
            new Plato("Flan", 200, Plato.TipoPlato.POSTRE),
            new Plato("Naranja", 50, Plato.TipoPlato.POSTRE),
            new Plato("Nueces", 500, Plato.TipoPlato.POSTRE),
            new Plato("Yogur", 100, Plato.TipoPlato.POSTRE),
            new Plato("Helado", 250, Plato.TipoPlato.POSTRE)
    );

    public Menu(Plato entrada, Plato principal, Plato postre) {
        this.entrada = entrada;
        this.principal = principal;
        this.postre = postre;
    }

    // Getters
    public Plato entrada() { return entrada; }
    public Plato principal() { return principal; }
    public Plato postre() { return postre; }

    // Función pura para calcular calorías totales
    public int totalCalorias() {
        return Stream.of(entrada, principal, postre)
                .mapToInt(Plato::calorias)
                .sum();
    }

    // ========== FUNCIONES ESTÁTICAS DE BASE DE DATOS ==========

    // Función pura para obtener todos los platos
    public static Supplier<List<Plato>> obtenerTodosLosPlatos = () ->
            Collections.unmodifiableList(PLATOS);

    // Función pura para filtrar platos por tipo
    public static Function<Plato.TipoPlato, List<Plato>> filtrarPorTipo =
            tipo -> PLATOS.stream()
                    .filter(plato -> plato.tipo() == tipo)
                    .collect(Collectors.toUnmodifiableList());

    // Función pura para buscar plato por nombre
    public static Function<String, Optional<Plato>> buscarPorNombre =
            nombre -> PLATOS.stream()
                    .filter(plato -> plato.nombre().equalsIgnoreCase(nombre.trim()))
                    .findFirst();

    // ========== FUNCIONES DE GENERACIÓN DE MENÚS ==========

    // Función pura para generar todas las combinaciones posibles
    public static Supplier<List<Menu>> generarTodasCombinaciones = () -> {
        List<Plato> entradas = filtrarPorTipo.apply(Plato.TipoPlato.ENTRADA);
        List<Plato> principales = filtrarPorTipo.apply(Plato.TipoPlato.PRINCIPAL);
        List<Plato> postres = filtrarPorTipo.apply(Plato.TipoPlato.POSTRE);

        return entradas.stream()
                .flatMap(entrada -> principales.stream()
                        .flatMap(principal -> postres.stream()
                                .map(postre -> new Menu(entrada, principal, postre))))
                .collect(Collectors.toUnmodifiableList());
    };

    // Función pura para filtrar menús por límite calórico
    public static Function<Integer, List<Menu>> filtrarPorCalorias =
            limite -> generarTodasCombinaciones.get().stream()
                    .filter(menu -> menu.totalCalorias() <= limite)
                    .sorted(Comparator.comparingInt(Menu::totalCalorias))
                    .collect(Collectors.toUnmodifiableList());

    // Función pura para crear un menú desde nombres de platos (currificada)
    public static Function<String, Function<String, Function<String, Optional<Menu>>>> crearMenu =
            nombreEntrada -> nombrePrincipal -> nombrePostre -> {
                Optional<Plato> entrada = buscarPorNombre.apply(nombreEntrada);
                Optional<Plato> principal = buscarPorNombre.apply(nombrePrincipal);
                Optional<Plato> postre = buscarPorNombre.apply(nombrePostre);

                return entrada.flatMap(e ->
                        principal.flatMap(p ->
                                postre.map(pos -> new Menu(e, p, pos))));
            };

    // Función pura para validar si un menú es válido por calorías
    public static Function<Integer, Function<Menu, Boolean>> esMenuValido =
            limite -> menu -> menu.totalCalorias() <= limite;

    @Override
    public String toString() {
        return String.format("* Entrada: %s\n  Principal: %s\n  Postre: %s\n  TOTAL: %d calorías\n",
                entrada, principal, postre, totalCalorias());
    }
}