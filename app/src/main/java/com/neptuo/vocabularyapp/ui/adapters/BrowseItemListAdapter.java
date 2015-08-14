package com.neptuo.vocabularyapp.ui.adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.BrowseItemActivity;
import com.neptuo.vocabularyapp.ui.fragments.BrowseItemDialogFragment;
import com.neptuo.vocabularyapp.ui.viewmodels.PercentageConverter;
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
        View rowView = inflater.inflate(R.layout.item_browse, parent, false);
        TextView originalText = (TextView) rowView.findViewById(R.id.originalText);
        TextView translatedText = (TextView) rowView.findViewById(R.id.translatedText);
        FrameLayout percentageLayout = (FrameLayout) rowView.findViewById(R.id.percentageLayout);
        TextView percentageText = (TextView) rowView.findViewById(R.id.percentageText);

        final UserDetailItemModel item = items.get(position);
        UserDetailItemViewModel itemViewModel = new UserDetailItemViewModel(item);
        int totalCount = itemViewModel.getUserModel().getTotalCount();
        int percentage = (int) (itemViewModel.getCorrentGuessRatio() * 100);

        originalText.setText(item.getModel().getOriginalText());
        translatedText.setText(item.getModel().getTranslatedText());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getContext(), BrowseItemActivity.class);
                //intent.putExtra(BrowseItemActivity.PARAMETER_SOURCE_TEXT, item.getModel().getOriginalText());
                //intent.putExtra(BrowseItemActivity.PARAMETER_SOURCE_DESCRIPTION, item.getModel().getOriginalDescription());
                //intent.putExtra(BrowseItemActivity.PARAMETER_TARGET_TEXT, item.getModel().getTranslatedText());
                //intent.putExtra(BrowseItemActivity.PARAMETER_TARGET_DESCRIPTION, item.getModel().getTranslatedDescription());
                //context.startActivity(intent);

                FragmentTransaction transaction = ((FragmentActivity)context).getFragmentManager().beginTransaction();
                BrowseItemDialogFragment fragment = new BrowseItemDialogFragment();
                fragment.setItemModel(item.getModel());
                fragment.show(transaction, "dialog");

            }
        });

        if(totalCount == 0) {
            percentageText.setText("");
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
