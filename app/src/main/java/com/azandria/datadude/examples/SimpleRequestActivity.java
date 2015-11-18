package com.azandria.datadude.examples;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.azandria.datadude.R;
import com.azandria.datadude.data.BasicDataRequestResponse;
import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.DataRequestFilter;
import com.azandria.datadude.data.DataRequestMethod;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class SimpleRequestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        DataRequestBuilder<String> requestBuilder = new DataRequestBuilder<>(new BasicDataRequestResponse<String>() {
            @Override
            public void onCompleted(DataRequestMethod method, final String result) {
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
            public void onBegun(DataRequestMethod method) {
                super.onBegun(method);

                Log.d(getClass().getName(), "onBegun called for string request.");
            }

            @Override
            public void onCancelled(DataRequestMethod method) {
                super.onCancelled(method);
                Log.d(getClass().getName(), "onCancelled called for string request.");
            }

            @Override
            public void onError(DataRequestMethod method, int errorCode) {
                super.onError(method, errorCode);
                Log.d(getClass().getName(), "onError called for string request.");
            }

            @Override
            public void onQueued(DataRequestMethod method) {
                super.onQueued(method);
                Log.d(getClass().getName(), "onQueued called for string request.");
            }

            @Override
            public void onTimeout(DataRequestMethod method) {
                super.onTimeout(method);
                Log.d(getClass().getName(), "onTimeout called for string request.");
            }
        });
        requestBuilder
            .addRequestMethod(new BookRequestMethod())
            .execute();
    }

    private static class BookRequestMethod implements DataRequestMethod<String> {

        @Override
        public boolean makeRequestDespitePreviousSuccess() {
            return true;
        }

        @Override
        public void doRequest(final DataRequestBuilder.RequestMethodListener<String> listener, Collection<DataRequestFilter> filters) {
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
