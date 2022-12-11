package com.gougou.adaptor2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * className: ClassAdapterFactory
 * package: com.gougou.adaptor2
 * Description: 课程信息适配器工厂
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/11 16:10
 */
@Service
@RequiredArgsConstructor
public class ClassAdapterFactory {
    private final List<ClassService> classServiceList;

    ClassService getAdapter(String classType) {
        return classServiceList.stream()
                .filter(cs -> cs.match(classType)).findFirst()
                .orElse(null);
    }
}
