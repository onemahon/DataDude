package com.azandria.datadude.visual;


import com.azandria.datadude.data.IDataRequestMethod;

public interface StateChanger {
    void showContent();
    void showLoading();
    void showEmpty();
    void showError(IDataRequestMethod requestMethod);
}
