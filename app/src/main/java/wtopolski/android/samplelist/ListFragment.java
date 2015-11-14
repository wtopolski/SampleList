package wtopolski.android.samplelist;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wtopolski.android.samplelist.db.DBContract;
import wtopolski.android.samplelist.db.ElementProvider;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOAD_CURSOR_ID = 1;

    private ListFragmentItemClickListener mListener;
    private ElementAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getActivity() instanceof ListFragmentItemClickListener) {
            mListener = (ListFragmentItemClickListener) activity;
        } else {
            throw new RuntimeException("Activity must implement ListFragmentItemClickListener interface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ElementAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(LOAD_CURSOR_ID, new Bundle(), this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.setListener(mListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.setListener(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOAD_CURSOR_ID:
                String[] projection = new String[] {
                        DBContract.ElementTable._ID,
                        DBContract.ElementTable.TITLE_COLUMN,
                        DBContract.ElementTable.DESC_COLUMN};

                // Returns a new CursorLoader
                return new CursorLoader(getActivity(), ElementProvider.ELEMENT_URI, projection, null, null, null);
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOAD_CURSOR_ID:
                mAdapter.setCursor(data);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOAD_CURSOR_ID:
                mAdapter.setCursor(null);
                break;
            default:
                break;
        }
    }

    public interface ListFragmentItemClickListener {
        void onListFragmentItemClick(long position);
    }
}
