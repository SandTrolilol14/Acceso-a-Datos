package Tarea1_Files;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Tarea4 {

    private static final File baseDir = new File("datos/ud1/practice)");

    public static void main(String[] args) {

        File butacas = new File(baseDir, "butacas.dat");

        try(RandomAccessFile raf = new RandomAccessFile(butacas, "rw")) {

            for (int i = 0; i <= 5; i++) {
                raf.writeInt(i);
                raf.writeChars(prepararNombre("Cliente " + i));
                raf.writeLong(System.currentTimeMillis());
            }

            imprimirPuntero(raf);



            // Mostrar nombre de la 5 sin leer las anteriores
            long posButaca5 = (5 - 1) * 32;

            raf.seek(posButaca5 + 4);

            String nombreLeido = "";
            for (int i = 0; i < 10; i++) {
                nombreLeido += raf.readChar();
            }
            System.out.println("Nombre recuperado: " + nombreLeido);



            //Cambiar nombre de la butaca 3
            long posButaca3 = (3 - 1) * 32;
            raf.seek(posButaca3 + 4);
            raf.writeChars(prepararNombre("Oskar"));
            System.out.println("Nombre cambiado.");
            imprimirPuntero(raf);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String prepararNombre(String nombre) {
        if (nombre.length() > 10) {
            return nombre.substring(0, 10);
        }

        // Rellena con espacios para llegar a 10 caracteres
        while (nombre.length() < 10) {
            nombre += " ";
        }
        return nombre;
    }

    private static void imprimirPuntero(RandomAccessFile raf) throws IOException{

        System.out.println("Puntero en byte: " + raf.getFilePointer() + " | Tamaño total: " + raf.length() + " bytes");

    }
}
