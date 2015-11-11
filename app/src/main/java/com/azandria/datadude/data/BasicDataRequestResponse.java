package com.azandria.datadude.data;

public class BasicDataRequestResponse<DataType> implements IDataRequestResponse<DataType> {

    @Override
    public void onCompleted(DataRequestMethod method, DataType dataType) {

    }

    @Override
    public void onError(DataRequestMethod method, int errorCode) {

    }

    @Override
    public void onTimeout(DataRequestMethod method) {

    }

    @Override
    public void onCancelled(DataRequestMethod method) {

    }

    @Override
    public void onBegun(DataRequestMethod method) {

    }

    @Override
    public void onQueued(DataRequestMethod method) {

    }
}
