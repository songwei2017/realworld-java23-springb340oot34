package com.world.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Data
@ConfigurationProperties(prefix = "thumb")
public class Thumb {

    private String[] list;

    public String getOne(){
        Random random = new Random();
        return list[random.nextInt(list.length)];
    }
}
