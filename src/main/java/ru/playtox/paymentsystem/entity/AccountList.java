package ru.playtox.paymentsystem.entity;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.playtox.paymentsystem.controller.AccountController;
import ru.playtox.paymentsystem.service.RandomService;

import java.util.LinkedList;
import java.util.List;

//Это бин
//Коллекция, предназначенная для хранения списка Account.
//Колличество account генерируется случано.
//В дипазоне от 4 до 20
//Средства по умолчанию 10000
@Component
public class AccountList {

    //Список счетов
    private List<Account> accountList;

    //Минимальное колличество счетов.
    @Value("4")
    private final int minSize = 4;

    //Мксимальное колличество счетов.
    @Value("20")
    private final int maxSize = 20;

    @Value("10000")
    private double defaultMoney;

    @Autowired
    private RandomService randomService;

    private static Logger logger = LoggerFactory.getLogger(AccountList.class);

    public AccountList() {
    }

    @PostConstruct
    public void init() {
        logger.info("Creating AccountList began....");
        this.defaultMoney = defaultMoney;
        logger.info("defaultMoney = " + defaultMoney);
        int sizeList = randomService.getRandomValue(minSize, maxSize);
        logger.info("sizeList = " + sizeList);
        logger.info("minSize = " + minSize);
        logger.info("maxSize = " + maxSize);
        this.accountList = new LinkedList<>();
        for (int i = 0; i < sizeList; i++) {
            Account account = new Account(defaultMoney);
            accountList.add(account);
            logger.info("account " + account + " was added in AccountList");
        }
        logger.info("AccountList was created!");
        showAccounts();
    }

    public int getSize() {
        return accountList.size();
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void updateAccount(String thread, int id, Account account) {
        accountList.set(id, account);
        logger.info(thread + ": Account " + account.toString() + " saved");
    }

    public void showAccounts() {
        logger.info("Accounts list:");
        for (Account account : accountList) {
            logger.info(account.toString());
        }
    }
}
