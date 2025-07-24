package tsk;


/**
 * className: DieCategory
 * package: tsk
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/7/24 20:40
 */
// Die类别枚举（对应DieCategory）
enum DieCategory {
    Unknow(1),
    PassDie(2),
    FailDie(4),
    SkipDie(8),
    SkipDie2(9),
    NoneDie(16),
    MarkDie(32),
    TIRefPass(64),
    TIRefFail(128);

    private final short value;

    DieCategory(int value) {
        this.value = (short) value;
    }

    public short getValue() {
        return value;
    }
}




