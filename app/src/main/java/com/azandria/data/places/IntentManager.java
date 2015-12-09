package com.azandria.data.places;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.azandria.datadude.R;

public class IntentManager {

    /**
     * Builds and starts an Intent to open the Place in
     * a maps app.
     * @param place The place to open in Maps.
     * @param context A context through which to build & start the intent.
     * @param view A view to start a Snackbar message, if necessary. Optional.
     */
    public static void startMapIntent(Place place, Context context, View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(place.mMapUri);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else if (view != null) {
            final Snackbar snackbar = Snackbar.make(view, R.string.Snackbar_NoMapsApp, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.OK, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
    }
}
