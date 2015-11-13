package wtopolski.pl.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 10c on 2015-11-12.
 */
public class ElementHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private TextView mDesc;
    private View view;

    public ElementHolder(View itemView) {
        super(itemView);
        view = itemView;
        mTitle = (TextView) view.findViewById(R.id.list_element_title);
        mDesc = (TextView) view.findViewById(R.id.list_element_desc);
    }

    public void set(String title, String desc) {
        mTitle.setText(title);
        mDesc.setText(desc);
    }
}
