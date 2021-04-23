package de.hso.rechenarchitektur.picsimulator.reader;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionDecoder;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.LSTLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileReader {
    private final File file;

    private final ArrayList<InstructionLine> programMemoryMap;

    private final ArrayList<LSTLine> lines;

    public FileReader(File file) {
        this.file = file;
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
            Scanner myReader = new Scanner(file, String.valueOf(StandardCharsets.ISO_8859_1));
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                lines.add(new LSTLine(line));
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
                InstructionDecoder.decodeInstruction(Integer.decode("0x" + lineSplit[1])))
        );
    }

    public List<LSTLine> getLineList() {
        return lines;
    }

    public List<InstructionLine> getInstructionLineList() {
        return programMemoryMap;
    }
}


