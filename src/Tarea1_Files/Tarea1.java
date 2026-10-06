package Tarea1_Files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tarea1 {

    private static final File baseFile = new File("datos/ud1");

    public static void main(String[] args) {
        try{

            System.out.println("0) Initial state clear. Does " + baseFile.getPath() + " exist? " + baseFile.exists() + " (mkdir() gona fail due to that");

            // 1. mkdir y mkdirs
            File practice = new File(baseFile, "practice");

            boolean withMkdir = practice.mkdir();
            System.out.println("mkdir() = " + withMkdir);

            boolean withMkdirs = practice.mkdirs();
            System.out.println("mkdirs() = " + withMkdirs);

            // 2. Crate letter.txt
            File letter = new File(practice, "letter.txt");

            System.out.println(" First time = " + letter.createNewFile());
            System.out.println(" Second time = " + letter.createNewFile());

            // 3. Write, Rename, Delete
            try (FileWriter writer = new FileWriter(letter)) {
                writer.write("That's the first line of the letter. \n");
                writer.write("That's the second line of the letter.");
            }

            File letterOK = new File(practice, "letter_ok.txt");
            boolean renamed = letter.renameTo(letterOK);
            System.out.println("Rename from " + letter.getName() + " to " + letterOK.getName() + " = " + renamed);

            File temp = new File(practice, "temp");
            System.out.println("Creation of: " + temp.getName() + " = " + temp.createNewFile());
            System.out.println("Deleting = " + temp.delete());

            // 4. File info
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            System.out.println("Information of: " + letterOK.getName() + "\n" +
                               "Absolute Path: " + letterOK.getAbsolutePath() + "\n" +
                               "Length: " + letterOK.length() + "\n" +
                               "Last Modified: " + format.format(new Date(letterOK.lastModified())));

            // 5. List directory contents

            File[] entries = practice.listFiles();
            if (entries != null) {
                System.out.println("Total entries: " + entries.length);
                for (File entry : entries) {
                    String type = entry.isDirectory() ? "FOLDER" : "FILE";
                    System.out.printf(" %-7s %-16s %7d bytes (%.2f KB)%n",
                            type, entry.getName(), entry.getAbsoluteFile().length(), entry.length()/1024.0);
                }
            }

            // 6. File exception control

            File[] exception = letterOK.listFiles();
            if (exception == null) {
                System.out.println("Controlled warning: Returned null due to " + letterOK.getName() + " is a file, not a directory.");
            }




        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
