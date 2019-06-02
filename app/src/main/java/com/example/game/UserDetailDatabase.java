package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.game.LoginActivity.loggedUserName;

public class UserDetailDatabase {


    private static  String DBNAME = "DetailUser" + loggedUserName;
    private static  String DBTABLE = "details" + loggedUserName;

    public static final String KEY_ROWID = "_id";
    public static final String KEY_LOGGED_NAME = "LoggedIn";
    public static final String KEY_VILLAGE_TYPE = "Village";
    public static final String KEY_XCOORD = "XCoord";
    public static final String KEY_YCOORD = "YCoord";
    private static final String TAG = "DBAdapter";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + DBTABLE + " (_id integer primary key autoincrement, "
                    + KEY_LOGGED_NAME +", "
                    + KEY_VILLAGE_TYPE +", "
                    + KEY_XCOORD +", "
                    + KEY_YCOORD +")";

    private Context context = null;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public UserDetailDatabase(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DBNAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            System.out.println("userDetailDatabase: " + DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DBTABLE);
            onCreate(db);
        }
    }

    public void open() throws SQLException
    {
        System.out.println("UserDetailDatabase LoggedUserName: " + loggedUserName);
        System.out.println("UserDetailDatabase: " + DBNAME + ", " + DBTABLE);
        db = DBHelper.getWritableDatabase();
    }


    public void close()
    {
        DBHelper.close();
    }

    public Cursor getRecords(){
        Cursor cursor = db.query(DBTABLE, null, null, null, null, null, null);
        return cursor;
    }

    public long AddData(String login, String villageType, int xCoord, int yCoord){
        ContentValues newData = new ContentValues();
        newData.put(KEY_LOGGED_NAME, login);
        newData.put(KEY_VILLAGE_TYPE, villageType);
        newData.put(KEY_XCOORD, String.valueOf(xCoord));
        newData.put(KEY_YCOORD, String.valueOf(yCoord));
        return db.insert(DBTABLE, null, newData);
    }

}
