import java.util.Objects;

public class Plato {
    private final String nombre;
    private final int calorias;
    private final TipoPlato tipo;

    public Plato(String nombre, int calorias, TipoPlato tipo) {
        this.nombre = nombre;
        this.calorias = calorias;
        this.tipo = tipo;
    }

    public String nombre() { return nombre; }
    public int calorias() { return calorias; }
    public TipoPlato tipo() { return tipo; }

    @Override
    public String toString() {
        return nombre + " (" + calorias + " cal)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plato)) return false;
        Plato plato = (Plato) o;
        return calorias == plato.calorias && Objects.equals(nombre, plato.nombre) && tipo == plato.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, calorias, tipo);
    }
    public enum TipoPlato {
        ENTRADA, PRINCIPAL, POSTRE
    }
}

