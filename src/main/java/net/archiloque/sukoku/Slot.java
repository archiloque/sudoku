package net.archiloque.sukoku;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

/**
 * Represents a slot
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public class Slot {

    private final Notifier notifier;

    private SlotValue value = null;

    private final Collection<SlotValue> possibleValues;

    private final int columnIndex;

    private final int lineIndex;

    private SlotsGroup column;

    private SlotsGroup line;

    private SlotsGroup square;

    public Slot(Notifier notifier, int columnIndex, int lineIndex) {
        this.notifier = notifier;
        this.columnIndex = columnIndex;
        this.lineIndex = lineIndex;
        possibleValues = EnumSet.allOf(SlotValue.class);
    }

    public SlotValue getValue() {
        return value;
    }

    public Collection<SlotValue> getPossibleValues(){
        return possibleValues;
    }

    public boolean isPossibleValue(SlotValue slotValue) {
        return possibleValues.contains(slotValue);
    }

    public void setValue(SlotValue value) {
        this.value = value;
        notifier.slotValueFound(this);
    }

    public void setSquare(SlotsGroup square) {
        this.square = square;
    }

    public void setLine(SlotsGroup line) {
        this.line = line;
    }

    public void setColumn(SlotsGroup column) {
        this.column = column;
    }

    public SlotsGroup getColumn() {
        return column;
    }

    public SlotsGroup getLine() {
        return line;
    }

    public SlotsGroup getSquare() {
        return square;
    }

    public void setNotValue(SlotValue slotValue) {
        if (value == null) {
            possibleValues.remove(slotValue);
            if (possibleValues.size() == 1) {
                value = (SlotValue) possibleValues.toArray()[0];
                System.out.println("A slot had only one single value " + this);
                notifier.slotValueFound(this);
            }
        }
    }

    public String toString() {
        return "column " + columnIndex + " line " + lineIndex + ((value != null) ? (" value " + value) : (" possible values " + Arrays.toString(possibleValues.toArray())));
    }
}
