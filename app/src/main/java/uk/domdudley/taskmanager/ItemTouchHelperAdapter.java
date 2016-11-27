package uk.domdudley.taskmanager;

/**
 * Created by Dom on 22/08/2016.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
