package controlador;

import modelo.Partido;

import java.io.*;
import java.util.ArrayList;

public class GestionAlmacenamiento {
    private static final File datos = new File("./partidos.dat");

    public static boolean guardarDatos(ArrayList<Partido> listaPartidos) {
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream(datos));
            output.writeObject(listaPartidos);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {

            }
        }
    }

    public static ArrayList<Partido> cargarDatos() {
        ObjectInputStream input = null;
        ArrayList<Partido> listaPartidos = new ArrayList<>();
        try {
            input = new ObjectInputStream(new FileInputStream(datos));
            listaPartidos = (ArrayList<Partido>) input.readObject();
        } catch (EOFException e) {

        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {

            }
            return listaPartidos;
        }
    }
}
