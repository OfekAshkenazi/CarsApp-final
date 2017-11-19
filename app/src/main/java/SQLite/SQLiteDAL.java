package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import Entities.Car;


import static SQLite.SqliteHelper.Entries.*;

/**
 * Created by Android on 11/16/2017.
 */

public class SQLiteDAL {
    SqliteHelper instance;

    public SQLiteDAL(Context context) {
        this.instance = new SqliteHelper(context);
    }

    public long insertCar(Car car) {
        SQLiteDatabase db=instance.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, car.getModel());
        values.put(COL_2, car.getYear());
        values.put(COL_3, car.getColor());
        return db.insert(SqliteHelper.Entries.TABLE_NAME, null, values);
    }

    public ArrayList<Car> getAllCars(@Nullable Object[] searchValues) {
        ArrayList<Car> carsList = new ArrayList<>();
        SQLiteDatabase db = instance.getReadableDatabase();
        Cursor cursor = null;
        Integer row0 = null;
        Integer row1 = null;
        Integer row2 = null;
        Integer row3 = null;
        if (searchValues == null) {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        } else {
            String searchColumn = getCurrentSearchFieldString((Integer) searchValues[0]);
            String value = (String) searchValues[1];
            cursor = db.query(TABLE_NAME, null, searchColumn + " LIKE '%" + value + "%'", null, null, null, searchColumn);
        }
        if (cursor != null) {
            row0 = cursor.getColumnIndex(ID_COL);
            row1 = cursor.getColumnIndex(COL_1);
            row2 = cursor.getColumnIndex(COL_2);
            row3 = cursor.getColumnIndex(COL_3);
            if (row1 == null && row2 == null && row3 == null) {
                Exception exception = new Exception("rows doesn't exists");
                exception.printStackTrace();
                return carsList;
            }
            while (cursor.moveToNext()) {
                Long id = cursor.getLong(row0);
                String model = cursor.getString(row1);
                int year = cursor.getInt(row2);
                String color = cursor.getString(row3);
                carsList.add(new Car(id, model, year, color));
            }
        }
        return carsList;
    }


    public String getCurrentSearchFieldString(int currentSearchField) {
        switch (currentSearchField) {
            case 0:
                return COL_1;
            case 1:
                return COL_2;
            case 2:
                return COL_3;
        }
        return COL_1;
    }


}

class SqliteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "carsDB";
    private static final int DB_VERSION = 1;
    Context context;

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Entries.CREATE_TABLE_QUERY);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entries.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Entries.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    final class Entries {

        protected static final String TABLE_NAME = "CARS_TABLE";
        protected static final String COL_1 = "model";
        protected static final String ID_COL = "_id";
        protected static final String COL_2 = "year";
        protected static final String COL_3 = "color";
        protected static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_1 + " TEXT, "
                + COL_2 + " INTEGER, "
                + COL_3 + " TEXT)";
    }
}
