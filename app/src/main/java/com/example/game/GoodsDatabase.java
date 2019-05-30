package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.game.LoginActivity.loggedUserName;

public class GoodsDatabase {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_STONE = "Stone";
    public static final String KEY_WOOD = "Wood";
    public static final String KEY_FOOD = "Food";
    public static final String KEY_WATER = "Water";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "Goodsof" + loggedUserName;
    private static final String DATABASE_TABLE = "Goods" + loggedUserName;
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " (_id integer primary key autoincrement, "
                    + KEY_STONE +", "
                    + KEY_WOOD +", "
                    + KEY_FOOD +", "
                    + KEY_WATER +")";

    private Context context = null;
    private GoodsDatabase.DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public GoodsDatabase(Context context) {
        this.context = context;
        DBHelper = new GoodsDatabase.DatabaseHelper(context);
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

    public long AddGoodsFirstTime(int StoneQuant, int WoodQuant, int FoodQuant, int WaterQuant){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_STONE, String.valueOf(StoneQuant));
        initialValues.put(KEY_WOOD, String.valueOf(WoodQuant));
        initialValues.put(KEY_FOOD, String.valueOf(FoodQuant));
        initialValues.put(KEY_WATER, String.valueOf(WaterQuant));
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public Cursor getRecords(){
        Cursor cursor = db.query(DATABASE_TABLE, null, null, null, null, null, null);
        return cursor;
    }

    public void raiseGoods(int StoneQuant, int WoodQuant, int FoodQuant, int WaterQuant, int addStoneQuant, int addWoodQuant, int addFoodQuant, int addWaterQuant){
        ContentValues newQuants = new ContentValues();
        newQuants.put(KEY_STONE, String.valueOf(StoneQuant + addStoneQuant));
        newQuants.put(KEY_WOOD, String.valueOf(WoodQuant + addWoodQuant));
        newQuants.put(KEY_FOOD, String.valueOf(FoodQuant + addFoodQuant));
        newQuants.put(KEY_WATER, String.valueOf(WaterQuant + addWaterQuant));
        db.update(DATABASE_TABLE, newQuants, null, null);
    }
}
