package com.starichenkov.Contracts;

public interface MainContract {
    interface View {
    }

    interface Presenter {
        void detachView();
    }

    interface Model {
    }
}
