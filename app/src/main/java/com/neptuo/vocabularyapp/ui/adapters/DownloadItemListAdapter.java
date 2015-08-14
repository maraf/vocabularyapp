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
import com.neptuo.vocabularyapp.services.models.DownloadModel;

import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DownloadItemListAdapter extends ArrayAdapter<DownloadModel> {
    private final Context context;
    private final List<DownloadModel> items;
    private OnItemSelectedListener itemSelectedListener;

    public DownloadItemListAdapter(Context context, List<DownloadModel> items) {
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    public void setItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.item_download, parent, false);

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
        TextView urlText = (TextView) rowView.findViewById(R.id.urlText);

        DownloadModel item = items.get(position);

        checkBox.setText(item.getSourceLanguage().getName() + " -> " + item.getTargetLanguage().getName());
        checkBox.setTag(item);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DownloadModel model = (DownloadModel) ((CheckBox) buttonView).getTag();
                model.setIsSelected(isChecked);

                if (itemSelectedListener != null) {
                    if (isChecked) {
                        itemSelectedListener.onItemSelected(model, isChecked);
                    } else {
                        boolean isAnythingSelected = false;
                        for (DownloadModel item : items) {
                            if(item.isSelected()) {
                                isAnythingSelected = true;
                                break;
                            }
                        }

                        if(!isAnythingSelected)
                            itemSelectedListener.onNothingSelected();
                    }
                }
            }
        });
        urlText.setText(item.getUrls().get(0));

        return rowView;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(DownloadModel model, boolean isChecked);
        void onNothingSelected();
    }
}
