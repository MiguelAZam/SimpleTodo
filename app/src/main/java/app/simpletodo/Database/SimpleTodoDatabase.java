package app.simpletodo.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import app.simpletodo.Models.Todo;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class SimpleTodoDatabase extends RoomDatabase {

    public abstract TodoDao TodoDao();
    private static SimpleTodoDatabase dbInstance;
    private static String DATABASE_NAME = "SimpleTodo";

    public static SimpleTodoDatabase getInstance(final Context c){
        if(dbInstance == null){
            synchronized (SimpleTodoDatabase.class){
                dbInstance = Room.databaseBuilder(c.getApplicationContext(),
                        SimpleTodoDatabase.class, SimpleTodoDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return dbInstance;
    }

}
