import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Procesador {

    // ========== FUNCIONES DE FORMATEO ==========

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
                List<Plato> platos = Menu.filtrarPorTipo.apply(tipo);
                return "\n--- " + titulo.toUpperCase() + "S ---\n" +
                        formatearListaPlatos.apply(platos);
            };

    // Función pura para crear mensaje de bienvenida
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

    // ========== FUNCIONES DE PROCESAMIENTO ==========

    // Función pura para procesar opción de menú específico
    public static Function<List<String>, String> procesarMenuEspecifico =
            entradas -> {
                if (entradas.size() != 3) {
                    return "❌ Se requieren exactamente 3 entradas: entrada, principal y postre.";
                }

                String nombreEntrada = entradas.get(0);
                String nombrePrincipal = entradas.get(1);
                String nombrePostre = entradas.get(2);

                Optional<Menu> menuOpt = Menu.crearMenu
                        .apply(nombreEntrada)
                        .apply(nombrePrincipal)
                        .apply(nombrePostre);

                return formatearResultadoMenu.apply(menuOpt);
            };

    // Función pura para procesar opción de combinaciones bajas en calorías
    public static Function<Integer, String> procesarCombinacionesBajas =
            maxCalorias -> {
                if (maxCalorias <= 0) {
                    return "❌ Por favor ingrese un número positivo.";
                }

                List<Menu> combinacionesValidas = Menu.filtrarPorCalorias.apply(maxCalorias);
                return formatearCombinacionesBajas.apply(maxCalorias).apply(combinacionesValidas);
            };

    // Función pura para validar entrada numérica
    public static Function<String, Optional<Integer>> validarEnteroPositivo =
            entrada -> {
                try {
                    int numero = Integer.parseInt(entrada.trim());
                    return numero > 0 ? Optional.of(numero) : Optional.empty();
                } catch (NumberFormatException e) {
                    return Optional.empty();
                }
            };

    // ========== FUNCIONES DE INTERFAZ DE USUARIO ==========

    // Función pura para manejar entrada de menú específico
    public static Function<Scanner, String> manejarMenuEspecifico =
            scanner -> {
                System.out.print(mostrarPlatosPorTipo.apply(Plato.TipoPlato.ENTRADA) +
                        "\nIngrese el nombre de la entrada: ");
                String entrada = scanner.nextLine();

                System.out.print(mostrarPlatosPorTipo.apply(Plato.TipoPlato.PRINCIPAL) +
                        "\nIngrese el nombre del plato principal: ");
                String principal = scanner.nextLine();

                System.out.print(mostrarPlatosPorTipo.apply(Plato.TipoPlato.POSTRE) +
                        "\nIngrese el nombre del postre: ");
                String postre = scanner.nextLine();

                return procesarMenuEspecifico.apply(List.of(entrada, principal, postre));
            };

    // Función pura para manejar entrada de combinaciones bajas
    public static Function<Scanner, String> manejarCombinacionesBajas =
            scanner -> {
                System.out.print("\n--- MENÚS BAJOS EN CALORÍAS ---\n" +
                        "Ingrese el máximo de calorías deseado: ");
                String entrada = scanner.nextLine();

                return validarEnteroPositivo.apply(entrada)
                        .map(procesarCombinacionesBajas)
                        .orElse("❌ Por favor ingrese un número válido positivo.");
            };

    // Función pura para procesar opción del menú principal
    public static Function<String, Function<Scanner, String>> procesarOpcion =
            opcion -> scanner -> {
                switch (opcion.trim()) {
                    case "1":
                        return manejarMenuEspecifico.apply(scanner);
                    case "2":
                        return manejarCombinacionesBajas.apply(scanner);
                    case "3":
                        return "\n👋 ¡Gracias por usar nuestro sistema!\n" +
                                "¡Esperamos verle pronto en \"Mi Mejor Comida\"!";
                    default:
                        return "❌ Opción no válida. Por favor seleccione 1, 2 o 3.";
                }
            };

    // Función pura para determinar si continuar
    public static Function<String, Boolean> deberContinuar =
            opcion -> !opcion.trim().equals("3");
}