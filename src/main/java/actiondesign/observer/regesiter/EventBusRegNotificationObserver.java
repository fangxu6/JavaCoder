package actiondesign.observer.regesiter;

import com.google.common.eventbus.Subscribe;

public class EventBusRegNotificationObserver {
    private NotificationService notificationService;

    @Subscribe
    public void handleRegSuccess(Long userId) {
        notificationService.sendInboxMessage(userId, "...");
    }
}