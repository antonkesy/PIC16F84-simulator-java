package de.hso.rechenarchitektur.picsimulator.parser;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionDecoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    private final File file;

    Map<Integer, Instruction> programMemoryMap;

    ArrayList<String> lines;

    public FileReader(File filePath) {
        this.file = filePath;
        lines = new ArrayList<>();
        programMemoryMap = new HashMap<>();
        readFile();

        System.out.println("\nInstructions:");
        for (Map.Entry<Integer, Instruction> entry : programMemoryMap.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().toString());
        }
    }

    private void readFile() {
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                lines.add(line);
                interpretLine(line);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void interpretLine(String line) {
        //Wenn nicht mit einer Adresse
        if (line.startsWith(" ")) return;
        
        //Line splitten und Leerzeichen entfernen
        String[] lineSplit = Arrays.stream(line.split(" ")).filter(t -> t.length() > 0).toArray(String[]::new);

        programMemoryMap.put(Integer.decode("0x" + lineSplit[0]), InstructionDecoder.decodeInstruction(lineSplit[1]));
    }

    public List<String> getLineList() {
        return lines;
    }
}


