package wtopolski.android.samplelist;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import wtopolski.android.samplelist.db.DBContract;
import wtopolski.android.samplelist.db.ElementProvider;
import wtopolski.android.samplelist.model.Element;

/**
 * A placeholder fragment containing a simple view.
 */
public class ElementSingleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOAD_CURSOR_ID = 1;
    public static final String ARGUMENT_ID = "id";
    public static final long ARGUMENT_NONE = -1L;

    private EditText titleEdit;
    private EditText descEdit;
    private Element element;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        element = new Element();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single, container, false);
        titleEdit = (EditText) view.findViewById(R.id.title);
        descEdit = (EditText) view.findViewById(R.id.description);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).mToolbar.setSubtitle(R.string.fragment_single); // TODO Do it better!

        Bundle bundle = new Bundle();
        Bundle arguments = getArguments();
        if (arguments != null) {
            long id = arguments.getLong(ARGUMENT_ID, ARGUMENT_NONE);
            bundle.putLong(ARGUMENT_ID, id);
        }
        getLoaderManager().initLoader(LOAD_CURSOR_ID, bundle, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_single, menu);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case LOAD_CURSOR_ID:
                if (bundle != null) {
                    long elementId = bundle.getLong(ARGUMENT_ID, ARGUMENT_NONE);
                    if (elementId > 0) {
                        String[] projection = new String[] {
                                DBContract.ElementTable._ID,
                                DBContract.ElementTable.TITLE_COLUMN,
                                DBContract.ElementTable.DESC_COLUMN};

                        Uri elementUri = ContentUris.withAppendedId(ElementProvider.ELEMENT_URI, id);
                        return new CursorLoader(getActivity(), elementUri, projection, null, null, null);
                    }
                }
                return null;
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOAD_CURSOR_ID:
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int idColumnIndex = cursor.getColumnIndex(DBContract.ElementTable._ID);
                        int titleColumnIndex = cursor.getColumnIndex(DBContract.ElementTable.TITLE_COLUMN);
                        int descColumnIndex = cursor.getColumnIndex(DBContract.ElementTable.DESC_COLUMN);

                        element.setId(cursor.getLong(idColumnIndex));
                        element.setTitle(cursor.getString(titleColumnIndex));
                        element.setDesc(cursor.getString(descColumnIndex));

                        updateForm();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void updateForm() {
        String title = element.getTitle();
        title = TextUtils.isEmpty(title) ? "" : title;
        titleEdit.setText(title);

        String desc = element.getDesc();
        desc = TextUtils.isEmpty(desc) ? "" : desc;
        descEdit.setText(desc);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO ???
    }

/*
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
        */
}
