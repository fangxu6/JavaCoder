package thread.seenandorder;

/**
 * @author fangxu
 * on date:2022/10/14
 */

// 以下代码来源于【参考1】
class VolatileExample {
    int x = 0;
    volatile boolean v = false;
    public void writer() {
        x = 42;
        v = true;
    }
    public void reader() {
        if (v == true) {
            // 这里x会是多少呢？
            //jdk1.5之前可能是0，可能是42
            //jdk1.5之后是42

        }
    }
}