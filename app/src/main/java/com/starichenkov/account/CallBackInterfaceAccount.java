package com.starichenkov.account;

import com.starichenkov.RoomDB.Users;

public interface CallBackInterfaceAccount {
    void getUserEvents();

    void openChangeAccountFragment();

    void getAccountData();

    void updateUser(Users user);

    void setCurrentFragment(String nameFragment);
}