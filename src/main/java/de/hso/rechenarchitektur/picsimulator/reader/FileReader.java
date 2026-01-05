package de.hso.rechenarchitektur.picsimulator.reader;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionDecoder;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.LSTLine;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Reads, interpret LST Files to Instructions
 *
 * <p>One FileReader per File to read
 */
public class FileReader {
  private final File file;

  private final ArrayList<InstructionLine> programMemoryMap;

  private final ArrayList<LSTLine> lines;

  /**
   * Constructor starts reading file
   *
   * @param file
   */
  public FileReader(File file) {
    this.file = file;
    lines = new ArrayList<>();
    programMemoryMap = new ArrayList<>();
    readFile();
  }

  /** Reads file per line and calls interpreter */
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

  /**
   * Interprets line
   *
   * @param line
   */
  private void interpretLine(String line) {
    // Direkt abbrechen, wenn nicht mit einer Adresse beginnt
    if (line.startsWith(" ")) return;
    // Line splitten und Leerzeichen entfernen
    String[] lineSplit =
        Arrays.stream(line.split(" ")).filter(t -> t.length() > 0).toArray(String[]::new);
    // Fuegt neue Instruktion mit allen noetigen Informationen in die Liste
    programMemoryMap.add(
        new InstructionLine(
            lines.size(), // aktuelle Line des Befehls im LST
            Integer.decode("0x" + lineSplit[0]), // Position im ProgramMemory
            InstructionDecoder.decodeInstruction(Integer.decode("0x" + lineSplit[1]))) // Opcode
        );
  }

  public List<LSTLine> getLineList() {
    return lines;
  }

  public List<InstructionLine> getInstructionLineList() {
    return programMemoryMap;
  }
}
