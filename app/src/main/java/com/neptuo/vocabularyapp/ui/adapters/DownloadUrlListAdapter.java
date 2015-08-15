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
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UrlModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DownloadUrlListAdapter extends ArrayAdapter<UrlModel> {
    private final Context context;
    private OnItemSelectedListener itemSelectedListener;
    private final List<UrlModel> selectedItems;

    public DownloadUrlListAdapter(Context context, List<UrlModel> items) {
        super(context, -1, items);
        this.context = context;
        this.selectedItems = new ArrayList<UrlModel>();
    }

    public void setItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    public List<UrlModel> getSelectedItems() {
        return selectedItems;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.item_download_url, parent, false);

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);

        UrlModel item = getItem(position);

        checkBox.setText(item.getName());
        checkBox.setTag(item);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UrlModel item = (UrlModel) ((CheckBox) buttonView).getTag();
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

        for (DetailModel detail : ServiceProvider.getDetails()) {
            for (UrlModel url : detail.getDownload().getUrls()) {
                if(url.getValue().equals(item.getValue())) {
                    checkBox.setChecked(true);
                }
            }
        }

        return rowView;
    }

    public interface OnItemSelectedListener {
        void onItemSelected();
        void onNothingSelected();
    }
}
