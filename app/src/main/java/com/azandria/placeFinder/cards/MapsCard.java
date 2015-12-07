package com.azandria.placeFinder.cards;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.azandria.datadude.R;

public class MapsCard extends CardView {

    ///////////
    // region Member Variables

    private TextView mTitle;

    // endregion
    ///////////


    ///////////
    // region Default Constructors

    public MapsCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MapsCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MapsCard(Context context) {
        super(context);
        init(context);
    }

    // endregion
    ///////////

    ///////////
    // region Public Methods

    public void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    // endregion
    ///////////

    ///////////
    // region Private Methods

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_maps_card, this);

        mTitle = (TextView) findViewById(R.id.view_maps_card_Title);
    }

    // endregion
    ///////////

}
