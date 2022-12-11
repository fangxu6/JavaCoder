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
public class BigClassAdapter implements ClassService {
    private static final String TYPE = "3";

    private final RemoteClassClient classClient;

    @Override
    public boolean match(String classType) {
        return TYPE.equals(classType);
    }

    @Override
    public ClassInfoBO getClassInfo(String classId) {
        final BigClass big = classClient.getBig();
        return ClassInfoBO.builder()
                .type("3")
                .classId(classId)
                .lessonNo(big.getLessonNo()).lessonName(big.getLessonName())
                .build();
    }
}
