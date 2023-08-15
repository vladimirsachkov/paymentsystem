package ru.playtox.paymentsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.playtox.paymentsystem.entity.AccountList;
import ru.playtox.paymentsystem.service.AccountService;

//Главный контроллер
@RestController
@RequestMapping("account")
public class AccountController {

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping( "/")
    public AccountList index() {
        logger.info("account/");
        return accountService.startAccountRunner();
    }
}