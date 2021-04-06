package de.hso.rechenarchitektur.picsimulator.pic16f8x;

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

    public static int operation(AluOperations operation, RandomAccessMemory ram, int wRegisterValue, int otherValue) {
        //todo check for flags
        int result = wRegisterValue;

        //Zero flag always reset
        ram.setZeroFlag(false);

        switch (operation) {
            case ADD:
                //Reset affected flags
                ram.setCarryFlag(false);
                ram.setDigitCarryFlag(false);
                //
                //TODO set flags
                result += otherValue;
                break;
            case SUB:
                //Reset affected flags
                ram.setCarryFlag(false);
                ram.setDigitCarryFlag(false);
                //
                //Maskenfehler des PIC ->  Ist der Subtrahend kleiner oder gleich dem Minuend, wird das Carryflag gesetzt
                if (otherValue <= wRegisterValue) {
                    ram.setCarryFlag(true);
                }
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
}
