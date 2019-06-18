package com.starichenkov.presenter.myPresenters;

import com.starichenkov.presenter.interfaces.IPresenterMain;
import com.starichenkov.view.interfaces.IViewMain;

public class PresenterMain implements IPresenterMain {

    private IViewMain iView;

    public PresenterMain(IViewMain iView){
        this.iView = iView;
    }

    @Override
    public void detachView() {
        iView = null;
    }
}
