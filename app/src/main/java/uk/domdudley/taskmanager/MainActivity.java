package uk.domdudley.taskmanager;

import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainScreen {

    private RecyclerView taskRecycler;
    private RecyclerView.LayoutManager taskLayout;
    private TaskAdapter taskListAdapter;

    private MainPresenter presenter;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);

        taskRecycler = (RecyclerView)findViewById(R.id.my_recycler_view);
        taskLayout = new LinearLayoutManager(this);
        taskRecycler.setLayoutManager(taskLayout);

        taskListAdapter = new TaskAdapter(dbManager.returnTaskList());
        taskRecycler.setAdapter(taskListAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(taskListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(taskRecycler);

        presenter = new MainPresenter(this);

        Log.v("Array Size: ", Integer.toString(taskListAdapter.getItemCount()));
    }

    @Override
    public void launchAddTaskDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        final EditText taskText = (EditText)dialogView.findViewById(R.id.task_name);
        taskText.setHint("Task Name");
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new task")
                .setView(dialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String taskString = String.valueOf(taskText.getText());
                        Task t = new Task(taskString);
                        presenter.processTask(t);
                        taskListAdapter.swapDataset(dbManager.returnTaskList());
                        Log.v("Array Size: ", Integer.toString(taskListAdapter.getItemCount()));
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public void addTaskToDatabase(Task task) {
        DBManager dbManager = new DBManager(this);
        dbManager.addTask(task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){
            case R.id.action_newTask:
                presenter.OnAddTaskButtonClick();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
