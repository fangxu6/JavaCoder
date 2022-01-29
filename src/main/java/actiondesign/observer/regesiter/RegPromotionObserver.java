package actiondesign.observer.regesiter;

public class RegPromotionObserver implements RegObserver {
    private PromotionService promotionService; // 依赖注入

    //    //同步实现方式
//    @Override
//    public void handleRegSuccess(long userId) {
//        promotionService.issueNewUserExperienceCash(userId);
//    }
    // 异步第一种实现方式，其他类代码不变，就没有再重复罗列
    @Override
    public void handleRegSuccess(long userId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                promotionService.issueNewUserExperienceCash(userId);
            }
        });
        thread.start();
    }
}
