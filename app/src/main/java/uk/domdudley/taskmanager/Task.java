package uk.domdudley.taskmanager;

/**
 * Created by Dom on 18/08/2016.
 */
public class Task {

    private int taskID;
    private String taskName;
    private Boolean isDone;

    public Task(String taskName){
        setTaskName(taskName);
    }

    public Task(){

    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
