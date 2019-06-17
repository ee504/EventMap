package com.starichenkov.Contracts;

public interface EventsContract extends MainContract {
    interface View {
    }

    interface Presenter {
        void detachView();
    }

    interface Model {
    }
}
