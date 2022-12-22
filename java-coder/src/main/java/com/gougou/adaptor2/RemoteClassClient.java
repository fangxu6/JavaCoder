package com.gougou.adaptor2;

/**
 * className: RemoteClassClient
 * package: com.gougou.adaptor2
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/11 15:59
 */
public interface RemoteClassClient {

    default OneClass getOne() {
        return OneClass.builder().lessonNo("one").lessonName("1V1").build();
    }

    default SmallClass getSmall() {
        return SmallClass.builder().lessonNo("small").lessonName("小班课").build();
    }

    default BigClass getBig() {
        return BigClass.builder().lessonNo("big").lessonName("大班课").build();
    }
}
