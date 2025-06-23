// ============= CLASE MENU =============
import java.util.stream.Stream;

public class Menu {
    private final Plato entrada;
    private final Plato principal;
    private final Plato postre;

    public Menu(Plato entrada, Plato principal, Plato postre) {
        this.entrada = entrada;
        this.principal = principal;
        this.postre = postre;
    }

    public Plato entrada() { return entrada; }
    public Plato principal() { return principal; }
    public Plato postre() { return postre; }

    // Función pura para calcular calorías totales
    public int totalCalorias() {
        return Stream.of(entrada, principal, postre)
                .mapToInt(Plato::calorias)
                .sum();
    }

    @Override
    public String toString() {
        return String.format("* Entrada: %s\n  Principal: %s\n  Postre: %s\n  TOTAL: %d calorías\n",
                entrada, principal, postre, totalCalorias());
    }
}