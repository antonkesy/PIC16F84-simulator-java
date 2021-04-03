package de.hso.rechenarchitektur.picsimulator.pic16f8x;


import java.util.List;

public class PIC16F8X {


    private Stack stack;
    private ProgramMemory programMemory;
    private RandomAccessMemory ram;
    private InstructionLine currentInstructionInRegister;


    private int wRegister;

    //Flags
    private boolean carryFlag, digitCarryFlag, zeroFlag; //todo("in ram speichern")


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

    private void instructionDecoder() {
        Instruction currentInstruction = currentInstructionInRegister.getInstruction();
        switch (currentInstruction.getType()) {
            //TODO("alle implementieren")
            case ADDWF:
                wRegister = alu(AluOperations.ADD, currentInstruction.getFK()); //was ist d?
                break;
            case ANDWF:
                wRegister = alu(AluOperations.AND, currentInstruction.getFK()); //was ist d?
                break;
            case CLRF:
                break;
            case CLRW:
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
                break;
            case MOVWF:
                ram.setDataToAddress(currentInstruction.getFK(), wRegister);
                break;
            case NOP:
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
                wRegister = alu(AluOperations.AND, currentInstruction.getFK());
                break;
            case ANDLW:
                break;
            case CALL:
                break;
            case CLRWDT:
                break;
            case GOTO:
                break;
            case IORLW:
                break;
            case MOVLW:
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
                break;
            case XORLW:
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

    enum AluOperations {
        ADD, SUB, AND, OR, XOR
    }

    private int alu(AluOperations operation, int otherValue) {
        //if null -> zeroflag = true
        //cary und digitCarry bei Bereichueberlauf
        //TODO
        return 0;
    }

    /**
     * Debug Test fuer Instruktionen lesen und makieren in der GUI
     */
    public int nextInstruction() {
        getNextInstruction();
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
