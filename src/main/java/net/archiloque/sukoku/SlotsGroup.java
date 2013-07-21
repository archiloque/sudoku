package net.archiloque.sukoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        checkForUpdates();
    }

    public void valueNotFound(Slot slot) {
        checkForUpdates();
    }

    private void checkForUpdates() {
        {
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

            // check it some values are only possible on one slot
            for (Map.Entry<SlotValue, List<Slot>> slotValueIntegerEntry : possibleValues.entrySet()) {
                if (slotValueIntegerEntry.getValue().size() == 1) {
                    SlotValue slotValue = slotValueIntegerEntry.getKey();
                    Slot slot = slotValueIntegerEntry.getValue().get(0);
                    System.out.println("Group " + this + " has a single slot where the value " + slotValue + " is the only possible " + slot);
                    slot.setValue(slotValue);
                }
            }
        }

        {
            // in the case were X slots have the same X possible value it means the values cannot be used by other slots
            // like with 2 slots with 4 and 6 we are sure these values are only on these slots
            // so we can't have 4 or 6 on other slots

            // simple case: we create a map where the key is the Set of values and the values are the list of Slots
            Map<Set<SlotValue>, List<Slot>> duplicatedSlotSet = new HashMap<Set<SlotValue>, List<Slot>>(9);
            for (Slot slot : slots) {
                if (slot.getValue() == null) {
                    Set<SlotValue> slotPossibleValues = slot.getPossibleValues();
                    if (!duplicatedSlotSet.containsKey(slotPossibleValues)) {
                        duplicatedSlotSet.put(slotPossibleValues, new ArrayList<Slot>(9));
                    }
                    duplicatedSlotSet.get(slotPossibleValues).add(slot);
                }
            }
            // then we iterate on the map to find cases were the size of the slot list of the same that the size of slot values
            for (Map.Entry<Set<SlotValue>, List<Slot>> setListEntry : duplicatedSlotSet.entrySet()) {
                if ((setListEntry.getValue().size() > 1) && (setListEntry.getKey().size() == setListEntry.getValue().size())) {
                    // in this case we can exclude the values from the other slots
                    for (Slot slot : slots) {
                        if ((slot.getValue() == null) && (!setListEntry.getValue().contains(slot))) {
                            for (SlotValue slotValue : setListEntry.getKey()) {
                                if (slot.getPossibleValues().contains(slotValue)) {
                                    System.out.println("Group " + this + " exclude " + slotValue + " from " + slot + " because it is used by " + Arrays.toString(setListEntry.getValue().toArray()));
                                    slot.setNotValue(slotValue);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String toString() {
        return slotsGroupType + " " + index;
    }

}
