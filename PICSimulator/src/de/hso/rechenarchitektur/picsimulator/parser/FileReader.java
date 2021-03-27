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

    int nextProgramMemoryPosition = 0;

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
                    int value;
                    //Hex Indikator entfernen
                    if (lineSplit[3].toLowerCase().endsWith("h")) {
                        value = Integer.decode("0x" + lineSplit[3].substring(0, lineSplit[3].length() - 1));
                    } else if (lineSplit[3].toLowerCase().endsWith("b")) {
                        value = Integer.parseInt(lineSplit[3].substring(0, lineSplit[3].length() - 1), 2);
                    } else {
                        //TODO Oct case
                        value = Integer.parseInt(lineSplit[3]);
                    }
                    ownSymbols.put(lineSplit[1], value);
                    break;
                }
                //non param Instruktions
                System.out.println("Instruction\t" + lineSplit[0] + "\t" + lineSplit[1] + "\t");
                //TODO Hex to Instruction
                break;
            case 5:
                //Instruktions + param
                System.out.println("Instruction\t" + lineSplit[0] + "\t" + lineSplit[1] + "\t");
                break;
        }
    }

    public List<String> getLineList() {
        return lines;
    }
}


