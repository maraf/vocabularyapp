package com.neptuo.vocabularyapp.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.PercentageConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailItemViewModel;

import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class BrowseItemListAdapter extends ArrayAdapter<UserDetailItemModel> {
    private final Context context;
    private final List<UserDetailItemModel> items;

    public BrowseItemListAdapter(Context context, List<UserDetailItemModel> items) {
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
        FrameLayout percentageLayout = (FrameLayout) rowView.findViewById(R.id.percentageLayout);
        TextView percentageText = (TextView) rowView.findViewById(R.id.percentageText);

        UserDetailItemModel item = items.get(position);
        UserDetailItemViewModel itemViewModel = new UserDetailItemViewModel(item);
        int totalCount = itemViewModel.getUserModel().getTotalCount();
        int percentage = (int) (itemViewModel.getCorrentGuessRatio() * 100);

        originalText.setText(item.getModel().getOriginalText());
        translatedText.setText(item.getModel().getTranslatedText());

        if(totalCount == 0) {
            percentageText.setText("---");
            percentageLayout.setBackgroundColor(Color.alpha(0));
        } else {
            //ViewGroup.LayoutParams params = percentageLayout.getLayoutParams();
            //params.width = PercentageConverter.mapToSize(params.width, percentage);
            //percentageLayout.setLayoutParams(params);
            percentageText.setText(percentage + "%");
            percentageLayout.setBackgroundColor(PercentageConverter.mapToColor(percentage));
        }

        return rowView;
    }

}
