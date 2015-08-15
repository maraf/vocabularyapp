package com.neptuo.vocabularyapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.ui.viewmodels.DownloadViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DownloadListAdapter extends ArrayAdapter<DownloadModel> {
    private final Context context;
    private final List<DownloadViewModel> viewModels;
    private OnItemSelectedListener itemSelectedListener;

    public DownloadListAdapter(Context context, List<DownloadModel> items) {
        super(context, -1, items);
        this.context = context;
        this.viewModels = new ArrayList<DownloadViewModel>();
    }

    public void setItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    public List<DownloadModel> getModelsToDownload() {
        List<DownloadModel> result = new ArrayList<DownloadModel>();
        for (DownloadViewModel viewModel : viewModels) {
            if(viewModel.isSelected())
                result.add(viewModel.createSelectedModel());
        }

        return result;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_download, parent, false);

        DownloadModel item = getItem(position);

        ListView listView = (ListView) rowView.findViewById(R.id.listView);
        TextView nameText = (TextView) rowView.findViewById(R.id.nameText);

        nameText.setText(item.getSourceLanguage().getName() + " <-> " + item.getTargetLanguage().getName());

        DownloadUrlListAdapter adapter = new DownloadUrlListAdapter(context, item.getUrls());
        adapter.setItemSelectedListener(new DownloadUrlListAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected() {
                if (itemSelectedListener != null) {
                    itemSelectedListener.onItemSelected();
                }
            }

            @Override
            public void onNothingSelected() {
                if (itemSelectedListener != null) {
                    for (DownloadViewModel viewModel : viewModels) {
                        if(viewModel.isSelected())
                            return;
                    }

                    itemSelectedListener.onNothingSelected();
                }
            }

        });
        listView.setAdapter(adapter);

        viewModels.add(new DownloadViewModel(item, adapter));
        return rowView;
    }

    public interface OnItemSelectedListener {
        void onItemSelected();
        void onNothingSelected();
    }
}
