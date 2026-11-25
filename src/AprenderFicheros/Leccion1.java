package AprenderFicheros;

import java.io.File;
import java.io.IOException;

public class Leccion1 {
    public static void main(String[] args) throws IOException {
        File f = new File("notas.txt");
        System.out.println("¿Existe? " + f.exists());
        System.out.println("Ruta: " + f.getAbsolutePath());
        System.out.println(f.createNewFile());
        System.out.println("¿Existe? " + f.exists());
        System.out.println("Ocupa: " + f.length());
        System.out.println(f.createNewFile());
        File f2 = new File("practicas/tema1/ejercicios");
        System.out.println(f2.mkdir());
        System.out.println(f2.mkdirs());
        f.delete();
        System.out.println("¿Existe post delete()? " + f.exists());
    }
}