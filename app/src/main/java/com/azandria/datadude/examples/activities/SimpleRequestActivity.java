package com.azandria.datadude.examples.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.azandria.datadude.R;
import com.azandria.datadude.data.BasicDataRequestResponse;
import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.IDataRequestFilter;
import com.azandria.datadude.data.IDataRequestMethod;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class SimpleRequestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        BasicDataRequestResponse<String> response = new BasicDataRequestResponse<String>() {
            @Override
            public void onCompleted(IDataRequestMethod method, final String result) {
                super.onCompleted(method, result);
                Log.d(getClass().getName(), "onCompleted called for string request.");

                final TextView textView = (TextView) findViewById(R.id.activity_test_TextView);
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(result);
                    }
                });
            }

            @Override
            public void onBegun(IDataRequestMethod method) {
                super.onBegun(method);

                Log.d(getClass().getName(), "onBegun called for string request.");
            }

            @Override
            public void onCancelled(IDataRequestMethod method) {
                super.onCancelled(method);
                Log.d(getClass().getName(), "onCancelled called for string request.");
            }

            @Override
            public void onError(IDataRequestMethod method, int errorCode) {
                super.onError(method, errorCode);
                Log.d(getClass().getName(), "onError called for string request.");
            }

            @Override
            public void onQueued(IDataRequestMethod method) {
                super.onQueued(method);
                Log.d(getClass().getName(), "onQueued called for string request.");
            }

            @Override
            public void onTimeout(IDataRequestMethod method) {
                super.onTimeout(method);
                Log.d(getClass().getName(), "onTimeout called for string request.");
            }
        };

        new DataRequestBuilder<String>(response)
            .addRequestMethod(new BookRequestMethod())
            .execute();
    }

    private static class BookRequestMethod implements IDataRequestMethod<String> {

        @Override
        public boolean makeRequestDespitePreviousSuccess() {
            return true;
        }

        @Override
        public void doRequest(final DataRequestBuilder.RequestMethodListener<String> listener, Collection<IDataRequestFilter> filters) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    listener.onCompleted("Brave New World");
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 2000);
        }
    }
}
