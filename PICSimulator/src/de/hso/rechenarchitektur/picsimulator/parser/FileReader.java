package de.hso.rechenarchitektur.picsimulator.parser;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionDecoder;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    private final File file;

    private final ArrayList<InstructionLine> programMemoryMap;

    private final ArrayList<String> lines;

    public FileReader(File filePath) {
        this.file = filePath;
        lines = new ArrayList<>();
        programMemoryMap = new ArrayList<>();
        readFile();

        System.out.println("\nInstructions:");
        for (InstructionLine entry : programMemoryMap) {
            System.out.println(entry);
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

        //Fuegt neue Instruktion mit allen noetigen Informationen in die Liste
        programMemoryMap.add(new InstructionLine(
                lines.size(),
                Integer.decode("0x" + lineSplit[0]),
                InstructionDecoder.decodeInstruction(lineSplit[1]))
        );
    }

    public List<String> getLineList() {
        return lines;
    }

    public List<InstructionLine> getInstructionLineList() {
        return programMemoryMap;
    }
}


