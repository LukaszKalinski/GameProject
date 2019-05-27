package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.game.LoginActivity.loggedUserName;

public class BuildingsDatabase {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_BUILDINGNAME = "BuildingName";
    public static final String KEY_XCOORD = "XCoord";
    public static final String KEY_YCOORD = "YCoord";
    public static final String KEY_LEVEL = "Level";
    public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_PRODUCT = "Product";
    public static final String KEY_PRODUCTIONTIME = "ProductionTime";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "Buildingsof" + loggedUserName;
    private static final String DATABASE_TABLE = loggedUserName;
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " (_id integer primary key autoincrement, "
                    + KEY_XCOORD +", "
                    + KEY_YCOORD +", "
                    + KEY_BUILDINGNAME +", "
                    + KEY_LEVEL +", "
                    + KEY_DESCRIPTION +", "
                    + KEY_PRODUCT +", "
                    + KEY_PRODUCTIONTIME +")";

    private Context context = null;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public BuildingsDatabase(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            System.out.println(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

        public void open() throws SQLException
        {
            db = DBHelper.getWritableDatabase();
        }


        public void close()
        {
            DBHelper.close();
        }

        public long AddBuilding(String buildingName, String xCoord, String yCoord, String level, String description, String product, String productionTime)

        {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_BUILDINGNAME, buildingName);
            initialValues.put(KEY_XCOORD, xCoord);
            initialValues.put(KEY_YCOORD, yCoord);
            initialValues.put(KEY_LEVEL, level);
            initialValues.put(KEY_DESCRIPTION, description);
            initialValues.put(KEY_PRODUCT, product);
            initialValues.put(KEY_PRODUCTIONTIME, productionTime);
            return db.insert(DATABASE_TABLE, null, initialValues);

        }

        public boolean databaseIsEmpty(){
        if (db == null){
            return true;
            }
            return false;
        }

        public Cursor getRecords(){
            Cursor cursor = db.query(DATABASE_TABLE, null, null, null, null, null, null);
            return cursor;
        }

        public String getRecordByXCoords(String xCoord){
        String xCoords;
            String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + KEY_XCOORD + " = ?";
            Cursor cursor = db.rawQuery(query, new String[] {xCoord});
            if (cursor.getCount()<1){
                cursor.close();
                xCoords = "not found";
                return xCoords;
            } else {
                cursor.moveToFirst();
                xCoords = cursor.getString(cursor.getColumnIndex(KEY_XCOORD));
                cursor.close();
                return xCoords;
            }
        }

        public void raiseCurrentBuildingLevelByOne(String name, int currentLevel){
        String[] names = new String[] {name};
        ContentValues newLevel = new ContentValues();
        newLevel.put(KEY_LEVEL, String.valueOf(currentLevel + 1));
        db.update(DATABASE_TABLE, newLevel, KEY_BUILDINGNAME + "=?", names);

        }

    }


