package wtopolski.pl.main;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wtopolski.pl.main.db.DBContract;
import wtopolski.pl.main.db.ElementProvider;


/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] projection = new String[] {DBContract._ID, DBContract.TITLE_COLUMN, DBContract.DESC_COLUMN};
        Cursor cursor = getActivity().getContentResolver().query(ElementProvider.CONTENT_URI, projection, null, null, null);


    }
}
