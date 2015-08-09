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
import com.neptuo.vocabularyapp.services.models.DefinitionModel;

import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DefinitionModelListAdapter extends ArrayAdapter<DefinitionModel> {
    private final Context context;
    private final List<DefinitionModel> items;

    public DefinitionModelListAdapter(Context context, List<DefinitionModel> items) {
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.download_item, parent, false);

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);
        TextView urlText = (TextView) rowView.findViewById(R.id.urlText);

        DefinitionModel item = items.get(position);

        checkBox.setText(item.getSourceLanguage() + " -> " + item.getTargetLanguage());
        checkBox.setTag(item);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((DefinitionModel)((CheckBox)buttonView).getTag()).setIsSelected(isChecked);
            }
        });
        urlText.setText(item.getUrls().get(0));

        return rowView;
    }

}
