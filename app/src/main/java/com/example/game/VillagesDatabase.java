package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.game.LoginActivity.loggedUserName;

public class VillagesDatabase {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_STONE_PRODUCTION_INDEX = "Stone";
    public static final String KEY_WOOD_PRODUCTION_INDEX = "Wood";
    public static final String KEY_FOOD_PRODUCTION_INDEX = "Food";
    public static final String KEY_WATER_PRODUCTION_INDEX = "Water";
    public static final String KEY_GOLD_PRODUCTION_INDEX = "Gold";
    public static final String KEY_SILVER_PRODUCTION_INDEX = "Silver";
    public static final String KEY_ARTIFACT_PRODUCTION_INDEX = "Artifact";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "VillageOf" + loggedUserName;
    private static final String DATABASE_TABLE = "VillageInfo" + loggedUserName;
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " (_id integer primary key autoincrement, "
                    + KEY_TYPE +", "
                    + KEY_STONE_PRODUCTION_INDEX +", "
                    + KEY_WOOD_PRODUCTION_INDEX + ", "
                    + KEY_FOOD_PRODUCTION_INDEX +", "
                    + KEY_WATER_PRODUCTION_INDEX +", "
                    + KEY_GOLD_PRODUCTION_INDEX +", "
                    + KEY_SILVER_PRODUCTION_INDEX +", "
                    + KEY_ARTIFACT_PRODUCTION_INDEX +")";

    private Context context = null;
    private VillagesDatabase.DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public VillagesDatabase(Context context) {
        this.context = context;
        DBHelper = new VillagesDatabase.DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            System.out.println("VillageDatabase: " + DATABASE_CREATE);
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

    public long AddVillageType(VillageType villageType){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_STONE_PRODUCTION_INDEX, String.valueOf(villageType.getStoneProductionIndex()));
        initialValues.put(KEY_WOOD_PRODUCTION_INDEX, String.valueOf(villageType.getWoodProductionIndex()));
        initialValues.put(KEY_FOOD_PRODUCTION_INDEX, String.valueOf(villageType.getFoodProductionIndex()));
        initialValues.put(KEY_WATER_PRODUCTION_INDEX, String.valueOf(villageType.getWaterProductionIndex()));
        initialValues.put(KEY_SILVER_PRODUCTION_INDEX, String.valueOf(villageType.getSilverProductionIndex()));
        initialValues.put(KEY_GOLD_PRODUCTION_INDEX, String.valueOf(villageType.getGoldProductionIndex()));
        initialValues.put(KEY_ARTIFACT_PRODUCTION_INDEX, String.valueOf(villageType.getArtifactProductionIndex()));
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public Cursor getRecords(){
        Cursor cursor = db.query(DATABASE_TABLE, null, null, null, null, null, null);
        return cursor;
    }

}
