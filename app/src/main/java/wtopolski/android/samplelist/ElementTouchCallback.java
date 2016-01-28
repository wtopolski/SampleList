package wtopolski.android.samplelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import wtopolski.android.samplelist.model.Element;
import wtopolski.android.samplelist.model.ElementHolder;

/**
 * Created by wojciechtopolski on 28.01.2016.
 */
public class ElementTouchCallback extends ItemTouchHelper.Callback {
    private ElementListAdapter mAdapter;

    public ElementTouchCallback(ElementListAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targetHolder) {
        Element fromElement = null;
        if (viewHolder instanceof ElementHolder) {
            fromElement = ((ElementHolder) viewHolder).getElement();
        }

        Element targetElement = null;
        if (targetHolder instanceof ElementHolder) {
            targetElement = ((ElementHolder) targetHolder).getElement();
        }

        if (fromElement == null || targetElement == null) {
            return false;
        }

        int positionView = viewHolder.getAdapterPosition();
        int positionTarget = targetHolder.getAdapterPosition();
        return mAdapter.moveItem(positionView, positionTarget, fromElement, targetElement);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // Determine id.
        long id = -1;
        if (viewHolder instanceof ElementHolder) {
            ElementHolder elementHolder = (ElementHolder) viewHolder;
            id = elementHolder.getElement().getId();
        }

        mAdapter.deleteItem(viewHolder.getAdapterPosition(), id);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ElementHolder) {
                ElementHolder elementHolder = (ElementHolder) viewHolder;
                elementHolder.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof ElementHolder) {
            ElementHolder itemViewHolder = (ElementHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }
}
