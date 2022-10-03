package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Partido implements Comparable, Serializable {
    private String local;
    private String visitante;
    private Division division;
    private String resultado;
    private LocalDate fecha;

    public enum Division {
        NINGUNA, PRIMERA, SEGUNDA, TERCERA
    }

    public Partido(String local, String visitante, Division division, String resultado, LocalDate fecha) {
        this.local = local;
        this.visitante = visitante;
        this.division = division;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public String getLocal() {
        return local;
    }

    public String getVisitante() {
        return visitante;
    }

    public Division getDivision() {
        return division;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public static Division divisionPorId(int id) {
        return (id < 1 && id > 3)
                ? Division.NINGUNA
                : Division.values()[id];
    }

    @Override
    public String toString() {
        return "Partido con fecha: " + fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + System.lineSeparator() +
                "- Equipo local: " + local + System.lineSeparator() +
                "- Equipo visitante: " + visitante + System.lineSeparator() +
                "- Division: " + division + System.lineSeparator() +
                "- Resultado: " + resultado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return Objects.equals(local, partido.local) && Objects.equals(visitante, partido.visitante) && Objects.equals(fecha, partido.fecha);
    }

    @Override
    public int compareTo(Object o) {
        Partido other = (Partido) o;
        return this.getFecha().compareTo(other.getFecha());
    }
}
