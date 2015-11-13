package wtopolski.android.samplelist;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

import wtopolski.android.samplelist.db.DBContract;
import wtopolski.android.samplelist.db.ElementProvider;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Insert test
        ContentValues values = new ContentValues();
        values.put(DBContract.ElementTable.TITLE_COLUMN, "title " + (new Date()).toGMTString());
        values.put(DBContract.ElementTable.DESC_COLUMN, "desc");
        Uri newUri = getContentResolver().insert(ElementProvider.ELEMENT_URI, values);

        // Query test
        String[] projection = new String[] {
                DBContract.ElementTable._ID,
                DBContract.ElementTable.TITLE_COLUMN,
                DBContract.ElementTable.DESC_COLUMN};
        Cursor cursor = getContentResolver().query(ElementProvider.ELEMENT_URI, projection, null, null, null);

        long firstId = -1L;
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(DBContract.ElementTable._ID);
            int titleColumnIndex = cursor.getColumnIndex(DBContract.ElementTable.TITLE_COLUMN);
            int descColumnIndex = cursor.getColumnIndex(DBContract.ElementTable.DESC_COLUMN);

            do {
                if (firstId < 0) {
                    firstId = cursor.getLong(idColumnIndex);
                }
                String title = cursor.getString(titleColumnIndex);
                String desc = cursor.getString(descColumnIndex);
                Log.d("wtopolski", "title: " + title + " desc: " + desc);
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()){
            cursor.close();
        }

        // Update test
        values = new ContentValues();
        values.put(DBContract.ElementTable.DESC_COLUMN, "desc update");
        int updateCount = getContentResolver().update(newUri, values, null, null);
        Log.d("wtopolski", "updateCount: " + updateCount);

        // Delete test
        Uri deleteUri = ContentUris.withAppendedId(ElementProvider.ELEMENT_URI, firstId);
        int deleteCount = getContentResolver().delete(deleteUri, null, null);
        Log.d("wtopolski", "deleteCount: " + deleteCount);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
