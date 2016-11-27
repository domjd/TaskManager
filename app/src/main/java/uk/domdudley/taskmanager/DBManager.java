package uk.domdudley.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dom on 16/08/2016.
 */
public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasks.db";

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASKNAME = "taskname";
    public static final String COLUMN_TASKISDONE = "isdone";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TASKS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_TASKNAME + " TEXT ," +
                COLUMN_TASKISDONE + " INTEGER " +
                ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    void addTask(Task t){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKNAME, t.getTaskName());
        values.put(COLUMN_TASKISDONE, t.getDone());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    void removeTask(Task t){
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_ID + "= ?";
        String[] selectionArgs = { String.valueOf(t.getTaskID()) };

        db.delete(TABLE_TASKS, selection, selectionArgs);
        db.close();
    }

    public List<Task> returnTaskList(){
        SQLiteDatabase db = getReadableDatabase();
        List<Task> t = new ArrayList<>();
        Cursor c = db.query(TABLE_TASKS,
                new String[]{COLUMN_ID, COLUMN_TASKNAME, COLUMN_TASKISDONE},
                null, null, null, null, null);

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String taskname = c.getString(c.getColumnIndex("taskname"));
                    int taskisdone = c.getInt(c.getColumnIndex("isdone"));
                    Boolean bTaskIsDone = (taskisdone != 0);
                    Task task = new Task();
                    task.setTaskID(c.getInt(c.getColumnIndex("_id")));
                    task.setTaskName(taskname);
                    task.setDone(bTaskIsDone);
                    t.add(task);
                }while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return t;
    }

    public void changeTaskStatus(Task t){
        Log.v("Before: ",String.valueOf(t.getDone()));
        t.setDone(!t.getDone());

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKNAME, t.getTaskName());
        values.put(COLUMN_TASKISDONE, t.getDone());

        String selection = COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(t.getTaskID())};

        SQLiteDatabase db = getReadableDatabase();

        db.update(TABLE_TASKS,
                values,
                selection,
                selectionArgs);
        db.close();
        Log.v("After: ",String.valueOf(t.getDone()));
    }
}
