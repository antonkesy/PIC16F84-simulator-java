package de.hso.rechenarchitektur.picsimulator.pic16f8x;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.*;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionType;

import java.util.List;

public class PIC16F8X {

    //in micro sec
    private float runTime = 0;
    //in kHz
    private long quarzSpeed = 32;

    private Stack stack;
    private final ProgramMemory programMemory;
    private RandomAccessMemory ram;
    private InstructionLine currentInstructionInRegister;

    private int wRegister;

    private boolean isWDT;
    //in micro sec
    private float watchDogTimer;

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
        ram.incrementPCL();
    }

    private void instructionHandler() {
        int result; //optional Variable fuer Zwischenergebnisse
        int cycles = 1;
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
                result = ArithmeticLogicUnit.getCompliment(ram.getDataFromAddress(currentInstruction.getFK()));
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                break;
            case DECF: //Fallthroug
            case INCF:
                result = ram.getDataFromAddress(currentInstruction.getFK());
                result += currentInstruction.getType() == InstructionType.INCF ? 1 : -1;
                result = setValueTo8BitAndSetZeroFlag(result);
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                break;
            case DECFSZ:
            case INCFSZ:
                result = ram.getDataFromAddress(currentInstruction.getFK());
                result += currentInstruction.getType() == InstructionType.INCFSZ ? 1 : -1;
                result = setValueTo8BitAndSetZeroFlag(result);
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                if (result == 0) {
                    skipNextInstruction();
                }
                break;
            case IORWF:
                result = ArithmeticLogicUnit.or(ram, wRegister, ram.getDataFromAddress(currentInstruction.getFK()));
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
                break;
            case MOVF:
                int valueOfAddress = ram.getDataFromAddress(currentInstruction.getFK());
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), valueOfAddress);
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
                result = ArithmeticLogicUnit.sub(ram, ram.getDataFromAddress(currentInstruction.getFK()), wRegister);
                setResultInDestination(currentInstruction.getBD(), currentInstruction.getFK(), result);
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
                        getBitClearF(
                                currentInstruction.getBD(),
                                ram.getDataFromAddress(currentInstruction.getFK())
                        )
                );
                break;
            case BSF:
                ram.setDataToAddress(
                        currentInstruction.getFK(),
                        getBitSetF(
                                currentInstruction.getBD(),
                                ram.getDataFromAddress(currentInstruction.getFK())
                        )
                );
                break;
            case BTFSC:
                if (!isBitFActive(currentInstruction.getBD(), ram.getDataFromAddress(currentInstruction.getFK()))) {
                    skipNextInstruction();
                }
                break;
            case BTFSS:
                if (isBitFActive(currentInstruction.getBD(), ram.getDataFromAddress(currentInstruction.getFK()))) {
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
                stack.push(getRam().getPCL());
                getRam().manipulatePCL(ram.getJumpAddress(currentInstruction.getFK()));
                break;
            case SLEEP:
                ram.setPowerDownFlag(false);
                ram.setTimeOutFlag(true);
                //todo clear watchdog timer & its prescaler
                break;
            case CLRWDT:
                //todo clear watchdog timer & its prescaler
                ram.setPowerDownFlag(true);
                ram.setTimeOutFlag(true);
                break;
            case GOTO:
                cycles = 2;
                ram.setPCL(ram.getJumpAddress(currentInstruction.getFK()));
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
            case SUBLW:
                cycles = 1;
                wRegister = ArithmeticLogicUnit.sub(ram, currentInstruction.getFK(), wRegister);
                break;
            case XORLW:
                wRegister = ArithmeticLogicUnit.xor(ram, wRegister, currentInstruction.getFK());
                break;
        }

        float timeForThisCycle = calculateRunTimePerCycle(cycles);
        runTime += timeForThisCycle;
        //todo
        //TODO prescaler
        //Timer
        int signal = 0;

        if (!ram.isTCs()) {
            //Timer
            signal = cycles;
            if (ram.isPSA()) {
                //signal through prescaler
                signal = cycles / PreScaler.getTimerPreScaler(ram.getOption());
            }
            //else raw
        } else {
            //RA4
            //signal == RA4
            //TODO
            if (!ram.isPSA()) {
                //signal through prescaler
                signal = cycles / PreScaler.getTimerPreScaler(ram.getOption());
            }
            //else raw
        }
        //TODO
        System.out.println("t" + signal);
        ram.incrementTMR0By(signal);

        //TODO watchDogTimer
        //Watchdog
        if (isWDT) {
            float watchDogCycleTime = timeForThisCycle;
            if (ram.isPSA()) {
                watchDogCycleTime /= PreScaler.getWatchDogPreScaler(ram.getOption());
            }
            watchDogTimer += watchDogCycleTime;
            if (watchDogTimer >= PreScaler.getWatchDogPreScaler(ram.getOption()) * 1000) {
                reset();
            }
        }

        getNextInstruction();
    }

    private int getSwapNibbles(int f) {
        return ((f << 4 | f >> 4) & 0xFF);
    }

    private int getRotateLeftThroughCarry(int f) {
        boolean isCarryFlagAfter = isBitFActive(7, f);
        f <<= 1;
        if (ram.isCarryFlag()) {
            f ^= 1;
        }
        ram.setCarryFlag(isCarryFlagAfter);
        return f & 0xFF;
    }

    private int getRotateRightTroughCarry(int f) {
        boolean isCarryFlagAfter = isBitFActive(0, f);
        f >>= 1;
        if (ram.isCarryFlag()) {
            f |= 0b1000_0000;
        }
        ram.setCarryFlag(isCarryFlagAfter);
        return f & 0xFF;
    }

    private int setValueTo8BitAndSetZeroFlag(int value) {
        value &= 0xFF;
        ram.setZeroFlag(value == 0);
        return value;
    }

    private void setResultInDestination(int d, int f, int value) {
        if (d == 0) {
            wRegister = value;
        } else {
            ram.setDataToAddress(f, value);
        }
    }

    private void skipNextInstruction() {
        getNextInstruction();
    }

    /**
     * Bit ’b’ in register ’f’ is cleared.
     * <p>
     * b start at 1 or 0?
     *
     * @param b
     * @param f
     * @return
     */
    private int getBitClearF(int b, int f) {
        //if bit already clear -> return current f -> else return f - 2^b
        return (!isBitFActive(b, f)) ? f : (f - (int) Math.pow(2, b));

    }

    /**
     * Bit ’b’ in register ’f’ is set.
     *
     * @param b
     * @param f
     * @return
     */
    private int getBitSetF(int b, int f) {
        //if bit already active -> return current f -> else return f + 2^b
        return (isBitFActive(b, f)) ? f : (f + (int) Math.pow(2, b));
    }

    private boolean isBitFActive(int b, int f) {
        return (f >> (b) & 1) == 1;
    }

    private float calculateRunTimePerCycle(int cycles) {
        return (cycles * getTimePerCycle());
    }

    private float getTimePerCycle() {
        //bei 1MHz => 4micoSecs
        //runtime = runtimeCount * 1/(currFrequency * 1000) * 4;
        //runtime in MS
        //freq in kHz
        return (float) quarzSpeed / 4000;
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
    public int step() {
        instructionHandler();
        return currentInstructionInRegister.getPositionLineInFile();
    }

    public String runTimeToString() {
        return String.format("%.3f", runTime) + "\u00B5s";
    }

    public InstructionLine getCurrentInstructionInRegister() {
        return this.currentInstructionInRegister;
    }

    public void setWDT(boolean value) {
        this.isWDT = value;
    }

    public void switchWDT() {
        setWDT(!isWDT);
    }

    public String getWatchDogTimerString() {
        return String.format("%.3f", watchDogTimer) + "\u00B5s";
    }
}
