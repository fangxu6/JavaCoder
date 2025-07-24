package tsk;

/**
 * className: TestResult
 * package: tsk
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/7/24 21:09
 */
enum TestResult {
    Pass(0),
    Fail(1);

    private final int value;

    TestResult(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
