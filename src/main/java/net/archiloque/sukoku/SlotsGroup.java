package net.archiloque.sukoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (int i = 0; i < 9; i++) {
            if (slots[i] == null) {
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
            if (slot != valuedSlot) {
                slot.setNotValue(valuedSlot.getValue());
            }
        }
        // for each untaken value we check if only one slot can take it
        Map<SlotValue, List<Slot>> possibleValues = new HashMap<SlotValue, List<Slot>>();
        for (SlotValue slotValue : SlotValue.values()) {
            possibleValues.put(slotValue, new ArrayList<Slot>());
        }
        for (Slot slot : slots) {
            if (slot.getValue() != null) {
                possibleValues.remove(slot.getValue());
            } else {
                for (SlotValue slotValue : slot.getPossibleValues()) {
                    if (possibleValues.containsKey(slotValue)) {
                        possibleValues.get(slotValue).add(slot);
                    }
                }
            }
        }

        for (Map.Entry<SlotValue, List<Slot>> slotValueIntegerEntry : possibleValues.entrySet()) {
            if (slotValueIntegerEntry.getValue().size() == 1) {
                SlotValue slotValue = slotValueIntegerEntry.getKey();
                Slot slot = slotValueIntegerEntry.getValue().get(0);
                System.out.println("Group " + this + " has a single slot where the value " + slotValue + " is the only possible " + slot);
                slot.setValue(slotValue);
            }
        }
    }

    public String toString() {
        return slotsGroupType + " " + index;
    }

}
