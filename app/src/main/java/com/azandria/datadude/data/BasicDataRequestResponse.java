package com.azandria.datadude.data;

public class BasicDataRequestResponse<DataType> implements IDataRequestResponse<DataType> {

    @Override
    public void onCompleted(IDataRequestMethod method, DataType dataType) {

    }

    @Override
    public void onError(IDataRequestMethod method, int errorCode) {

    }

    @Override
    public void onTimeout(IDataRequestMethod method) {

    }

    @Override
    public void onCancelled(IDataRequestMethod method) {

    }

    @Override
    public void onBegun(IDataRequestMethod method) {

    }

    @Override
    public void onQueued(IDataRequestMethod method) {

    }
}
