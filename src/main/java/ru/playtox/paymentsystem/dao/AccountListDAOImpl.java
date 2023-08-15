package ru.playtox.paymentsystem.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import ru.playtox.paymentsystem.entity.AccountList;

@Repository
public class AccountListDAOImpl implements AccountListDAO {

    @Autowired
    private ApplicationContext appContext;

    private static Logger logger = LoggerFactory.getLogger(AccountListDAOImpl.class);

    //Получение листа
    @Override
    public AccountList getAccountList() {
        logger.info("using method getAccountList()...");
        return appContext.getBean(AccountList.class);
    }
}
