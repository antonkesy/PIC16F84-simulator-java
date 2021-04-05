package de.hso.rechenarchitektur.picsimulator.pic16f8x;


import java.util.List;

public class PIC16F8X {

    private int quarzSpeed = 32;

    private Stack stack;
    private final ProgramMemory programMemory;
    private RandomAccessMemory ram;
    private InstructionLine currentInstructionInRegister;

    private int wRegister;

    public PIC16F8X(List<InstructionLine> instructionLineList) {
        programMemory = new ProgramMemory(instructionLineList);
        reset();
    }

    private void reset() {
        currentInstructionInRegister = new InstructionLine();
        ram = new RandomAccessMemory();
        stack = new Stack();
    }

    private void getNextInstruction() {
        currentInstructionInRegister = programMemory.getInstructionAt(ram.getPCL());
        ram.setPCL(ram.getPCL() + 1);
    }

    private void InstructionWithDestinationBit(Instruction instruction, AluOperations aluOperation) {
        int result = alu(aluOperation, instruction.getFK());
        if (instruction.getBD() == 1) {
            wRegister = result;
        } else {
            ram.setDataToAddress(instruction.getFK(), result);
        }
    }

    private void instructionHandler() {
        int cycles = 0; //TODO
        Instruction currentInstruction = currentInstructionInRegister.getInstruction();
        switch (currentInstruction.getType()) {
            //TODO("alle implementieren")
            case ADDWF:
                cycles = 1;
                InstructionWithDestinationBit(currentInstruction, AluOperations.ADD);
                break;
            case ANDWF:
                cycles = 1;
                InstructionWithDestinationBit(currentInstruction, AluOperations.AND);
                break;
            case CLRF:
                ram.setDataToAddress(currentInstruction.getFK(), 0);
                break;
            case CLRW:
                cycles = 1;
                wRegister = 0;
                break;
            case COMF:
                break;
            case DECF:
                break;
            case DECFSZ:
                break;
            case INCF:
                break;
            case INCFSZ:
                break;
            case IORWF:
                break;
            case MOVF:
                int valueOfAddress = ram.getDataFromAddress(currentInstruction.getFK());
                ram.setZeroFlag(false);
                //wenn d = 1 -> ueberpruefen auf null
                if (currentInstruction.getBD() == 1 && valueOfAddress == 0) {
                    ram.setZeroFlag(true);
                } else {
                    wRegister = valueOfAddress;
                }
                break;
            case MOVWF:
                ram.setDataToAddress(currentInstruction.getFK(), wRegister);
                break;
            case NOP:
                //No Operation
                break;
            case RLF:
                break;
            case RRF:
                break;
            case SUBWF:
                break;
            case SWAPF:
                break;
            case XORWF:
                break;
            case BCF:
                break;
            case BSF:
                break;
            case BTFSC:
                break;
            case BTFSS:
                break;
            case ADDLW:
                cycles = 1;
                wRegister = alu(AluOperations.ADD, currentInstruction.getFK());
                break;
            case ANDLW:
                cycles = 1;
                wRegister = alu(AluOperations.AND, currentInstruction.getFK());
                break;
            case CALL:
                break;
            case CLRWDT:
                break;
            case GOTO:
                ram.setPCL(currentInstruction.getFK());
                break;
            case IORLW:
                cycles = 1;
                wRegister = alu(AluOperations.OR, currentInstruction.getFK());
                break;
            case MOVLW:
                System.out.println("movelw " + currentInstruction.getFK());
                wRegister = currentInstruction.getFK();
                break;
            case RETFIE:
                break;
            case RETLW:
                break;
            case RETURN:
                break;
            case SLEEP:
                break;
            case SUBLW:
                cycles = 1;
                wRegister = alu(AluOperations.SUB, currentInstruction.getFK());
                break;
            case XORLW:
                cycles = 1;
                wRegister = alu(AluOperations.XOR, currentInstruction.getFK());
                break;
        }
        getNextInstruction();
    }

    public int getCurrentLine() {
        return currentInstructionInRegister.getPositionLineInFile() - 1;
    }

    public Stack getStack() {
        return stack;
    }

    public RandomAccessMemory getRam() {
        return ram;
    }

    public int getWRegister() {
        return wRegister;
    }

    public int getQuarzSpeed() {
        return quarzSpeed;
    }

    public void setQuarzSpeed(int quarzSpeed) {
        this.quarzSpeed = quarzSpeed;
    }

    enum AluOperations {
        ADD, SUB, AND, OR, XOR
    }

    private int alu(AluOperations operation, int otherValue) {
        //todo check for flags
        int result = wRegister;
        switch (operation) {
            case ADD:
                result += otherValue;
                break;
            case SUB:
                result -= otherValue;
                break;
            case AND:
                result &= otherValue;
                break;
            case OR:
                result |= otherValue;
                break;
            case XOR:
                result ^= otherValue;
                break;
        }
        return result;
    }

    /**
     * Debug Test fuer Instruktionen lesen und makieren in der GUI
     */
    public int nextInstruction() {
        instructionHandler();
        return currentInstructionInRegister.getPositionLineInFile();
    }

    /**
     * Resets PIC
     *
     * @return selected index todo
     */
    public int resetCall() {
        reset();
        return 0;
    }

}
