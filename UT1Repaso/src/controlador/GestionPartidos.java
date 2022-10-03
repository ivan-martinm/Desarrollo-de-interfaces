package controlador;

import modelo.Partido;
import vista.Main;

import java.time.LocalDate;
import java.util.*;

public class GestionPartidos {
    private ArrayList<Partido> listaPartidos;

    public GestionPartidos() {
        if ((listaPartidos = GestionAlmacenamiento.cargarDatos()).isEmpty()) {
            Main.mostrarMensaje("No se han cargado datos de partidos");
        } else {
            Main.mostrarMensaje("Se han cargado los datos de partidos");
        }
        //rellenarArray();
    }

    public boolean nuevoPartido(String local, String visitante, int div, String resultado, LocalDate fecha) {
        Partido nuevoPartido = new Partido(local, visitante, Partido.divisionPorId(div), resultado, fecha);
        if (!partidoDuplicado(nuevoPartido)) {
            return listaPartidos.add(nuevoPartido);
        }
        return false;
    }

    public List<String> listarPartidos() {
        return listaPartidos
                .stream()
                .map(p -> p.toString())
                .toList();

        // Sin usar streams
        /*
        ArrayList<String> partidos = new ArrayList<>();
        for(Partido partido : listaPartidos) {
            partidos.add(partido.toString());
        }
        return partidos;
        */
    }

    public boolean borrarPartido(String[] equipos, LocalDate fecha) {
        Partido partido = obtenerPartido(equipos[0], equipos[1], fecha);
        if (partido != null) {
            return listaPartidos.remove(partido);
        }

        // Sin usar streams
        /*
        int idPartido = obtenerIdPartido(equipos[0], equipos[1], fecha);
        if(idPartido != -1) {
            listaPartidos.remove(idPartido);
            return true;
        }
        */

        return false;
    }

    public List<String> ordenarAscendente() {
        return listaPartidos
                .stream()
                .sorted(Comparator.comparing(p -> p.getFecha()))
                .map(p -> p.toString())
                .toList();

        // Sin usar Stream (requiere que Partido implemente Comparable y override de compareTo)
        /*
        ArrayList<Partido> copiaPartidos = new ArrayList(listaPartidos);
        Collections.sort(copiaPartidos);
        ArrayList<String> partidos = new ArrayList<>();
        for(Partido partido : copiaPartidos) {
            partidos.add(partido.toString());
        }
        return partidos;
        */
    }

    public List<String> ordenarDescendente() {
        return listaPartidos
                .stream()
                .sorted(Comparator.comparing(p -> p.getFecha(), Comparator.reverseOrder()))
                .map(p -> p.toString())
                .toList();

        // Sin usar Stream (requiere que Partido implemente Comparable y override de CompareTo)
        /*
        ArrayList<Partido> copiaPartidos = new ArrayList(listaPartidos);
        Collections.sort(copiaPartidos, Collections.reverseOrder());
        ArrayList<String> partidos = new ArrayList<>();
        for (Partido partido : copiaPartidos) {
            partidos.add(partido.toString());
        }
        return partidos;
        */
    }

    public List<String> partidosPorDivision(int division) {
        return listaPartidos
                .stream()
                .filter(p -> p.getDivision() == Partido.divisionPorId(division))
                .map(p -> p.toString())
                .toList();

        // Sin usar streams
        /*
        ArrayList<String> partidos = new ArrayList<>();
        for(Partido partido : listaPartidos) {
            if(partido.getDivision() == Partido.divisionPorId(division)) {
                partidos.add(partido.toString());
            }
        }
        return partidos;
         */
    }

    public List<String[]> partidosPorFecha(LocalDate fecha) {
        return listaPartidos
                .stream()
                .filter(p -> p.getFecha().isEqual(fecha))
                .map(p -> new String[]{p.getLocal(), p.getVisitante()})
                .toList();

        // Sin usar streams
        /*
        ArrayList<String[]> partidos = new ArrayList<>();
        for (Partido partido : listaPartidos) {
            if (partido.getFecha().isEqual(fecha)) {
                partidos.add(new String[]{partido.getLocal(), partido.getVisitante()});
            }
        }
        return partidos;
        */
    }

    public boolean guardarPartidos() {
        return GestionAlmacenamiento.guardarDatos(listaPartidos);
    }

    //*************************************//
    //********* Métodos privados  *********//
    //*************************************//

    private boolean partidoDuplicado(Partido partido) {
        return listaPartidos
                .stream()
                .filter(p -> partido.equals(p))
                .count() > 0;

        // Sin usar streams
        /*
        for(Partido p : listaPartidos) {
            if(partido.equals(p)) {
                return true;
            }
        }
        return false;
        */
    }

    private Partido obtenerPartido(String local, String visitante, LocalDate fecha) {
        return listaPartidos
                .stream()
                .filter(p -> p.getFecha().isEqual(fecha)
                        && p.getLocal().equals(local)
                        && p.getVisitante().equals(visitante))
                .findFirst().orElse(null);
    }

    private int obtenerIdPartido(String local, String visitante, LocalDate fecha) {
        for (int i = 0; i < listaPartidos.size(); i++) {
            if (listaPartidos.get(i).getFecha().isEqual(fecha)
                    && listaPartidos.get(i).getLocal().equals(local)
                    && listaPartidos.get(i).getVisitante().equals(visitante)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Método opcional para introducir datos en el array y hacer pruebas
     */
    private void rellenarArray() {
        listaPartidos.add(new Partido("equipo 1", "equipo 2", Partido.Division.PRIMERA, "15-12", LocalDate.now()));
        listaPartidos.add(new Partido("equipo 3", "equipo 4", Partido.Division.TERCERA, "16-2", LocalDate.of(2021,
                12, 26)));
        listaPartidos.add(new Partido("equipo 2", "equipo 5", Partido.Division.PRIMERA, "18-18", LocalDate.of(2022,
                6, 13)));
        listaPartidos.add(new Partido("equipo 5", "equipo 3", Partido.Division.SEGUNDA, "3-10", LocalDate.of(2019,
                10, 15)));
        listaPartidos.add(new Partido("equipo 4", "equipo 1", Partido.Division.SEGUNDA, "11-19", LocalDate.of(2023,
                1, 1)));
    }
}
