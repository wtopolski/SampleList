package wtopolski.android.samplelist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 10c on 2015-11-12.
 */
public class ElementDBHelper extends SQLiteOpenHelper {

    ElementDBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(DBContract.ElementTable.TABLE_NAME);
        sb.append(" (");
        sb.append(DBContract.ElementTable._ID);
        sb.append(" INTEGER PRIMARY KEY, ");
        sb.append(DBContract.ElementTable.TITLE_COLUMN);
        sb.append(" TEXT, ");
        sb.append(DBContract.ElementTable.DESC_COLUMN);
        sb.append(" TEXT, ");
        sb.append(DBContract.ElementTable.PRIORITY_COLUMN);
        sb.append(" INTEGER)");

        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not implemented
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL("ALTER TABLE " + DBContract.ElementTable.TABLE_NAME + " ADD COLUMN  " + DBContract.ElementTable.PRIORITY_COLUMN + " INTEGER");
        }
    }
}
