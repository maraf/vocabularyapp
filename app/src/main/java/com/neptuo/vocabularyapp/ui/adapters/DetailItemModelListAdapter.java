package com.neptuo.vocabularyapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;

import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DetailItemModelListAdapter extends ArrayAdapter<DetailItemModel> {
    private final Context context;
    private final List<DetailItemModel> items;

    public DetailItemModelListAdapter(Context context, List<DetailItemModel> items) {
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.browse_item, parent, false);
        TextView originalText = (TextView) rowView.findViewById(R.id.originalText);
        TextView translatedText = (TextView) rowView.findViewById(R.id.translatedText);

        DetailItemModel item = items.get(position);

        originalText.setText(item.getOriginalText());
        translatedText.setText(item.getTranslatedText());

        return rowView;
    }

}
