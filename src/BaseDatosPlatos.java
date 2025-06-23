import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class BaseDatosPlatos {

    // Base de datos inmutable de platos
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
}