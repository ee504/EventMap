package com.starichenkov.presenter.CallBacks;

import com.starichenkov.data.Events;

public interface CallBackCreateEvent extends CallBackCurrentUser{
    void setCurrentEvent(Events event);
}
