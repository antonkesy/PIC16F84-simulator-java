package de.hso.rechenarchitektur.picsimulator.pic16f8x.elements;

public final class ArithmeticLogicUnit {

    public enum AluOperations {
        ADD, SUB, AND, OR, XOR, NOT
    }

    public static int add(RandomAccessMemory ram, int wRegisterValue, int otherValue) {
        return operation(AluOperations.ADD, ram, wRegisterValue, otherValue);
    }

    public static int sub(RandomAccessMemory ram, int wRegisterValue, int otherValue) {
        return operation(AluOperations.SUB, ram, wRegisterValue, otherValue);
    }

    public static int and(RandomAccessMemory ram, int wRegisterValue, int otherValue) {
        return operation(AluOperations.AND, ram, wRegisterValue, otherValue);
    }

    public static int or(RandomAccessMemory ram, int wRegisterValue, int otherValue) {
        return operation(AluOperations.OR, ram, wRegisterValue, otherValue);
    }

    public static int xor(RandomAccessMemory ram, int wRegisterValue, int otherValue) {
        return operation(AluOperations.XOR, ram, wRegisterValue, otherValue);
    }

    public static int not(RandomAccessMemory ram, int wRegisterValue, int otherValue) {
        return operation(AluOperations.NOT, ram, wRegisterValue, otherValue);
    }

    public static int operation(AluOperations operation, RandomAccessMemory ram, int firstValue, int secondValue) {
        int result = firstValue;

        switch (operation) {
            case SUB:
                //Zweierkompliment und dann fallthrough zu ADD
                secondValue = getTwoCompliment(secondValue);
            case ADD:
                //Reset affected flags
                ram.setCarryFlag(false);
                ram.setDigitCarryFlag(false);
                //
                result += secondValue;
                //CarryFlag
                ram.setCarryFlag(isCarry(firstValue, secondValue));
                //DigitCarryFlag
                ram.setDigitCarryFlag(isDigitCarry(firstValue, secondValue));
                //Result auf 8Bit maskieren
                break;
            case AND:
                result &= secondValue;
                break;
            case OR:
                result |= secondValue;
                break;
            case XOR:
                result ^= secondValue;
                break;
            case NOT:
                //never used
                break;
        }

        result &= 0xFF;
        //Zero Flag
        ram.setZeroFlag(result == 0);

        return result;
    }

    public static boolean isDigitCarry(int firstStart, int secondStart) {
        int carryCheck = (firstStart & 0b01111) + (secondStart & 0b01111);
        carryCheck >>= 4;
        return carryCheck != 0;
    }

    public static boolean isCarry(int firstStart, int secondStart) {
        int carryCheck = (firstStart & 0xFF) + (secondStart & 0xFF);
        carryCheck >>= 8;
        return carryCheck != 0;
    }

    /**
     * 8Bit 2er Kompliment
     *
     * @param value
     * @return
     */
    public static int getTwoCompliment(int value) {
        value = getCompliment(value);
        value += 1;
        value &= 0xFF; //just to be sure
        return value;
    }

    /**
     * Returns 8-Bit compliment of f
     *
     * @param f
     * @return
     */
    public static int getCompliment(int f) {
        return ~f & 0xFF;
    }
}
