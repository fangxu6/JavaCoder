package actiondesign.observer.regesiter;

import com.google.common.eventbus.Subscribe;

public class EventBusRegPromotionObserver {
    private PromotionService promotionService; // 依赖注入

    @Subscribe
    public void handleRegSuccess(Long userId) {
        promotionService.issueNewUserExperienceCash(userId);
    }
}
