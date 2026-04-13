package co.edu.uptc.configlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class YamlLoader {
    private final List<String> lines = new ArrayList<>();

    public boolean load(String fileName) {
        lines.clear();

        if (loadExternal(fileName)) {
            return true;
        }

        return loadInternal(fileName);
    }

    private boolean loadExternal(String fileName) {
        try {
            Path path = Path.of(fileName);
            if (Files.exists(path)) {
                lines.addAll(Files.readAllLines(path));
                return true;
            }
        } catch (IOException e) {

        }
        return false;
    }

    private boolean loadInternal(String fileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {

            if (input == null) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            reader.lines().forEach(lines::add);
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public List<String> getLines() {
        return new ArrayList<>(lines);
    }

    public List<String> getCleanLines() {
        List<String> clean = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty() && !trimmed.startsWith("#")) {
                clean.add(line);
            }
        }

        return clean;
    }

    public static int countWhites(String text) {
        int counter = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (Character.isLetter(c)) {
                break;
            }

            if (c == ' ') {
                counter++;
            }
        }

        return counter;
    }
}
