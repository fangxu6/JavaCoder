package com.gougou.adaptor2;

import lombok.Builder;
import lombok.Data;

/**
 * className: OneClass
 * package: com.gougou.adaptor2
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/11 15:58
 */
@Data
@Builder
public class SmallClass {
    // 课程编号
    private String lessonNo;
    // 课程名称
    private String lessonName;

    // 其他信息
    private String one;
}
