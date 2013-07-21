package net.archiloque.sukoku;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a slot
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public class Slot {

    private SlotValue value = null;

    private final List<SlotValue> possibleValues;

    private final int columnIndex;

    private final int lineIndex;

    public Slot(int columnIndex, int lineIndex) {
        this.columnIndex = columnIndex;
        this.lineIndex = lineIndex;
        possibleValues = Arrays.asList(SlotValue.values());
    }

    public SlotValue getValue() {
        return value;
    }

    public boolean isPossibleValue(SlotValue slotValue) {
        return possibleValues.contains(slotValue);
    }
}
