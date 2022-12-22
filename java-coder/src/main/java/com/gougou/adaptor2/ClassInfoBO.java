package com.gougou.adaptor2;

/**
 * className: ClassInfoBO
 * package: com.gougou.adaptor2
 * Description: 课程信息
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/11 16:01
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassInfoBO {
    // 课程类型 1:1v1 2:small 3:big
    private String type;

    // 班级ID
    private String classId;
    // 课程编号
    private String lessonNo;
    // 课程名称
    private String lessonName;
}