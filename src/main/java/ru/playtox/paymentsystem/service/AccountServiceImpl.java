package ru.playtox.paymentsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.playtox.paymentsystem.entity.AccountList;
import ru.playtox.paymentsystem.thread.AccountThreadRunner;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountThreadRunner accountThreadRunner;

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public AccountList startAccountRunner(){
        logger.info("using method startAccountRunner()...");
        return accountThreadRunner.start();
    }
}
