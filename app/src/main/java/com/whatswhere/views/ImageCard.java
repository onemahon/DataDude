package com.whatswhere.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatswhere.R;


/**
 * A custom implementation of a CardView that just includes a
 * full-width ImageView and a right-aligned TextView below it,
 * each of which can be set via custom attributes.
 */
public class ImageCard extends CardView {

    ///////////
    // region Member Variables

    private int mImageDrawableResource;
    private String mTitle;
    private String mButtonText;

    private TextView mContentText;

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
    // region Public Methods

    public void setContent(String text) {
        if (mContentText != null) {
            mContentText.setText(text);

            int visibility = (TextUtils.isEmpty(text)) ? GONE : VISIBLE;
            mContentText.setVisibility(visibility);
        }
    }

    // endregion
    ///////////

    ///////////
    // region Private Methods

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_image_card, this);

        mContentText = (TextView) findViewById(R.id.view_image_card_Content);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.ImageCard, 0, 0);

            try {
                mImageDrawableResource = a.getResourceId(R.styleable.ImageCard_imageDrawableResource, 0);
                mTitle = a.getString(R.styleable.ImageCard_titleText);
                mButtonText = a.getString(R.styleable.ImageCard_buttonText);
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

        if (mButtonText != null) {
            TextView button = (TextView) findViewById(R.id.view_image_card_Text);
            button.setText(mButtonText);
        }

        if (mImageDrawableResource != 0) {
            ImageView image = (ImageView) findViewById(R.id.view_image_card_Image);
            image.setImageResource(mImageDrawableResource);
        }

        if (mTitle != null) {
            TextView title = (TextView) findViewById(R.id.view_image_card_Title);
            title.setText(mTitle);
        }
    }

    // endregion
    ///////////
}
