package com.gougou.googleeventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

/**
 * Created by zhangzh on 2017/1/10. 
 */
public class EventBusCenter {

//    private static EventBus eventBus = new EventBus();

    private static EventBus eventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());

    private EventBusCenter() {

    }

    public static EventBus getInstance() {
        return eventBus;
    }

    public static void register(Object obj) {
        eventBus.register(obj);
    }

    public static void unregister(Object obj) {
        eventBus.unregister(obj);
    }

    public static void post(Object obj) {
        eventBus.post(obj);
    }

}  
