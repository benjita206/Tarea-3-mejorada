import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class GeneradorMenus {

    // Función pura para generar todas las combinaciones posibles
    public static Supplier<List<Menu>> generarTodasCombinaciones = () -> {
        List<Plato> entradas = BaseDatosPlatos.filtrarPorTipo.apply(Plato.TipoPlato.ENTRADA);
        List<Plato> principales = BaseDatosPlatos.filtrarPorTipo.apply(Plato.TipoPlato.PRINCIPAL);
        List<Plato> postres = BaseDatosPlatos.filtrarPorTipo.apply(Plato.TipoPlato.POSTRE);

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
                Optional<Plato> entrada = BaseDatosPlatos.buscarPorNombre.apply(nombreEntrada);
                Optional<Plato> principal = BaseDatosPlatos.buscarPorNombre.apply(nombrePrincipal);
                Optional<Plato> postre = BaseDatosPlatos.buscarPorNombre.apply(nombrePostre);

                return entrada.flatMap(e ->
                        principal.flatMap(p ->
                                postre.map(pos -> new Menu(e, p, pos))));
            };

    // Función pura para validar si un menú es válido por calorías
    public static Function<Integer, Function<Menu, Boolean>> esMenuValido =
            limite -> menu -> menu.totalCalorias() <= limite;
}