package ru.playtox.paymentsystem.service;

import ru.playtox.paymentsystem.entity.AccountList;

//Сервис для работы с Account
public interface AccountService {
    //Создание экземпляров объекта Account
    public AccountList startAccountRunner();
}
