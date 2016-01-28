package wtopolski.android.samplelist.model;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import wtopolski.android.samplelist.R;
import wtopolski.android.samplelist.model.Element;

/**
 * Created by 10c on 2015-11-12.
 */
public class ElementHolder extends RecyclerView.ViewHolder {
    private Element mElement;

    private TextView mTitle;
    private TextView mDesc;
    private View view;

    public ElementHolder(View itemView) {
        super(itemView);
        mElement = new Element();
        view = itemView.findViewById(R.id.list_element_box);
        mTitle = (TextView) itemView.findViewById(R.id.list_element_title);
        mDesc = (TextView) itemView.findViewById(R.id.list_element_desc);
    }

    public void updateView() {
        mTitle.setText(mElement.getTitle());
        mDesc.setText(mElement.getDesc());
    }

    public void setOnClickListener(View.OnClickListener listener) {
        view.setClickable(true);
        view.setOnClickListener(listener);
    }

    public Element getElement() {
        return mElement;
    }

    public void onItemSelected() {
        mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mDesc.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void onItemClear() {
        mTitle.setTypeface(Typeface.DEFAULT);
        mDesc.setTypeface(Typeface.DEFAULT);
    }
}
