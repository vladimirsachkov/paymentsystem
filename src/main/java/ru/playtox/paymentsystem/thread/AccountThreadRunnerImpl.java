package ru.playtox.paymentsystem.thread;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.playtox.paymentsystem.dao.AccountListDAO;
import ru.playtox.paymentsystem.entity.AccountList;
import ru.playtox.paymentsystem.entity.Account;
import ru.playtox.paymentsystem.service.RandomService;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountThreadRunnerImpl implements AccountThreadRunner {

    // Список счетов
    private volatile AccountList accountList;
    @Autowired
    private RandomService randomService;

    @Autowired
    private AccountListDAO accountListDAO;

    //Минимальное колличество потоков
    @Value("2")
    private int minCountThreads;

    //Максимальное колличество потоков
    @Value("5")
    private int maxCountThreads;


    //Колличество потоков
    private int threadsCount;

    //Колличество транзакций
    private volatile int countTransaction = 0;

    //Максимальное колличество транзакци
    @Value("30")
    private int MaxCountTransaction;

    //Минимальное время про
    @Value("1000")
    private int minSleep;

    //максимальное время засыпания
    @Value("2000")
    private int maxValue;

    //Потоки.
    private List<Thread> threads;

    private static Logger logger = LoggerFactory.getLogger(AccountThreadRunnerImpl.class);

    @PostConstruct
    private void init() {
        threadsCount = randomService.getRandomValue(minCountThreads, maxCountThreads);
        creatingThreads();
        accountList = accountListDAO.getAccountList();
    }

    public AccountList start() {
        for (Thread thread: threads) {
            thread.start();
        }

        for (Thread thread: threads) {
            while (thread.isAlive()) {
            };
        }

        accountList.showAccounts();

        return accountList;
    }

    private void creatingThreads() {
        threads = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new AccountThreadRunnerThread();
            threads.add(thread);
        }
    }

    private class AccountThreadRunnerThread extends Thread {

        private int currentTimeSleep;

        public AccountThreadRunnerThread() {
            currentTimeSleep = randomService.getRandomValue(minSleep, maxValue);
        }

        @Override
        public void run() {
            logger.info(Thread.currentThread() + " started");
            try {
                while (countTransaction < MaxCountTransaction) {
                    logger.info(Thread.currentThread() + ":  transfer of account...");
                    //Размер листа счетов
                    int accountListSize = accountList.getSize();

                    //Откуда взять счёт
                    int idFrom = randomService.getRandomValue(0, accountListSize);

                    //Куда отправить счёт
                    int idTo = 0;
                    while (idFrom == idTo) {
                        idTo = randomService.getRandomValue(0, accountListSize);
                    }

                    //С какого счёта перевести операции
                    Account accountFrom = accountList.getAccountList().get(idFrom);

                    //Какая сумма перевода
                    double transferAmount = randomService.getRandomValue(0, (int) accountFrom.getMoney());

                    //на какой счёт перевести сумму
                    Account accountTo = accountList.getAccountList().get(idTo);

                    //Списание
                    accountFrom.debitTheAccount(transferAmount);
                    logger.info(Thread.currentThread() + ": Forgiveness " + accountFrom.toString() + " amount of "
                            + transferAmount);
                    //Перевод
                    accountTo.replenish(transferAmount);
                    logger.info(Thread.currentThread() + ": Account replenishment " + accountTo.toString() + " amount of "
                            + transferAmount);

                    //Сохранение
                    accountList.updateAccount(String.valueOf(Thread.currentThread()), idFrom, accountFrom);
                    accountList.updateAccount(String.valueOf(Thread.currentThread()), idTo, accountTo);

                    logger.info(Thread.currentThread() + ": " + accountFrom);
                    logger.info(Thread.currentThread() + ": " + accountTo);
                    //Завершение
                    logger.info(Thread.currentThread() + ": Transaction is complete");
                    countTransaction++;
                    Thread.sleep(countTransaction);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                logger.info(Thread.currentThread() + " finished");
            }
        }
    }
}
