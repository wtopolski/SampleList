package wtopolski.android.samplelist;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wtopolski.android.samplelist.db.DBContract;
import wtopolski.android.samplelist.db.ElementProvider;
import wtopolski.android.samplelist.model.Element;
import wtopolski.android.samplelist.model.ElementHolder;

/**
 * Created by 10c on 2015-11-12.
 */
public class ElementListAdapter extends RecyclerView.Adapter<ElementHolder> {

    private Cursor mCursor;
    private Context mCtx;

    private int idColumnIndex;
    private int titleColumnIndex;
    private int descColumnIndex;
    private int priorityColumnIndex;

    private ElementListFragment.ListFragmentItemClickListener mListener;

    public ElementListAdapter(Context ctx) {
        mCtx = ctx;
    }

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
            element.setPriority(mCursor.getLong(priorityColumnIndex));
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
            priorityColumnIndex = cursor.getColumnIndex(DBContract.ElementTable.PRIORITY_COLUMN);
        }

        notifyDataSetChanged();
    }

    public void setListener(ElementListFragment.ListFragmentItemClickListener listener) {
        mListener = listener;
    }

    public void deleteItem(int adapterPosition, long id) {
        Uri deleteUri = ContentUris.withAppendedId(ElementProvider.ELEMENT_URI, id);
        int deleteCount = mCtx.getContentResolver().delete(deleteUri, null, null); // TODO Should be in background thread
        if (deleteCount > 0) {
            mCtx.getContentResolver().notifyChange(ElementProvider.ELEMENT_URI, null);
            mListener.notifyUser(mCtx.getString(R.string.delete_notification));
            notifyItemRemoved(adapterPosition);
        }
    }

    public boolean moveItem(int positionView, int positionTarget, Element from, Element target) {
        // SWAP
        long priority = from.getPriority();
        from.setPriority(target.getPriority());
        target.setPriority(priority);

        // SAVE
        storeInDB(from);
        storeInDB(target);

        // DISPLAY
        notifyItemMoved(positionView, positionTarget);

        return true;
    }

    // TODO Should be in background thread
    private boolean storeInDB(Element element) {
        ContentValues values = new ContentValues();
        values.put(DBContract.ElementTable.PRIORITY_COLUMN, element.getPriority());
        Uri updateUri = ContentUris.withAppendedId(ElementProvider.ELEMENT_SILENT_URI, element.getId());
        mCtx.getContentResolver().update(updateUri, values, null, null);
        return true;
    }
}
