package com.neptuo.vocabularyapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DownloadUrlListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> items;
    private OnItemSelectedListener itemSelectedListener;
    private final List<String> selectedItems;

    public DownloadUrlListAdapter(Context context, List<String> items) {
        super(context, -1, items);
        this.context = context;
        this.items = items;
        this.selectedItems = new ArrayList<String>();
    }

    public void setItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.item_download_url, parent, false);

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);

        String item = items.get(position);

        checkBox.setText(item);
        checkBox.setTag(item);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String item = (String) ((CheckBox) buttonView).getTag();
                if (isChecked) {
                    selectedItems.add(item);
                } else {
                    selectedItems.remove(item);
                }

                if (itemSelectedListener != null) {
                    if (isChecked || !selectedItems.isEmpty()) {
                        itemSelectedListener.onItemSelected();
                    } else {
                        itemSelectedListener.onNothingSelected();
                    }
                }
            }
        });

        return rowView;
    }

    public interface OnItemSelectedListener {
        void onItemSelected();
        void onNothingSelected();
    }
}
