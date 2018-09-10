package saarland.cispa.sopra;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public final class WorldParser {

    private WorldParser() {

    }

    public static World parseMap(File mapFile, long seed, Map<Character, Swarm> swarms) throws IOException {

        Field[][] fields = null;

        try (BufferedReader bReader = Files.newBufferedReader(Paths.get(mapFile.getPath()))) {

            String line = bReader.readLine();

            String[] splittedlines = line.split("\\\\n");

            int width = checkNumber(splittedlines[0].toCharArray());
            int height = checkNumber(splittedlines[1].toCharArray());

            if (splittedlines.length > height + 2) {
                throw new IllegalArgumentException("Map could not be parsed correctly");
            }
            fields = new Field[width][height];

            for (int i = 2; i < splittedlines.length; i++) {

                char[] actualLine = splittedlines[i].toCharArray();

                int x = 0;

                for (char actualChar : actualLine) {
                    if (x >= width) {
                        throw new IllegalArgumentException("Map could not be parsed correctly");
                    }
                    checkLetter(actualChar, fields, x, i - 2);
                    x++;
                }
            }

        }

        Map<Integer, Ant> ants = spawnAnts(swarms, fields);
        World welt = new World(fields, seed, ants, swarms);
        welt.setAntlion();
        return welt;
    }


    private static void checkLetter(char fieldType, Field[][] fields, int x, int y) {

        char antLion = '=';
        char normal = '.';

        if (fieldType >= 65 && fieldType <= 90 || fieldType >= 97 && fieldType <= 122) {
            fields[x][y] = new Base(fieldType, x, y);
            return;
        }
        if (fieldType >= 49 && fieldType <= 57) {
            fields[x][y] = new Normal(x, y, fieldType - 48);
            return;
        }

        if (fieldType == normal) {
            fields[x][y] = new Normal(x, y, 0);
            return;
        }
        if (fieldType == antLion) {
            fields[x][y] = new Antlion(x, y);
            return;
        } else {
            throw new IllegalArgumentException("Map could not be parsed correctly (Invalid Character)");
        }

    }

    private static Map<Integer, Ant> spawnAnts(Map<Character, Swarm> swarms, Field[][] fields) {
        HashMap<Integer, Ant> ants = new HashMap<>();
        for (Field[] fieldh : fields) {
            for (Field field : fieldh) {
                Character type = field.getType();
                if (type != '.' && type != '=' && type != '#') {
                    if (swarms.get(type).getIdent() != type) {
                        throw new IllegalArgumentException("wrong swarm");
                    }
                    Ant ant = new Ant(swarms.get(type), ants.size(), field);
                    ants.put(ants.size(), ant);
                }
            }
        }
        return ants;
    }

    private static int checkNumber(char[] number) {

        int result = 0;
        for (char digit : number) {
            if (digit < 48 || digit > 57) {
                throw new IllegalArgumentException("Map could not be parsed correctly : Illegal Header");
            } else {
                result = result * 10 + digit - 48;
            }
        }
        return result;
    }
}
