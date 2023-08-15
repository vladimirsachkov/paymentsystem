package ru.playtox.paymentsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//Рандом
@Component
public class RandomServiceImpl implements RandomService {

    private static Logger logger = LoggerFactory.getLogger(RandomServiceImpl.class);

    //Получение случайного числа в диапазоне[min. max]
    @Override
    public int getRandomValue(int min, int max) {
        logger.info("using method getRandomValue()...");
        return (int) ((Math.random() * (max - min)) + min);
    }
}
