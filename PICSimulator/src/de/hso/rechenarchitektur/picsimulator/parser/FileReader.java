package de.hso.rechenarchitektur.picsimulator.parser;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    private final File file;

    Map<Integer, Instruction> instructionMap;

    ArrayList<String> lines;
    Map<String, Integer> ownSymbolsMap;
    Map<String, Integer> jumpMap;

    int nextProgramMemoryPosition = 0;

    public FileReader(File filePath) {
        this.file = filePath;
        lines = new ArrayList<>();
        ownSymbolsMap = new HashMap<>();
        jumpMap = new HashMap<>();
        instructionMap = new HashMap<>();
        readFile();

        System.out.println("\nownSymbols:");
        for (Map.Entry<String, Integer> entry : ownSymbolsMap.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        System.out.println("\nJumper:");
        for (Map.Entry<String, Integer> entry : jumpMap.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        System.out.println("\nInstructions:");
        for (Map.Entry<Integer, Instruction> entry : instructionMap.entrySet()) {
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
        String lineString = line;
        if (line.contains(";")) {
            lineString = line.substring(0, line.indexOf(";"));
        }
        //Line splitten und Leerzeichen entfernen
        String[] lineSplit = Arrays.stream(lineString.split(" ")).filter(t -> t.length() > 0).toArray(String[]::new);

        //Nach Anzahl der Splits entscheiden, was die Line bedeuten kann
        switch (lineSplit.length) {
            case 2:
                //jump
                jumpMap.put(lineSplit[1], nextProgramMemoryPosition);
                break;
            case 3:
                //spezial
                if (lineSplit[1].equals("device")) {
                    //SetDevice
                } else if (lineSplit[1].equals("list")) {
                    //Set lts Zeichenlaenge
                } else if (lineSplit[1].equals("org")) {
                    //Set Programm start
                } else {
                    System.out.println("todo Interpreter" + line.charAt(36));
                }
                break;
            case 4:
                //Symbole
                if (lineSplit[2].equalsIgnoreCase("equ")) {
                    int value = getIntegerValueFromCode(lineSplit[3]);
                    ownSymbolsMap.put(lineSplit[1], value);
                    break;
                }
                //non param Instruktions
                int programMemoryPosition = getIntegerValueFromCode(lineSplit[0]);
                nextProgramMemoryPosition = programMemoryPosition + 1;
                instructionMap.put(programMemoryPosition, new Instruction(getInstructionType(lineSplit[3])));
                //TODO Hex to Instruction
                break;
            case 5:
                //Instruktions + param
                programMemoryPosition = Integer.decode("0x" + lineSplit[0]);
                nextProgramMemoryPosition = programMemoryPosition + 1;
                if (lineSplit[3].equalsIgnoreCase("goto")) {
                    System.out.println("GoTo " + lineSplit[4]); //TODO
                } else if (lineSplit[4].contains(",")) {
                    String[] values = lineSplit[4].split(",");
                    instructionMap.put(programMemoryPosition,
                            new Instruction(getInstructionType(lineSplit[3]),
                                    getIntegerValueFromCode(values[0]),
                                    getIntegerValueFromCode(values[1])));
                } else {
                    instructionMap.put(programMemoryPosition, new Instruction(getInstructionType(lineSplit[3]), getIntegerValueFromCode(lineSplit[4])));
                }
                break;
        }
    }

    public List<String> getLineList() {
        return lines;
    }

    private int getIntegerValueFromCode(String code) {
        if (ownSymbolsMap.containsKey(code)) {
            return ownSymbolsMap.get(code);
        }
        if (code.toLowerCase().endsWith("h")) {
            return Integer.decode("0x" + code.substring(0, code.length() - 1));
        } else if (code.toLowerCase().endsWith("b")) {
            return Integer.parseInt(code.substring(0, code.length() - 1), 2);
        } else if (code.equalsIgnoreCase("w")) {
            //MOVF d Flag
            return 0;
        } else if (code.equalsIgnoreCase("f")) {
            //MOVF d Flag
            return 1;
        }
        return Integer.parseInt(code);
    }

    private InstructionType getInstructionType(String typeAsString) {
        for (InstructionType instructionType : InstructionType.values()) {
            if (instructionType.code.equals(typeAsString)) {
                return instructionType;
            }
        }
        return InstructionType.NOP;
    }

    //TODO("Bitmaske Hex umrechnung linke bits fuer Gruppen, naechster Byte fuer Typ"

}


