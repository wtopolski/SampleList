package wtopolski.android.samplelist;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class ElementSingleFragment extends Fragment {

    public static final String ARGUMENT_ID = "id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single, container, false);

        ((MainActivity)getActivity()).mToolbar.setSubtitle(R.string.fragment_single);

        return view;
    }
}
