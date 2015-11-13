package wtopolski.pl.main.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by 10c on 2015-11-12.
 */
public class ElementProvider extends ContentProvider {

    private ElementDBHelper helper;
    private SQLiteDatabase db;

    public static final String PROVIDER_NAME = "wtopolski.pl.provider";
    public static final String URL = "content://" + PROVIDER_NAME + "/elements";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    @Override
    public boolean onCreate() {
        helper = new ElementDBHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //String lastSegment = uri.getLastPathSegment();

        //SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        //queryBuilder.setTables(DBContract.TABLE_NAME);
/*
        if () {

        } else {
            queryBuilder.appendWhere(DBContract._ID + "=" + uri.getLastPathSegment());
        }*/
        //Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, DBContract._ID);
        //cursor.setNotificationUri(getContext().getContentResolver(), uri);

        //String[] columns = new String[] {DBContract._ID, DBContract.TITLE_COLUMN, DBContract.DESC_COLUMN};

        return db.query(DBContract.TABLE_NAME, projection, null, null, null, null, null);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = db.insert(DBContract.TABLE_NAME, null, values);

        if (id > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete(DBContract.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }
}
