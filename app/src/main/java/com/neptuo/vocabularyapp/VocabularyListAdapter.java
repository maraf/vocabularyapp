package com.neptuo.vocabularyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.neptuo.vocabularyapp.services.VocabularyItem;

import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class VocabularyListAdapter extends ArrayAdapter<VocabularyItem> {
    private final Context context;
    private final List<VocabularyItem> items;

    public VocabularyListAdapter(Context context, List<VocabularyItem> items) {
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

        VocabularyItem item = items.get(position);

        originalText.setText(item.getOriginalText());
        translatedText.setText(item.getTranslatedText());

        return rowView;
    }

}
