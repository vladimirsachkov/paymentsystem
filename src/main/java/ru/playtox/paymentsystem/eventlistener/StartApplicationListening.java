package ru.playtox.paymentsystem.eventlistener;


import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.playtox.paymentsystem.service.ServerInfoService;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


//Логика запуска стартовой страницы после запуска Spring Boot приложения.
@Component
public class StartApplicationListening {

    //port - Порт приложения
    //contextName - корневой URL путь приложения.
    //beginController - первоначальный контроллер.
    private final String URLPATTERN = "http://localhost:${port}/${contextName}/${beginController}/";

    @Autowired
    private ServerInfoService serverInfoService;

    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {;
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("port", String.valueOf(serverInfoService.getPortApp()));
        urlParameters.put("contextName", serverInfoService.getContextPath());
        urlParameters.put("beginController", "account");
        StringSubstitutor substitutor = new StringSubstitutor(urlParameters);
        //Стартовая ссылка для запуска приложения
        String indexUrl = substitutor.replace(URLPATTERN);
        System.out.println("Application started ... launching browser now");
        browse(indexUrl);
    }

    public static void browse(String url) {
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
