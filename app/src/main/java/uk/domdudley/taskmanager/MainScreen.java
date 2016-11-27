package uk.domdudley.taskmanager;

import java.util.List;

/**
 * Created by Dom on 16/08/2016.
 */
public interface MainScreen {
    void launchAddTaskDialog();
    void addTaskToDatabase(Task task);
}
