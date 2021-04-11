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

    private void instructionHandler() {
        int result; //optional Variable fuer Zwischenergebnisse
        int cycles = 1; //TODO immer mindestens einer
        Instruction currentInstruction = currentInstructionInRegister.getInstruction();
        switch (currentInstruction.getType()) {
            case ADDWF:
                result = ArithmeticLogicUnit.add(ram, wRegister, ram.getDataFromAddress(currentInstruction.getFK()));
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                break;
            case ANDWF:
                result = ArithmeticLogicUnit.and(ram, wRegister, ram.getDataFromAddress(currentInstruction.getFK()));
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                break;
            case CLRF:
                ram.setDataToAddress(currentInstruction.getFK(), 0);
                ram.setZeroFlag(true);
                break;
            case CLRW:
                wRegister = 0;
                ram.setZeroFlag(true);
                break;
            case COMF:
                result = ArithmeticLogicUnit.getCompliment(currentInstruction.getFK());
                //TODO decrement 0 -> 255?
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result & 0xFF);
                ram.setZeroFlag(result == 0);
                break;
            case DECF: //Fallthroug
            case INCF:
                result = ram.getDataFromAddress(currentInstruction.getFK());
                result += currentInstruction.getType() == InstructionType.INCF ? 1 : -1;
                //TODO decrement 0 -> 255?
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result & 0xFF);
                ram.setZeroFlag(result == 0);
                break;
            case DECFSZ:
            case INCFSZ:
                result = ram.getDataFromAddress(currentInstruction.getFK());
                result += currentInstruction.getType() == InstructionType.INCFSZ ? 1 : -1;
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                if (result == 0) {
                    skipNextInstruction();
                }
                break;
            case IORWF:
                wRegister = ArithmeticLogicUnit.or(ram, wRegister, ram.getDataFromAddress(currentInstruction.getFK()));
                break;
            case MOVF:
                int valueOfAddress = ram.getDataFromAddress(currentInstruction.getFK());
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), valueOfAddress);
                ram.setZeroFlag(valueOfAddress == 0);
                break;
            case MOVWF:
                ram.setDataToAddress(currentInstruction.getFK(), wRegister);
                break;
            case NOP:
                //No Operation
                break;
            case RLF:
                result = getRotateLeftThroughCarry(ram.getDataFromAddress(currentInstruction.getFK()));
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                break;
            case RRF:
                result = getRotateRightTroughCarry(ram.getDataFromAddress(currentInstruction.getFK()));
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                break;
            case SUBWF:
                break;
            case SWAPF:
                result = getSwapNibbles(ram.getDataFromAddress(currentInstruction.getFK()));
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                break;
            case XORWF:
                result = ArithmeticLogicUnit.xor(ram, wRegister, ram.getDataFromAddress(currentInstruction.getFK()));
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                break;
            case BCF:
                ram.setDataToAddress(
                        currentInstruction.getFK(),
                        getBitClearF(currentInstruction.getBD(),
                                ram.getDataFromAddress(currentInstruction.getFK()))
                );
                break;
            case BSF:
                ram.setDataToAddress(
                        currentInstruction.getFK(),
                        getBitSetF(currentInstruction.getBD(),
                                ram.getDataFromAddress(currentInstruction.getFK()))
                );
                break;
            case BTFSC:
                //TODO testen!
                if (!isBitInFActive(currentInstruction.getBD(), currentInstruction.getFK())) {
                    skipNextInstruction();
                }
                break;
            case BTFSS:
                //TODO testen!
                if (isBitInFActive(currentInstruction.getBD(), currentInstruction.getFK())) {
                    skipNextInstruction();
                }
                break;
            case ADDLW:
                wRegister = ArithmeticLogicUnit.add(ram, wRegister, currentInstruction.getFK());
                break;
            case ANDLW:
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
                wRegister = ArithmeticLogicUnit.or(ram, wRegister, currentInstruction.getFK());
                break;
            case MOVLW:
                wRegister = currentInstruction.getFK();
                break;
            case RETFIE:
                cycles = 2;
                ram.setPCL(stack.pop());
                //TODO INTCON Bit setzen
                ram.setIntcon(0b100_0000);
                break;
            case RETLW: //Fallthroug
                wRegister = currentInstruction.getFK();
            case RETURN:
                cycles = 2;
                getRam().setPCL(stack.pop());
                break;
            case SLEEP:
                ram.setPowerDownFlag(false);
                ram.setTimeOutFlag(true);
                //todo clear watchdog timer & its prescaler
                break;
            case SUBLW:
                cycles = 1;
                wRegister = ArithmeticLogicUnit.sub(ram, currentInstruction.getFK(), wRegister);
                break;
            case XORLW:
                wRegister = ArithmeticLogicUnit.xor(ram, wRegister, currentInstruction.getFK());
                break;
        }
        calculateRunTime(cycles);
        getNextInstruction();
    }

    private int getSwapNibbles(int f) {
        return ((f << 4 | f >> 4) & 0xFF);
    }

    private int getRotateLeftThroughCarry(int f) {
        f <<= 1;
        ram.setCarryFlag(f > 0xFF);
        return f & 0xFF;
    }

    private int getRotateRightTroughCarry(int f) {
        f >>= 1;
        if (ram.isCarryFlag()) {
            f |= 0b1000_0000;
        }
        ram.setCarryFlag(false);
        return f & 0xFF;
    }

    private void setResultInDestination(int d, int f, int value) {
        if (d == 0) {
            wRegister = value;
        } else {
            ram.setDataToAddress(f, value);
        }
    }

    private void skipNextInstruction() {
        currentInstructionInRegister = new InstructionLine();
        ram.setPCL(ram.getPCL() + 1);
        instructionHandler();
    }

    /**
     * Bit ’b’ in register ’f’ is cleared.
     *
     * @param b
     * @param f
     * @return
     */
    private int getBitClearF(int b, int f) {
        //TODO testen!
        return (f - (int) Math.pow(2, b));
    }

    /**
     * Bit ’b’ in register ’f’ is set.
     *
     * @param b
     * @param f
     * @return
     */
    private int getBitSetF(int b, int f) {
        //TODO testen!
        return (f + (int) Math.pow(2, b));
    }

    private boolean isBitInFActive(int b, int f) {
        f >>= (b - 1); //-1 weil Index start bei 1?
        return (f & 1) == 1;
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
