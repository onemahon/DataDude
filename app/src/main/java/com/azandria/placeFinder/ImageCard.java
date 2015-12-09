package com.azandria.placeFinder;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.azandria.datadude.R;

/**
 * A custom implementation of a CardView that just includes a
 * full-width ImageView and a right-aligned TextView below it,
 * each of which can be set via custom attribues.
 */
public class ImageCard extends CardView {

    ///////////
    // region Member Variables

    private int imageDrawableResource;
    private String buttonText;

    // endregion
    ///////////

    ///////////
    // region Default Constructors

    public ImageCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ImageCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageCard(Context context) {
        super(context);
        init(context, null);
    }

    // endregion
    ///////////

    ///////////
    // region Private Methods

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_image_card, this);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.ImageCard, 0, 0);

            try {
                imageDrawableResource = a.getResourceId(R.styleable.ImageCard_imageDrawableResource, 0);
                buttonText = a.getString(R.styleable.ImageCard_buttonText);
            } finally {
                a.recycle();
            }
        }
    }

    // endregion
    ///////////


    ///////////
    // region Lifecycle

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (buttonText != null) {
            TextView button = (TextView) findViewById(R.id.view_image_card_Text);
            button.setText(buttonText);
        }

        if (imageDrawableResource != 0) {
            ImageView image = (ImageView) findViewById(R.id.view_image_card_Image);
            image.setImageResource(imageDrawableResource);
        }
    }

    // endregion
    ///////////
}
