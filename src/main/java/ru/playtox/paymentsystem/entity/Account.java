package ru.playtox.paymentsystem.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.playtox.paymentsystem.controller.AccountController;

import java.util.Objects;
import java.util.UUID;


//Cчет
public class Account {

    //Идентификатор счета
    private String ID;

    //первоначальная сумма
    private double money;

    private static Logger logger = LoggerFactory.getLogger(Account.class);

    public Account(double money) {
        this.ID = UUID.randomUUID().toString();
        this.money = money;
        logger.info("Account " + toString() + " created");
    }

    //пополнить
    public void replenish(double sum) {
        money = money + sum;
    }

    //списать
    public void debitTheAccount(double sum) {
        money = money - sum;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "ID='" + ID + '\'' +
                ", money=" + money +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return money == account.money && Objects.equals(ID, account.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, money);
    }
}
