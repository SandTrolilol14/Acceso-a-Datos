package Tarea1_Files;

import java.io.File;

public class Tarea5 {

    private static final File practica = new File("datos/ud1/practice");

    private static int totalFicheros = 0;
    private static int totalCarpetas = 0;
    private static long totalBytes = 0;

    public static void main(String[] args) {

        //Directoris mkdirs
        File entrada = new File(practica, "entrada");
        File salida = new File(practica, "salida");
        File copia = new File(practica, "copia");

        entrada.mkdirs(); salida.mkdirs(); copia.mkdirs();

        // Mover un fichero a entrada
        File oldFile = new File(practica, "original.bin");
        File newFile = new File(entrada, "original.bin");

        if(newFile.exists()) {
            System.out.println("Nombre: " + newFile.getName() + "\n" +
                               "Ruta: " + newFile.getAbsolutePath());
        }

        // Lista recursiva
        listaRecursiva(practica, "");

        System.out.println("Resumen:" + "\n" +
                           "Carpetas: " + totalCarpetas + "\n" +
                           "Ficheros" + totalFicheros + "\n" +
                           "Tamaño: " + totalBytes + "bytes.");

        // Borrado
        //Mal con delete()
        System.out.println("entrada.delete() = " + entrada.delete());
        //Devuelve false, por seguridad, delete siempre de hijos hacia padres

        //Ahora recursivo
        borrarRecursivo(entrada);
        System.out.println("Entrada existe ahora? " + entrada.exists());

    }

    public static void listaRecursiva(File carpeta, String sangria) {
        File[] elementos = carpeta.listFiles();

        if(elementos == null) return;

        for (File f : elementos) {
            if(f.isDirectory()) {
                totalCarpetas++;
                System.out.println(sangria + "[CARPETA]" + f.getName());
                listaRecursiva(f, sangria + "    ");
            } else {
                totalFicheros++;
                totalBytes += f.length();
                System.out.println(sangria + "[FICHERO]" + f.getName());
            }
        }
    }

    public static void borrarRecursivo(File elemento) {
        if (elemento.isDirectory()) {
            File[] hijos = elemento.listFiles();
            if (hijos != null) {
                for (File hijo : hijos) {
                    borrarRecursivo(hijo);
                }
            }
        }
        elemento.delete();
    }

}
