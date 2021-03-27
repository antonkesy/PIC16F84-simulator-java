package de.hso.rechenarchitektur.picsimulator.parser;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    private final File file;

    Map<Integer, Instruction> instructionList;

    ArrayList<String> lines;
    Map<String, Integer> ownSymbols;
    Map<String, Integer> jumpMap;

    int currentProgramMemoryPosition = 0;

    public FileReader(File filePath) {
        this.file = filePath;
        lines = new ArrayList<>();
        ownSymbols = new HashMap<>();
        jumpMap = new HashMap<>();
        readFile();

        System.out.println("\nownSymbols:");
        for (Map.Entry<String, Integer> entry : ownSymbols.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        System.out.println("\nJumper:");
        for (Map.Entry<String, Integer> entry : jumpMap.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
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
        //Goto
        if (line.charAt(27) != (' ')) {
            jumpMap.put(line.substring(27, 35).replaceAll("\\s+", ""), currentProgramMemoryPosition + 1);
            return;
        }

        //Spezial LST
        if (line.startsWith(" ")) {
            if (line.length() > 36 && line.charAt(36) != ';' && line.charAt(36) != ' ') {
                //Eigene Symbole
                if (line.length() <= 39) return;
                if (line.substring(36).contains("equ")) {
                    //Ab hier werden Symbole definiert
                    String eqlLine = line.substring(36);
                    //Value von Symbol
                    String value = eqlLine.substring(eqlLine.indexOf("equ") + 3).replaceAll("\\s+", "");
                    //Hex Indikator entfernen
                    if (value.endsWith("h")) {
                        value = value.substring(0, value.length() - 1);
                    }
                    ownSymbols.put(eqlLine.substring(0, eqlLine.indexOf(' ')), Integer.decode("0x" + value));
                } else if (line.substring(36).contains("device 16F84")) {
                    //SetDevice
                } else if (line.substring(36).contains("list c=")) {
                    //Set lts Zeichenlaenge
                } else if (line.substring(36).contains("org ")) {
                    //Set Programm start
                } else {
                    System.out.println("todo Interpreter" + line.charAt(36));
                }
            }
            return;
        }

        String lineWithOutComments = line;
        if (line.contains(";")) {
            lineWithOutComments = line.substring(0, line.indexOf(';'));
        }

        int memorySpot = Integer.decode("0x" + line.substring(0, 4));
        int instructionHex = Integer.decode("0x" + line.substring(5, 9));

        String instructionString = lineWithOutComments.substring(36);
        String instructionValue = "";
        if (lineWithOutComments.length() > 36 && lineWithOutComments.contains(" ")) {
            int firstSpaceAfterInstruction = lineWithOutComments.substring(36).indexOf(" ") + 36;
            System.out.println(firstSpaceAfterInstruction);
            instructionString = lineWithOutComments.substring(36, firstSpaceAfterInstruction);
            if (lineWithOutComments.length() > firstSpaceAfterInstruction) {
                instructionValue = lineWithOutComments.substring(firstSpaceAfterInstruction).replaceAll("\\s+", "");
            }
        }
        /*
        if (lineWithOutComments.length() > 42) {
            instructionString = lineWithOutComments.substring(36, 42);
            instructionValue = lineWithOutComments.substring(42);
        }
        */
        if (instructionValue.length() > 0) {
            //Check if two values
            if(instructionValue.contains(";")){
                
            }
            currentProgramMemoryPosition = Integer.decode("0x" + instructionValue);
            //TODO only when number
        }

        Instruction instruction;
        switch (instructionString.toLowerCase()) {
            case "movelw":
                instruction = new Instruction(InstructionType.MOVLW, Integer.decode("0x" + instructionString));
            case "addlw":
                instruction = new Instruction(InstructionType.ADDLW, Integer.decode("0x" + instructionString));
                break;
            case "sublw":
                instruction = new Instruction(InstructionType.SUBLW, Integer.decode("0x" + instructionString));
                break;
        }

        System.out.println(memorySpot + "\t" + Integer.toHexString(instructionHex)
                + "\t" + instructionString + "\t" + instructionValue + "\t");


    }

    public List<String> getLineList() {
        return lines;
    }
}


