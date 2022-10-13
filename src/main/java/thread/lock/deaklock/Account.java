package thread.lock.deaklock;

/**
 * @author fangxu
 * on date:2022/10/13
 */

class Account {
    private int balance;
    // 转账
    void transfer(Account target, int amt){
        // 锁定转出账户
        synchronized(this) {
            // 锁定转入账户
            synchronized(target) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
