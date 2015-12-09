package com.azandria.boildroid.visual;


import com.azandria.boildroid.data.IDataRequestMethod;

public interface StateChanger {
    void showContent();
    void showLoading();
    void showEmpty();
    void showError(IDataRequestMethod requestMethod);
}
