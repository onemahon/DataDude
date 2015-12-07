package com.azandria.placeFinder.cards;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.azandria.datadude.R;

public class HopperCard extends CardView {

    ///////////
    // region Default Constructors

    public HopperCard(Context context) {
        super(context);
        init(context);
    }

    public HopperCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HopperCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // endregion
    ///////////

    ///////////
    // region Private Methods

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_hopper_card, this);
    }

    // endregion
    ///////////

}
