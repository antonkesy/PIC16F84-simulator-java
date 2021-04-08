package de.hso.rechenarchitektur.picsimulator.pic16f8x;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.ArithmeticLogicUnit.AluOperations;

import java.util.List;

public class PIC16F8X {

    private long runTime = 0;

    //in kHz
    private long quarzSpeed = 32;

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
        int result = ArithmeticLogicUnit.operation(aluOperation, ram, wRegister, instruction.getFK());
        if (instruction.getBD() == 0) {
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
                wRegister = ArithmeticLogicUnit.add(ram, wRegister, currentInstruction.getFK());
                break;
            case ANDLW:
                cycles = 1;
                wRegister = ArithmeticLogicUnit.and(ram, wRegister, currentInstruction.getFK());
                break;
            case CALL:
                cycles = 2;
                stack.AddNewAddress(getRam().getPCL());
                getRam().setPCL(currentInstruction.getFK());
                break;
            case CLRWDT:
                break;
            case GOTO:
                cycles = 2;
                ram.setPCL(currentInstruction.getFK());
                break;
            case IORLW:
                cycles = 1;
                wRegister = ArithmeticLogicUnit.or(ram, wRegister, currentInstruction.getFK());
                break;
            case MOVLW:
                wRegister = currentInstruction.getFK();
                break;
            case RETFIE:
                break;
            case RETLW:
                cycles = 2;
                wRegister = currentInstruction.getFK();
                getRam().setPCL(stack.getCurrent());
                break;
            case RETURN:
                cycles = 2;
                getRam().setPCL(stack.getCurrent());
                break;
            case SLEEP:
                break;
            case SUBLW:
                cycles = 1;
                wRegister = ArithmeticLogicUnit.sub(ram, currentInstruction.getFK(), wRegister);
                break;
            case XORLW:
                cycles = 1;
                wRegister = ArithmeticLogicUnit.xor(ram, wRegister, currentInstruction.getFK());
                break;
        }
        calculateRunTime(cycles);
        getNextInstruction();
    }

    private void calculateRunTime(int cycles) {
        runTime += (cycles * getTimePerCycle());
    }

    private long getTimePerCycle() {
        //TODO
        //bei 1MHz => 4micoSecs
        //
        //runtime = runtimeCount * 1/(currFrequency * 1000) * 4;
        //runtime in MS
        //freq in kHz
        return quarzSpeed * 1000 * 4;
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

    public long getQuarzSpeed() {
        return quarzSpeed;
    }

    public void setQuarzSpeed(int quarzSpeed) {
        this.quarzSpeed = quarzSpeed;
    }


    /**
     * Debug Test fuer Instruktionen lesen und makieren in der GUI
     */
    public int nextInstruction() {
        instructionHandler();
        return currentInstructionInRegister.getPositionLineInFile();
    }

    public String runTimeToString() {
        return runTime + "\u00B5s";
    }
}
