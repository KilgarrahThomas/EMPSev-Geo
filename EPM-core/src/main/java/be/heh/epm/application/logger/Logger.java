package be.heh.epm.application.logger;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

// ATTRIBUTES

// CONSTRUCTOR

// GETTERS & SETTERS

// METHODS
    private static void save (String level, String place, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try {
            FileOutputStream file = new FileOutputStream("log.txt", true);
            OutputStreamWriter out = new OutputStreamWriter(file, Charset.forName("UTF8"));

            out.write(LocalDateTime.now().format(formatter) + " - " + level + "\n");
            out.write(place + "\n");
            out.write(message + "\n");
            out.close();
        }
        catch (IOException e) {

        }
    }

    public static void LogInfo(String place, String message) {
        save("INFO", place, message);
    }

    public static void LogWarn(String place, String message) {
        save("WARN", place, message);
    }

    public static void LogError(String place, String message) {
        save("ERROR", place, message);
    }
}
