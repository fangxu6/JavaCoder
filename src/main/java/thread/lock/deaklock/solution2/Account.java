package thread.lock.deaklock.solution2;

/**
 * @author fangxu
 * on date:2022/10/13
 * 破坏循环等待，安装id的大小，来进行资源的锁定
 */

class Account {
    private int id;
    private int balance;

    // 转账
    void transfer(Account target, int amt) {
        Account left = this;
        Account right = target;
        if (this.id > target.id) {
            left = target;
            right = this;
        }
        // 锁定序号小的账户
        synchronized (left) {
            // 锁定序号大的账户
            synchronized (right) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
