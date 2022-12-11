package com.gougou.adaptor2;

/**
 * className: ClassService
 * package: com.gougou.adaptor2
 * Description: 目标接口
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/11 16:02
 */
public interface ClassService {

    boolean match(String classType);

    ClassInfoBO getClassInfo(String classId);
}
