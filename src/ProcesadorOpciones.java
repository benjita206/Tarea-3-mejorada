// ============= CLASE PROCESADOR DE OPCIONES =============
import java.util.*;
import java.util.function.*;

public class ProcesadorOpciones {

    // Función pura para procesar opción de menú específico
    public static Function<List<String>, String> procesarMenuEspecifico =
            entradas -> {
                if (entradas.size() != 3) {
                    return "❌ Se requieren exactamente 3 entradas: entrada, principal y postre.";
                }

                String nombreEntrada = entradas.get(0);
                String nombrePrincipal = entradas.get(1);
                String nombrePostre = entradas.get(2);

                Optional<Menu> menuOpt = GeneradorMenus.crearMenu
                        .apply(nombreEntrada)
                        .apply(nombrePrincipal)
                        .apply(nombrePostre);

                return Formateador.formatearResultadoMenu.apply(menuOpt);
            };

    // Función pura para procesar opción de combinaciones bajas en calorías
    public static Function<Integer, String> procesarCombinacionesBajas =
            maxCalorias -> {
                if (maxCalorias <= 0) {
                    return "❌ Por favor ingrese un número positivo.";
                }

                List<Menu> combinacionesValidas = GeneradorMenus.filtrarPorCalorias.apply(maxCalorias);
                return Formateador.formatearCombinacionesBajas.apply(maxCalorias).apply(combinacionesValidas);
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
}