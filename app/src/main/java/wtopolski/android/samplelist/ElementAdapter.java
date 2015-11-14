package wtopolski.android.samplelist;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wtopolski.android.samplelist.db.DBContract;
import wtopolski.android.samplelist.model.Element;

/**
 * Created by 10c on 2015-11-12.
 */
public class ElementAdapter extends RecyclerView.Adapter<ElementHolder> {

    private Cursor mCursor;

    private int idColumnIndex;
    private int titleColumnIndex;
    private int descColumnIndex;

    private ListFragment.ListFragmentItemClickListener mListener;

    @Override
    public ElementHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_list_element, viewGroup, false);
        return new ElementHolder(v);
    }

    @Override
    public void onBindViewHolder(ElementHolder elementHolder, int position) {
        if (mCursor == null || mCursor.isClosed()) {
            return;
        }

        if (mCursor.moveToPosition(position)) {
            final long id = mCursor.getLong(idColumnIndex);

            Element element = elementHolder.getElement();
            element.setId(id);
            element.setTitle(mCursor.getString(titleColumnIndex));
            element.setDesc(mCursor.getString(descColumnIndex));
            elementHolder.updateView();
            elementHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onListFragmentItemClick(id);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    public void setCursor(Cursor cursor) {
        mCursor = cursor;

        if (cursor != null) {
            idColumnIndex = cursor.getColumnIndex(DBContract.ElementTable._ID);
            titleColumnIndex = cursor.getColumnIndex(DBContract.ElementTable.TITLE_COLUMN);
            descColumnIndex = cursor.getColumnIndex(DBContract.ElementTable.DESC_COLUMN);
        }

        notifyDataSetChanged();
    }

    public void setListener(ListFragment.ListFragmentItemClickListener listener) {
        mListener = listener;
    }
}
