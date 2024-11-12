package com.example.bkjeon.base.services.api.v1.thread.repository;

import com.example.bkjeon.base.services.api.v1.thread.domain.Item;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ItemRepository {

    private Map<String, Item> storeMap = new ConcurrentHashMap<>();

    public ItemRepository() {
        storeMap.put("storeA", Item.builder().name("pencil").price(300).build());
        storeMap.put("storeB", Item.builder().name("eraser").price(500).build());
        storeMap.put("storeC", Item.builder().name("mechanical pencil").price(2000).build());
    }

    public int getPriceByStoreName(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return storeMap.get(name).getPrice();
    }

}