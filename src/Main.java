import java.util.Scanner;

public class Main {

    // Función recursiva para el bucle principal (simulando recursión de cola)
    private static void funcionPrincipal(Scanner scanner, boolean continuar) {
        if (!continuar) {
            return;
        }

        System.out.print(Procesador.mensajeBienvenida.get() + "\nSeleccione una opción (1-3): ");
        String opcion = scanner.nextLine();

        String resultado = Procesador.procesarOpcion.apply(opcion).apply(scanner);
        System.out.println(resultado);

        boolean seguirContinuando = Procesador.deberContinuar.apply(opcion);

        if (seguirContinuando) {
            System.out.println("\n" + "=".repeat(50));
        }

        // Llamada recursiva
        funcionPrincipal(scanner, seguirContinuando);
    }

    // Función principal - punto de entrada del programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Estado funcional usando recursión
        funcionPrincipal(scanner, true);

        scanner.close();
    }
}