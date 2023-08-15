package ru.playtox.paymentsystem.service;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

//Сервис для предоставлении информации о сервере.
@Component
public class ServerInfoServiceImpl implements ServerInfoService {

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private ServletContext context;

    //Получение порта приложения
    @Override
    public int getPortApp() {
        return serverProperties.getPort();
    }

    //Получение context-path
    @Override
    public String getContextPath() {
        return context.getContextPath().replace("/","");
    }
}
