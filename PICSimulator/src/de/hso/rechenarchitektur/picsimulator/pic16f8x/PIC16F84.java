package de.hso.rechenarchitektur.picsimulator.pic16f8x;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.*;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionType;

import java.util.List;

public class PIC16F84 {

    //in micro sec
    private float runTime = 0;
    //in kHz
    private double quarzSpeed = 1.0;

    private Stack stack;
    private final ProgramMemory programMemory;
    private final RandomAccessMemory ram;
    private InstructionLine currentInstructionInRegister;

    private int wRegister;

    private final WatchDog watchDog;
    //RB0 Interrupt PortB0
    private boolean wasRB0 = false;

    public PIC16F84(List<InstructionLine> instructionLineList) {
        programMemory = new ProgramMemory(instructionLineList);
        ram = new RandomAccessMemory();
        watchDog = new WatchDog();
        reset();

    }

    private void reset() {
        watchDog.resetWatchDogTimer();
        currentInstructionInRegister = new InstructionLine(0, 0, new Instruction(InstructionType.NOP));
        ram.setPCL(0);
        //was sleeping
        if (ram.isTimeOutFlag() && !ram.isPowerDownFlag()) {
            if (ram.isGIE()) {
                // the device executes the instruction
                //after the SLEEP instruction and then branches to the
                //interrupt address (0004h).
                checkForInterrupts();
            } else {
                //no interrupt -> go to normal
                ram.setPowerDownFlag(true);
            }
        } else {
            //normal reset
            currentInstructionInRegister = new InstructionLine();
            //ram = new RandomAccessMemory();
            stack = new Stack();
        }
        //Reset Intcon GIE by Reset
        ram.setGIE(false);
    }

    private boolean checkForInterrupts() {
        boolean wasInterrupt = false;
        if (ram.isGIE()) {
            //Timer Interrupt
            if (ram.isT0IE() && ram.isT0IF()) {
                //Timer Interrupt
                stack.push(ram.getPCL());
                ram.setGIE(false);
                ram.setPCL(4);
                wasInterrupt = true;
            }
            //RB0 Interrupt
            if (ram.isINTF() && ram.isINTE()) {
                //Timer Interrupt
                stack.push(ram.getPCL());
                ram.setGIE(false);
                ram.setPCL(4);
                wasInterrupt = true;
            }
            //RB Interrupt
            if (ram.isRBIE() && ram.isRBIF()) {
                //Timer Interrupt
                stack.push(ram.getPCL());
                ram.setGIE(false);
                ram.setPCL(4);
                wasInterrupt = true;
            }
        }
        return wasInterrupt;
    }

    private void getNextInstruction() {
        checkForInterrupts();
        InstructionLine nextInstruction = programMemory.getInstructionAt(ram.getPCL());
        //checks if there is a instruction left
        if (nextInstruction != null) {
            currentInstructionInRegister = programMemory.getInstructionAt(ram.getPCL());
            ram.incrementPCL();
        }
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
            case DECF: //Fallthrough
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
                ram.setZeroFlag(valueOfAddress == 0);
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
                //is watchDog enabled
                if (watchDog.isWDT()) {
                    watchDog.resetWatchDogTimer();
                } else {
                    reset();
                }
                break;
            case CLRWDT:
                watchDog.resetWatchDogTimer();
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
                ram.setGIE(true);
                break;
            case RETLW: //Fallthrough
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

        //RunTime
        runTime += calculateRunTimePerCycle(cycles);

        handleTimer(cycles);

        handleWatchDog(cycles);


        getNextInstruction();
    }

    private void handleWatchDog(int cycles) {
        //Watchdog
        if (watchDog.isWDT()) {
            watchDog.addWatchDogTimer(calculateRunTimePerCycle(cycles));
            watchDog.setWatchDogTimerEnd(18 * PreScaler.getWatchDogPreScaler(ram.getOption()) * 1000);
            if (watchDog.isWatchDogOver()) {
                reset();
            }
        }
    }

    private void handleTimer(int cycles) {
        //Timer
        float signal = 0;
        //Timer from cycles
        if (!ram.isTCs()) {
            signal = cycles;
            if (!ram.isPSA()) {
                //signal through prescaler
                signal = (float) cycles / PreScaler.getTimerPreScaler(ram.getOption());
            }
        }
        addTimer(signal);
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

    public void skipNextInstruction() {
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

    private double calculateRunTimePerCycle(int cycles) {
        //4Mhz-> 1 microSec pro cycle
        return (4000 * cycles / quarzSpeed);
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


    public void setQuarzSpeed(double quarzSpeed) {
        this.quarzSpeed = quarzSpeed;
    }


    /**
     * Debug Test fuer Instruktionen lesen und makieren in der GUI
     */
    public void cycle() {
        if (ram.isTimeOutFlag() && !ram.isPowerDownFlag()) {
            //Sleep
            handleWatchDog(1);
            ram.setPowerDownFlag(checkForInterrupts());
        } else {
            //normal instruction handling
            instructionHandler();
        }
    }

    public String runTimeToString() {
        return String.format("%.3f", runTime) + "\u00B5s";
    }

    public InstructionLine getCurrentInstructionInRegister() {
        return this.currentInstructionInRegister;
    }

    public void setWDT(boolean value) {
        watchDog.setWDT(value);
    }

    private void addTimer(float signal) {
        ram.addTimer(signal);
        //reset timer value if timer overflowed
        if (ram.isT0IF()) {
            ram.manipulateTMR0(0);
        }
    }

    public void switchRA4T0CKI(boolean selected) {
        //Timer gets signals from RA4
        float signal = 0;
        if (ram.isTCs()) {
            //high or low flank
            if (ram.isTSe()) {
                //high to low
                if (ram.getTimer().wasLastRA4_T0CKIFlankUp() && !selected) {
                    signal = 1;
                }
            } else {
                //low to high
                if (!ram.getTimer().wasLastRA4_T0CKIFlankUp() && selected) {
                    signal = 1;
                }
            }
        }
        if (!ram.isPSA()) {
            signal = signal / PreScaler.getTimerPreScaler(ram.getOption());
        }
        addTimer(signal);
        ram.getTimer().setWasLastRA4_T0CKIFlankUp(selected);
    }


    public WatchDog getWatchDog() {
        return this.watchDog;
    }

    public void switchRB0(boolean selected) {
        if (ram.isINTE()) {
            if (ram.isIEg()) {
                //rising
                if (!wasRB0 && selected) {
                    ram.setINTF(true);
                }
            } else {
                //falling
                if (wasRB0 && !selected) {
                    ram.setINTF(true);
                }
            }
        }
        wasRB0 = selected;
    }

    public void switchRB4_7(int index) {
        //checks if RBIE & Port at Index is not output set by tris
        if (ram.isRBIE() && (ram.getTrisB() >> index & 1) == 1) {
            ram.setRBIF(true);
        }
    }
}
