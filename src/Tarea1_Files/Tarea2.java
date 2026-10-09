package Tarea1_Files;

import java.io.*;

public class Tarea2 {

    private static final File baseDir = new File("Datos/ud1/practice");

    public static void main(String[] args) {
        File original = new File(baseDir, "original.bin");
        File copia = new File(baseDir, "copia.bin");

        try {
            //Crear original de 20.000 bytes
            System.out.println("Escribiendo 20.000 bytes en Original");
            try (FileOutputStream out = new FileOutputStream(original)) {
                for(int i = 0; i<20000; i++) {
                    out.write(i % 256);
                }
            }

            //Copiar en Copia
            System.out.println("Copiando Original en Copia");
            int copiados = 0;
            try(FileInputStream in = new FileInputStream(original);
                FileOutputStream out = new FileOutputStream(copia)) {
                int b;
                while((b=in.read()) != -1) {
                    out.write(b);
                    copiados++;
                }
            }

            System.out.println("Total copiados = " + copiados);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        //Coprobar misma longitud
        System.out.println("Comprobación de longitud:");
        System.out.println("Longitud de original.bin: " + original.length() +
                           "\nLongitud de copia.bin: " + copia.length() + "\n" +
                           "Cuadra?: " + (original.length() == copia.length()));

        //Leer los 5 primeros bytes
        try(FileReader fr = new FileReader(original)) {
            for (int i = 0; i < 5; i++) {
                int c = fr.read();
                System.out.println((char) c);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
