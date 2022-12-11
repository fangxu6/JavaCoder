package com.gougou.adaptor2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * className: OneClassAdapter
 * package: com.gougou.adaptor2
 * Description: 1v1适配器
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/11 16:02
 */
@Component
@RequiredArgsConstructor
public class SmallClassAdapter implements ClassService {
    private static final String TYPE = "2";

    private final RemoteClassClient classClient;

    @Override
    public boolean match(String classType) {
        return TYPE.equals(classType);
    }

    @Override
    public ClassInfoBO getClassInfo(String classId) {
        final SmallClass small = classClient.getSmall();
        return ClassInfoBO.builder()
                .type("2")
                .classId(classId)
                .lessonNo(small.getLessonNo()).lessonName(small.getLessonName())
                .build();
    }
}
