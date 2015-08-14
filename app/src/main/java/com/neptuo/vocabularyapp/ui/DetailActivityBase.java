package com.neptuo.vocabularyapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DetailModel;

/**
 * Created by Windows10 on 8/14/2015.
 */
public class DetailActivityBase extends AppCompatActivity {

    public static final String PARAMETER_DETAIL_INDEX = "detailIndex";
    public static final String PARAMETER_IS_REVERSE = "isReverse";

    protected DetailModel prepareDetailModel() {
        Bundle extras = getIntent().getExtras();

        int detailIndex = extras.getInt(PARAMETER_DETAIL_INDEX, 0);
        boolean isReverse = extras.getBoolean(PARAMETER_IS_REVERSE, false);

        DetailModel model = ServiceProvider.getDetails().get(detailIndex);
        if(isReverse) {
            model = model.reverse();
        }

        return model;
    }

}
