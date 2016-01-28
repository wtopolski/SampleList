package wtopolski.android.samplelist.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import wtopolski.android.samplelist.R;

/**
 * Created by 10c on 2015-11-12.
 */
public class ElementProvider extends ContentProvider {

    private ElementDBHelper helper;

    public static String AUTHORITY;
    public static Uri ELEMENT_URI;
    public static Uri ELEMENT_SILENT_URI;

    static final int ELEMENT = 1;
    static final int ELEMENT_SILENT = 2;
    static final int ELEMENT_LIST = 3;

    private UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        AUTHORITY = getContext().getString(R.string.element_provider);
        ELEMENT_URI = Uri.parse("content://" + AUTHORITY + "/elements");
        ELEMENT_SILENT_URI = Uri.parse("content://" + AUTHORITY + "/elements_silent");

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "elements/*", ELEMENT);
        uriMatcher.addURI(AUTHORITY, "elements_silent/*", ELEMENT_SILENT);
        uriMatcher.addURI(AUTHORITY, "elements", ELEMENT_LIST);

        helper = new ElementDBHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = helper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {
            case ELEMENT_LIST:
                Cursor cursor = db.query(DBContract.ElementTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;

            case ELEMENT:
                String selectionTmp = DBContract.ElementTable._ID + "='" + uri.getLastPathSegment() + "'";
                if (TextUtils.isEmpty(selection)) {
                    selection = "";
                } else {
                    selection += " and ";
                }
                selection = selection + selectionTmp;
                return db.query(DBContract.ElementTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

            default:
                throw new IllegalArgumentException("Unsupported URI for query : " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case ELEMENT_LIST:
                long id = db.insert(DBContract.ElementTable.TABLE_NAME, null, values);
                if (id > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return ContentUris.withAppendedId(uri, id);
                }
                throw new RuntimeException("Problem while inserting into uri" + uri);

            default:
                throw new IllegalArgumentException("Unsupported URI for insert : " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int count;

        switch (uriMatcher.match(uri)) {
            case ELEMENT_LIST:
                count = db.delete(DBContract.ElementTable.TABLE_NAME, selection, selectionArgs);
                break;

            case ELEMENT:
                String selectionTmp = DBContract.ElementTable._ID + "='" + uri.getLastPathSegment() + "'";
                if (TextUtils.isEmpty(selection)) {
                    selection = "";
                } else {
                    selection += " and ";
                }
                selection = selection + selectionTmp;
                count = db.delete(DBContract.ElementTable.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI for delete : " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int count;
        String selectionTmp;

        switch (uriMatcher.match(uri)) {
            case ELEMENT_LIST:
                count = db.update(DBContract.ElementTable.TABLE_NAME, values, selection, selectionArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(ELEMENT_URI, null);
                }
                break;

            case ELEMENT:
                selectionTmp = DBContract.ElementTable._ID + "='" + uri.getLastPathSegment() + "'";
                if (TextUtils.isEmpty(selection)) {
                    selection = "";
                } else {
                    selection += " and ";
                }
                selection = selection + selectionTmp;
                count = db.update(DBContract.ElementTable.TABLE_NAME, values, selection, selectionArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;

            case ELEMENT_SILENT:
                selectionTmp = DBContract.ElementTable._ID + "='" + uri.getLastPathSegment() + "'";
                if (TextUtils.isEmpty(selection)) {
                    selection = "";
                } else {
                    selection += " and ";
                }
                selection = selection + selectionTmp;
                count = db.update(DBContract.ElementTable.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI for update : " + uri);
        }

        return count;
    }

    @Override
    public String getType(Uri uri) {
        throw new IllegalArgumentException("Unsupported URI: " + uri);
    }
}
