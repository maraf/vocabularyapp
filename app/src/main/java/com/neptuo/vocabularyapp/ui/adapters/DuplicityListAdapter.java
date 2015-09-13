package com.neptuo.vocabularyapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;

import java.util.List;

/**
 * Created by Windows10 on 9/13/2015.
 */
public class DuplicityListAdapter extends ArrayAdapter<DetailItemModel> {
    private final Context context;

    public DuplicityListAdapter(Context context, List<DetailItemModel> items) {
        super(context, -1, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_duplicity, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text);

        DetailItemModel item = getItem(position);
        textView.setText(item.getOriginalText() + " <-> " + item.getTranslatedText());

        return rowView;
    }
}
