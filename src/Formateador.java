// ============= CLASE FORMATEADOR =============
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Formateador {

    // Función pura para formatear lista de platos como texto
    public static Function<List<Plato>, String> formatearListaPlatos =
            platos -> platos.stream()
                    .map(plato -> "• " + plato.toString())
                    .collect(Collectors.joining("\n"));

    // Función pura para formatear menús como texto
    public static Function<List<Menu>, String> formatearMenus =
            menus -> menus.stream()
                    .map(Menu::toString)
                    .collect(Collectors.joining("\n"));

    // Función pura para mostrar platos por tipo
    public static Function<Plato.TipoPlato, String> mostrarPlatosPorTipo =
            tipo -> {
                String titulo = tipo.name().toLowerCase();
                List<Plato> platos = BaseDatosPlatos.filtrarPorTipo.apply(tipo);
                return "\n--- " + titulo.toUpperCase() + "S ---\n" +
                        formatearListaPlatos.apply(platos);
            };

    // Función pura para crear mensaje de bienvenida uwu
    public static Supplier<String> mensajeBienvenida = () ->
            "🍽️ BIENVENIDO A \"MI MEJOR COMIDA\"\n" +
                    "=====================================\n" +
                    "\n📋 OPCIONES DISPONIBLES:\n" +
                    "1. Calcular calorías de un menú específico\n" +
                    "2. Mostrar combinaciones bajas en calorías\n" +
                    "3. Salir\n";

    // Función pura para formatear resultado de menú específico
    public static Function<Optional<Menu>, String> formatearResultadoMenu =
            menuOpt -> menuOpt
                    .map(menu -> "\n🍽️ RESUMEN DE SU MENÚ:\n" +
                            "=====================\n" +
                            menu.toString())
                    .orElse("❌ Uno o más platos no son válidos. Verifique los nombres.");

    // Función pura para formatear resultado de combinaciones bajas
    public static Function<Integer, Function<List<Menu>, String>> formatearCombinacionesBajas =
            maxCalorias -> combinaciones -> {
                if (combinaciones.isEmpty()) {
                    return "\n❌ No hay combinaciones disponibles con menos de " + maxCalorias + " calorías.\n" +
                            "💡 Intente con un límite más alto.";
                } else {
                    return "\n🍽️ Combinaciones disponibles con menos de " + maxCalorias + " calorías:\n" +
                            "==========================================\n" +
                            formatearMenus.apply(combinaciones) +
                            "\n📊 Total de combinaciones encontradas: " + combinaciones.size();
                }
            };
}