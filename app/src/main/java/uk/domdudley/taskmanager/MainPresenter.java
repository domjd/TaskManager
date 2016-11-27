package uk.domdudley.taskmanager;

/**
 * Created by Dom on 16/08/2016.
 */
public class MainPresenter {

    private MainScreen mainScreen;

    public MainPresenter(MainScreen mainScreen){
        this.mainScreen = mainScreen;
    }

    public void OnAddTaskButtonClick(){
        mainScreen.launchAddTaskDialog();
    }

    public void processTask(Task task){
        mainScreen.addTaskToDatabase(task);
    }

}
