package net.archiloque.sukoku;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Represnts the sudoku grid, most code is to setup the slots, an event queue, and a big toString
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public class Grid implements Notifier {

    abstract class SlotEvent {

        protected final Slot slot;

        SlotEvent(Slot slot) {
            this.slot = slot;
        }

        abstract void process();

    }

    class SlotEventValueFound extends SlotEvent {

        SlotEventValueFound(Slot slot) {
            super(slot);
        }

        @Override
        void process() {
            System.out.println("Processing slot event value found " + slot);
            slot.getColumn().valueFound(slot);
            slot.getLine().valueFound(slot);
            slot.getSquare().valueFound(slot);
        }
    }

    class SlotEventNotValueFound extends SlotEvent {

        SlotEventNotValueFound(Slot slot) {
            super(slot);
        }

        @Override
        void process() {
            System.out.println("Processing slot event not value found " + slot);
            slot.getColumn().valueNotFound(slot);
            slot.getLine().valueNotFound(slot);
            slot.getSquare().valueNotFound(slot);
        }
    }

    private final Slot[] slots = new Slot[81];

    private final Queue<SlotEvent> events = new LinkedList<SlotEvent>();

    public Grid() {
        SlotsGroup[] columns = new SlotsGroup[9];
        SlotsGroup[] squares = new SlotsGroup[9];
        SlotsGroup[] lines = new SlotsGroup[9];
        for (int i = 0; i < 9; i++) {
            columns[i] = new SlotsGroup(SlotsGroupType.COLUMN, SlotValue.getByValue(i + 1));
            lines[i] = new SlotsGroup(SlotsGroupType.LINE, SlotValue.getByValue(i + 1));
            squares[i] = new SlotsGroup(SlotsGroupType.SQUARE, SlotValue.getByValue(i + 1));
        }
        for (int lineIndex = 0; lineIndex < 9; lineIndex++) {
            for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                Slot slot = new Slot(this, columnIndex, lineIndex);

                slots[lineIndex * 9 + columnIndex] = slot;

                SlotsGroup column = columns[columnIndex];
                column.addSlot(slot);
                slot.setColumn(column);
                SlotsGroup line = lines[lineIndex];
                line.addSlot(slot);
                slot.setLine(line);
                SlotsGroup square = squares[((lineIndex / 3) * 3) + columnIndex / 3];
                square.addSlot(slot);
                slot.setSquare(square);
            }
        }
    }

    public Grid(String description) {
        this();
        if (description.length() != 81) {
            throw new IllegalArgumentException("Length should be 81 and not " + description.length());
        }
        for (int lineIndex = 0; lineIndex < 9; lineIndex++) {
            for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                char slot = description.charAt(lineIndex * 9 + columnIndex);
                if (slot != ' ') {
                    SlotValue slotValue = SlotValue.getByValue(Integer.parseInt(String.valueOf(slot)));
                    slots[lineIndex * 9 + columnIndex].setValue(slotValue);
                }
            }
        }
        processEvents();
    }

    @Override
    public void slotValueFound(Slot slot) {
        System.out.println("Notified that a slot value has been found " + slot);
        events.add(new SlotEventValueFound(slot));
    }

    @Override
    public void slotNotValueFound(Slot slot) {
        System.out.println("Notified that a slot value is no more possible " + slot);
        events.add(new SlotEventNotValueFound(slot));
    }

    private void processEvents() {
        while (!events.isEmpty()) {
            events.remove().process();
        }
    }

    private boolean isGridCompletlyCalculated() {
        for (Slot slot : slots) {
            if (slot.getValue() == null) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (isGridCompletlyCalculated()) {
            for (int slotLineIndex = 0; slotLineIndex < 9; slotLineIndex++) {
                for (int slotColumnIndex = 0; slotColumnIndex < 9; slotColumnIndex++) {
                    Slot slot = slots[slotLineIndex * 9 + slotColumnIndex];
                    result.append(slot.getValue());
                }
                result.append("\n");
            }
        } else {

            for (int absoluteLineIndex = 0; absoluteLineIndex < 27; absoluteLineIndex++) {
                int slotLineIndex = absoluteLineIndex / 3;
                int slotPossibleValueLineIndex = absoluteLineIndex % 3;
                for (int absoluteColumnIndex = 0; absoluteColumnIndex < 27; absoluteColumnIndex++) {
                    int slotColumnIndex = absoluteColumnIndex / 3;
                    int slotPossibleValueColumnIndex = absoluteColumnIndex % 3;

                    SlotValue slotValue = SlotValue.getByValue(3 * slotPossibleValueLineIndex + slotPossibleValueColumnIndex + 1);

                    Slot slot = slots[slotLineIndex * 9 + slotColumnIndex];
                    if (slot.getValue() == null) {
                        if (slot.isPossibleValue(slotValue)) {
                            result.append(slotValue);
                        } else {
                            result.append(' ');
                        }
                    } else {
                        if ((slotPossibleValueLineIndex == 1) && (slotPossibleValueColumnIndex == 1)) {
                            result.append(slot.getValue());
                        } else {
                            result.append('*');
                        }
                    }
                    if ((slotPossibleValueColumnIndex == 2) && (absoluteColumnIndex != 26)) {
                        if (slotColumnIndex % 3 == 2) {
                            result.append('‖');
                        } else {
                            result.append('|');
                        }
                    }

                }
                result.append("\n");
                if ((slotPossibleValueLineIndex == 2) && (absoluteLineIndex != 26)) {
                    for (int i = 0; i < 36; i++) {
                        if (slotLineIndex % 3 == 2) {
                            result.append("=");
                        } else {
                            result.append("-");
                        }
                    }
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }

}
