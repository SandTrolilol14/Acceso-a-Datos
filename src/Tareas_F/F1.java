package Tareas_F;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class F1 {
    public static void main(String[] args) {
        try {
            caso1_Log();
            caso2_RegistroDirecto();
            caso3_Configuracion();
            caso4_ReservasHotel();
        } catch (IOException e) {
            System.out.println("Error de E/S: " + e.getMessage());
        }
    }

    // =====================================================================
    // CASO 1: LOG -> SECUENCIAL (añadir al final)
    // Solo se escriben líneas nuevas al final. Nunca se salta a ningún sitio.
    // =====================================================================
    static void caso1_Log() throws IOException {
        System.out.println("===== CASO 1: LOG (secuencial, append) =====");

        // El 'true' del FileWriter es el modo APPEND: escribe al final
        // sin borrar lo que ya había. Si ejecutas el programa varias veces,
        // verás que el log va creciendo.
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("app.log", StandardCharsets.UTF_8, true))) {
            bw.write(java.time.LocalDateTime.now() + " - Aplicación iniciada");
            bw.newLine();
            bw.write(java.time.LocalDateTime.now() + " - Usuario conectado");
            bw.newLine();
        } // el try-with-resources cierra el fichero solo (y vuelca el buffer)

        // Para comprobarlo, lo leemos entero (también secuencial)
        try (BufferedReader br = new BufferedReader(
                new FileReader("app.log", StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) { // null = fin de fichero
                System.out.println("  " + linea);
            }
        }
        System.out.println();
    }

    // =====================================================================
    // CASO 2: REGISTROS DE 100 BYTES -> ALEATORIO (ir directo al 4.573)
    // Registro = id (int, 4 bytes) + nombre (48 chars x 2 bytes = 96) = 100 bytes
    // =====================================================================
    static final int TAM_CLIENTE = 100;
    static final int LONG_NOMBRE = 48; // 48 caracteres * 2 bytes = 96 bytes

    static void caso2_RegistroDirecto() throws IOException {
        System.out.println("===== CASO 2: REGISTRO 4.573 (aleatorio) =====");

        // --- Preparación: crear un fichero con 5.000 clientes ---
        try (RandomAccessFile raf = new RandomAccessFile("clientes.dat", "rw")) {
            raf.setLength(0); // vaciamos por si ya existía
            for (int i = 0; i < 5000; i++) {
                raf.writeInt(i);                                   // 4 bytes
                escribirTextoFijo(raf, "Cliente_" + i, LONG_NOMBRE); // 96 bytes
            }
            System.out.println("  Fichero creado: " + raf.length() + " bytes ("
                    + raf.length() / TAM_CLIENTE + " registros)");
        }

        // --- Lo importante: saltar directamente al registro 4.573 ---
        int numRegistro = 4573; // contamos desde 0
        try (RandomAccessFile raf = new RandomAccessFile("clientes.dat", "r")) {
            long posicion = (long) numRegistro * TAM_CLIENTE; // 457.300
            raf.seek(posicion); // el puntero salta ahí SIN leer nada de antes

            int id = raf.readInt();
            String nombre = leerTextoFijo(raf, LONG_NOMBRE);
            System.out.println("  Saltado al byte " + posicion);
            System.out.println("  Leído -> id=" + id + ", nombre=" + nombre);
        }
        System.out.println();
    }

    // =====================================================================
    // CASO 3: CONFIGURACIÓN -> SECUENCIAL (leer entero al arrancar)
    // =====================================================================
    static void caso3_Configuracion() throws IOException {
        System.out.println("===== CASO 3: CONFIGURACIÓN (secuencial) =====");

        // --- Preparación: crear el fichero de configuración ---
        // Aquí SIN append (no ponemos 'true'): se sobrescribe cada vez.
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("config.txt", StandardCharsets.UTF_8))) {
            bw.write("idioma=es");      bw.newLine();
            bw.write("tema=oscuro");    bw.newLine();
            bw.write("puerto=8080");    bw.newLine();
        }

        // --- Lo importante: leerlo entero, de principio a fin, en orden ---
        try (BufferedReader br = new BufferedReader(
                new FileReader("config.txt", StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("="); // "idioma=es" -> ["idioma", "es"]
                System.out.println("  Clave: " + partes[0] + " | Valor: " + partes[1]);
            }
        }
        System.out.println();
    }

    // =====================================================================
    // CASO 4: RESERVAS DE HOTEL -> ALEATORIO (actualizar solo una habitación)
    // Registro = habitación (int, 4) + cliente (30 chars = 60) + noches (int, 4) = 68 bytes
    // =====================================================================
    static final int TAM_RESERVA = 68;
    static final int LONG_CLIENTE = 30;

    static void caso4_ReservasHotel() throws IOException {
        System.out.println("===== CASO 4: RESERVAS HOTEL (aleatorio, rw) =====");

        // --- Preparación: 10 habitaciones (de la 1 a la 10), todas libres ---
        try (RandomAccessFile raf = new RandomAccessFile("reservas.dat", "rw")) {
            raf.setLength(0);
            for (int hab = 1; hab <= 10; hab++) {
                raf.writeInt(hab);
                escribirTextoFijo(raf, "LIBRE", LONG_CLIENTE);
                raf.writeInt(0);
            }
        }

        // --- Lo importante: actualizar SOLO la habitación 7 ---
        int habitacion = 7;
        try (RandomAccessFile raf = new RandomAccessFile("reservas.dat", "rw")) {
            // Las habitaciones empiezan en 1, pero las posiciones en 0 -> restamos 1
            long posicion = (long) (habitacion - 1) * TAM_RESERVA;

            // Nos saltamos el int del número de habitación (4 bytes) porque ese no cambia
            raf.seek(posicion + 4);
            escribirTextoFijo(raf, "Ana García", LONG_CLIENTE); // sobrescribe el cliente
            raf.writeInt(3);                                    // sobrescribe las noches
            System.out.println("  Habitación " + habitacion + " actualizada (byte " + posicion + ")");
        }

        // --- Comprobación: mostramos todas para ver que solo cambió la 7 ---
        try (RandomAccessFile raf = new RandomAccessFile("reservas.dat", "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int hab = raf.readInt();
                String cliente = leerTextoFijo(raf, LONG_CLIENTE);
                int noches = raf.readInt();
                System.out.println("  Hab " + hab + " -> " + cliente + " (" + noches + " noches)");
            }
        }
        System.out.println();
    }

    // =====================================================================
    // MÉTODOS AUXILIARES: texto de longitud fija
    // Para que todos los registros midan lo mismo, el texto se rellena con
    // espacios hasta 'longitud' caracteres (o se corta si es más largo).
    // writeChars escribe 2 bytes por carácter.
    // =====================================================================
    static void escribirTextoFijo(RandomAccessFile raf, String texto, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(texto);
        sb.setLength(longitud); // corta si sobra; si falta, rellena con '\0'
        for (int i = texto.length(); i < longitud; i++) {
            sb.setCharAt(i, ' '); // cambiamos los '\0' por espacios
        }
        raf.writeChars(sb.toString());
    }

    static String leerTextoFijo(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim(); // quitamos los espacios de relleno
    }
}
