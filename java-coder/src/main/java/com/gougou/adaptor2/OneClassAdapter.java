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
public class OneClassAdapter implements ClassService {
    private static final String TYPE = "1";

    private final RemoteClassClient classClient;

    @Override
    public boolean match(String classType) {
        return TYPE.equals(classType);
    }

    @Override
    public ClassInfoBO getClassInfo(String classId) {
        final OneClass one = classClient.getOne();
        return ClassInfoBO.builder()
                .type("1")
                .classId(classId)
                .lessonNo(one.getLessonNo()).lessonName(one.getLessonName())
                .build();
    }
}
