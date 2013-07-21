package net.archiloque.sukoku;

/**
 * A slot group: can be a column, a line, or a square
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public class SlotsGroup {

    private final Slot[] slots = new Slot[9];

    private final SlotsGroupType slotsGroupType;

    private final SlotValue index;

    public SlotsGroup(SlotsGroupType slotsGroupType, SlotValue index) {
        this.slotsGroupType = slotsGroupType;
        this.index = index;
    }

    public void addSlot(Slot slot) {
        for(int i = 0; i < 9; i++) {
            if(slots[i] == null) {
                slots[i] = slot;
                return;
            }
        }
    }

    /**
     * Callback when a value of a slot has been found.
     */
    public void valueFound(Slot valuedSlot) {
        for (Slot slot : slots) {
            if(slot != valuedSlot) {
                slot.setNotValue(valuedSlot.getValue());
            }
        }
    }

}
