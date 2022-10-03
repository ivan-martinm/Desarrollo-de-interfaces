package vista;

import controlador.GestionPartidos;
import modelo.Partido;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static GestionPartidos gestionPartidos = new GestionPartidos();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("1 - Dar de alta de un nuevo partido");
            System.out.println("2 - Mostrar listado de partidos");
            System.out.println("3 - Borrar un partido");
            System.out.println("4 - Ordenar partidos");
            System.out.println("5 - Mostrar partidos por división");
            System.out.println("6 - Salir");
            System.out.println("Selecciona una acción: ");
            opcion = validarInput();

            switch (opcion) {
                case 1:
                    nuevoPartido();
                    break;
                case 2:
                    mostrarPartidos();
                    break;
                case 3:
                    borrarPartido();
                    break;
                case 4:
                    ordenarPartidos();
                    break;
                case 5:
                    partidosPorDivision();
                    break;
                case 6:
                    guardarDatos();
                    System.out.println("Fin de la aplicación");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
        while (opcion != 6);
    }

    private static void nuevoPartido() {
        System.out.println("Introduce el nombre del equipo local: ");
        String local = scanner.nextLine();
        System.out.println("Introduce el nombre del equipo visitante: ");
        String visitante = scanner.nextLine();
        System.out.println("De qué división es el partido?: 1, 2, 3:  ");
        int division = validarInput();
        if (!validarRango(division, 1, 3)) {
            System.out.println("División no válida");
            return;
        }
        System.out.println("Fecha del partido (formato DD-MM-YYYY): ");
        LocalDate fecha = validarFecha(scanner.nextLine());
        if (fecha == null) {
            System.out.println("Formato de fecha no válido");
            return;
        }
        System.out.println("Resultado (formato XX-YY): ");
        String resultado = scanner.nextLine();
        if (!validarResultado(resultado)) {
            System.out.println("Resultado no válido");
            return;
        }
        if (gestionPartidos.nuevoPartido(local, visitante, division, resultado, fecha)) {
            System.out.println("Partido dado de alta correctamente");
        } else {
            System.out.println("El partido ya existe, no se ha dado de alta");
        }
    }

    private static void mostrarPartidos() {
        List<String> listaPartidos = gestionPartidos.listarPartidos();
        if (listaPartidos.size() == 0) {
            System.out.println("No hay partidos almacenados");
            return;
        }
        for (String partido : listaPartidos) {
            System.out.println(partido);
            System.out.println("-----------------");
        }
    }

    private static void borrarPartido() {
        System.out.println("Introduce la fecha del partido a borrar (formato dd-mm-yyyy): ");
        LocalDate fecha = validarFecha(scanner.nextLine());
        if (fecha == null) {
            System.out.println("Formato de fecha no válido");
            return;
        }
        List<String[]> equipos = gestionPartidos.partidosPorFecha(fecha);
        if (equipos.size() == 0) {
            System.out.println("No hay partidos en la fecha seleccionada");
            return;
        }
        System.out.println("Mostrando partidos en la fecha : " + fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"
                                                                                                         )));
        System.out.println("-----------------");
        for (int i = 0; i < equipos.size(); i++) {
            System.out.println((i + 1) + " - " + equipos.get(i)[0] + "   VS   " + equipos.get(i)[1]);
            System.out.println("-----------------");
        }
        System.out.println("Introduce el número del partido que quieres borrar: ");
        int opcion = validarInput();
        if (!validarRango(opcion, 1, equipos.size())) {
            System.out.println("Número no válido");
            return;
        }
        if(gestionPartidos.borrarPartido(equipos.get(opcion-1), fecha)) {
            System.out.println("Partido borrado correctamente");
        } else {
            System.out.println("El partido seleccionado no existe");
        }
    }

    private static void ordenarPartidos() {
        System.out.println("1 - Ordenar ascendente");
        System.out.println("2 - Ordenar descendente");
        System.out.println("Escoge la ordenación: ");
        int opcion = validarInput();
        if(!validarRango(opcion, 1, 2)) {
            System.out.println("Opcion no válida");
            return;
        }
        List<String> listaPartidos = opcion == 1
                ? gestionPartidos.ordenarAscendente()
                : gestionPartidos.ordenarDescendente();

        if (listaPartidos.size() == 0) {
            System.out.println("No hay partidos almacenados");
            return;
        }
        for (String partido : listaPartidos) {
            System.out.println(partido);
            System.out.println("-----------------");
        }
    }

    private static void partidosPorDivision() {
        System.out.println("Elige una division (1, 2, 3): ");
        int division = validarInput();
        if (!validarRango(division, 1, 3)) {
            System.out.println("División no válida");
            return;
        }
        List<String> listaPartidos = gestionPartidos.partidosPorDivision(division);
        if (listaPartidos.size() == 0) {
            System.out.println("No hay partidos en esta división");
            return;
        }
        System.out.println("Mostrando partidos de " + Partido.divisionPorId(division).toString() + " division: ");
        System.out.println("-----------------");
        for (String partido : listaPartidos) {
            System.out.println(partido);
            System.out.println("-----------------");
        }
    }

    private static void guardarDatos() {
        if(gestionPartidos.guardarPartidos()) {
            System.out.println("Se han guardado los partidos en disco");
        } else {
            System.out.println("Error de escritura. Los datos no se guardarán");
        }
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    //*************************************//
    //***** Métodos para validaciones *****//
    //*************************************//

    private static int validarInput() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            return -1;
        } finally {
            scanner.nextLine();
        }
    }

    private static boolean validarRango(int num, int min, int max) {
        return num == -1 ? false : num >= min && num <= max;
    }

    private static LocalDate validarFecha(String fecha) {
        try {
            return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static boolean validarResultado(String resultado) {
        return resultado.matches("\\d{1,3}-\\d{1,3}");
    }
}