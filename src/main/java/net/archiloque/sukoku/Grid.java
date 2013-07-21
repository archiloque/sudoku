package net.archiloque.sukoku;

import java.util.LinkedList;
import java.util.Queue;

/**
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public class Grid implements Notifier {

    class SlotEventValueFound {

        private final Slot slot;

        SlotEventValueFound(Slot slot) {
            this.slot = slot;
        }

        void process() {
            System.out.println("Processing slot event value found " + slot);
            slot.getColumn().valueFound(slot);
            slot.getLine().valueFound(slot);
            slot.getSquare().valueFound(slot);
        }
    }

    private final Slot[] slots = new Slot[81];

    private final SlotsGroup[] columns = new SlotsGroup[9];
    private final SlotsGroup[] lines = new SlotsGroup[9];
    private final SlotsGroup[] squares = new SlotsGroup[9];

    private final Queue<SlotEventValueFound> events = new LinkedList<SlotEventValueFound>();

    public Grid() {
        for (int i = 0; i < 9; i++) {
            columns[i] = new SlotsGroup(SlotsGroupType.COLUMN, SlotValue.getByValue(i + 1));
            lines[i] = new SlotsGroup(SlotsGroupType.LINE, SlotValue.getByValue(i + 1));
            squares[i] = new SlotsGroup(SlotsGroupType.SQUARE, SlotValue.getByValue(i + 1));
        }
        for (int lineIndex = 0; lineIndex < 9; lineIndex++) {
            for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                Slot slot = new Slot(this, columnIndex, lineIndex);

                slots[lineIndex * 9 + columnIndex] = slot;

                columns[columnIndex].addSlot(slot);
                slot.setColumn(columns[columnIndex]);
                lines[lineIndex].addSlot(slot);
                slot.setLine(lines[lineIndex]);
                getSquare(columnIndex, lineIndex).addSlot(slot);
                slot.setSquare(getSquare(columnIndex, lineIndex));
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

    private SlotsGroup getSquare(int columnIndex, int lineIndex) {
        return squares[((lineIndex / 3) * 3) + columnIndex / 3];
    }

    @Override
    public void slotValueFound(Slot slot) {
        System.out.println("Notified that a slot value has been found " + slot);
        events.add(new SlotEventValueFound(slot));
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
                    if (slotPossibleValueColumnIndex == 2) {
                        result.append('|');
                    }

                }
                result.append("\n");
                if (slotPossibleValueLineIndex == 2) {
                    for (int i = 0; i < 36; i++) {
                        result.append("-");
                    }
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }

}
