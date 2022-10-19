package com.godeltech.gbf.scheduled;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PingTask {
    @Value("${pingtask.url}")
    private String urlForPing;

    @Scheduled(fixedRateString = "${pingtask.period}")
    public void pingMe() {
        try {
            URL url = new URL(urlForPing);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.disconnect();
            System.out.println("Ping job run...");
        } catch (IOException e) {
            System.out.println("Ping job exited with error");
        }
    }
}
