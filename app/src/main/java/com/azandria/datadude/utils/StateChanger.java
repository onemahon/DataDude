package com.azandria.datadude.utils;


public interface StateChanger {
    void showContent();
    void showLoading();
    void showEmpty();
    void showError(IDataRequestMethod requestMethod);
}
