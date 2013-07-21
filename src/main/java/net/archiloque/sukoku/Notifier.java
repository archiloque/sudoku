package net.archiloque.sukoku;

/**
 * To be notified when something happen so its is dispatched.
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public interface Notifier {

    void slotValueFound(Slot slot);

    void slotNotValueFound(Slot slot);
}
