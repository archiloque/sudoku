package net.archiloque.sukoku;

/**
 * Values available in a Slot.
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public enum SlotValue {


    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

    private static final SlotValue[] LOOKUP_TABLE = new SlotValue[10];

    static {
        for(SlotValue slotValue : values()){
            LOOKUP_TABLE[slotValue.value] = slotValue;
        }
    }

    private final int value;

    SlotValue(int value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public static SlotValue getByValue(int value) {
        if(value == 0) {
            throw new RuntimeException("0 is not a acceptable value, values are from 1 to 9");
        }
        return LOOKUP_TABLE[value];
    }
}
