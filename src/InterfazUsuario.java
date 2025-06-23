// ============= CLASE INTERFAZ USUARIO =============
import java.util.*;
import java.util.function.*;

public class InterfazUsuario {

    // Función pura para manejar entrada de menú específico
    public static Function<Scanner, String> manejarMenuEspecifico =
            scanner -> {
                System.out.print(Formateador.mostrarPlatosPorTipo.apply(Plato.TipoPlato.ENTRADA) +
                        "\nIngrese el nombre de la entrada: ");
                String entrada = scanner.nextLine();

                System.out.print(Formateador.mostrarPlatosPorTipo.apply(Plato.TipoPlato.PRINCIPAL) +
                        "\nIngrese el nombre del plato principal: ");
                String principal = scanner.nextLine();

                System.out.print(Formateador.mostrarPlatosPorTipo.apply(Plato.TipoPlato.POSTRE) +
                        "\nIngrese el nombre del postre: ");
                String postre = scanner.nextLine();

                return ProcesadorOpciones.procesarMenuEspecifico.apply(List.of(entrada, principal, postre));
            };

    // Función pura para manejar entrada de combinaciones bajas
    public static Function<Scanner, String> manejarCombinacionesBajas =
            scanner -> {
                System.out.print("\n--- MENÚS BAJOS EN CALORÍAS ---\n" +
                        "Ingrese el máximo de calorías deseado: ");
                String entrada = scanner.nextLine();

                return ProcesadorOpciones.validarEnteroPositivo.apply(entrada)
                        .map(ProcesadorOpciones.procesarCombinacionesBajas)
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