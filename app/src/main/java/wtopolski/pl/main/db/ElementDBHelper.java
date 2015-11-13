package wtopolski.pl.main.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 10c on 2015-11-12.
 */
public class ElementDBHelper extends SQLiteOpenHelper {
    ElementDBHelper(Context context) {
        super(context, "element_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE element(_ID INTEGER PRIMARY KEY, TITLE TEXT, DESC TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
