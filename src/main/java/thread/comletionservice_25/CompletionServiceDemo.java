package thread.comletionservice_25;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * className:CompletionServiceDemo
 * package:thread.comletionservice_25
 * Description:
 *
 * @Date:2022/11/616:43
 * @Author:fangxu6@gmail.com
 */
public class CompletionServiceDemo {
    public static void main(String[] args) {

// 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
// 创建CompletionService
        CompletionService<Integer> cs = new
                ExecutorCompletionService<>(executor);

        /*
        // 异步向电商S1询价
        cs.submit(() -> getPriceByS1());
// 异步向电商S2询价
        cs.submit(() -> getPriceByS2());
// 异步向电商S3询价
        cs.submit(() -> getPriceByS3());
// 将询价结果异步保存到数据库
        for (int i = 0; i < 3; i++) {
            Integer r = cs.take().get();
            executor.execute(() -> save(r));
        }

        */
    }
}
