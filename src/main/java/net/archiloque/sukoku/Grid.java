package net.archiloque.sukoku;

/**
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public class Grid {

    private final Slot[] slots = new Slot[81];

    private final SlotsGroup[] columns = new SlotsGroup[9];
    private final SlotsGroup[] lines = new SlotsGroup[9];
    private final SlotsGroup[] squares = new SlotsGroup[9];

    public Grid() {
        for (int i = 0; i < 9; i++) {
            columns[i] = new SlotsGroup(SlotsGroupType.COLUMN, SlotValue.getByValue(i + 1));
            lines[i] = new SlotsGroup(SlotsGroupType.LINE, SlotValue.getByValue(i + 1));
            squares[i] = new SlotsGroup(SlotsGroupType.SQUARE, SlotValue.getByValue(i + 1));
        }
        for (int lineIndex = 0; lineIndex < 9; lineIndex++) {
            for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                Slot slot = new Slot(columnIndex, lineIndex);

                slots[lineIndex * 9 + columnIndex] = slot;

                columns[columnIndex].addSlot(slot);
                lines[lineIndex].addSlot(slot);
                squares[((lineIndex / 3) * 3) + columnIndex / 3].addSlot(slot);
            }
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
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
                    if ((slotColumnIndex == 1) && (slotLineIndex == 1)) {
                        result.append(slot.getValue());
                    } else {
                        result.append(' ');
                    }
                }

            }
            result.append("\n");
        }
        return result.toString();
    }
}
