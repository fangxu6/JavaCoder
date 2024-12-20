package com.gougou.testable;


//import com.gougou.singletonlock.IdGenerator;


public class Transaction {
    private String id;
    private Long buyerId;
    private Long sellerId;
    private Long productId;
    private Long orderId;
    private Long createTimestamp;
    private Double amount;
    private STATUS status;
    private String walletTransactionId;

    // ...get() methods...

    public Transaction(String preAssignedId, Long buyerId, Long sellerId, Long productId, Long orderId) {
        if (preAssignedId != null && !preAssignedId.isEmpty()) {
            this.id = preAssignedId;
        } else {
//            this.id = IdGenerator.generateTransactionId();
            this.id = "11";

        }
        if (!this.id.startsWith("t_")) {
            this.id = "t_" + preAssignedId;
        }
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.orderId = orderId;
        this.status = STATUS.TO_BE_EXECUTD;
        this.createTimestamp = System.currentTimeMillis();
    }

    public boolean execute() throws Exception {
        if (buyerId == null || sellerId == null ) {
            throw new Exception("Invalid transaction");
        }
        if (status == STATUS.EXECUTED) return true;
        boolean isLocked = false;
        try {
//            isLocked = RedisDistributedLock.getSingletonIntance().lockTransction(id);
            isLocked = true;
            if (!isLocked) {
                return false; // 锁定未成功，返回false，job兜底执行
            }
            if (status == STATUS.EXECUTED)
                return true; // double check
//            long executionInvokedTimestamp = System.currentTimeMillis();
//            if (executionInvokedTimestamp - createTimestamp > 14 * 24 * 3600 * 1000) {
//                this.status = STATUS.EXPIRED;
//                return false;
//            }
            if (isExpired()) {
                this.status = STATUS.EXPIRED;
                return false;
            }
            WalletRpcService walletRpcService = new WalletRpcService();
            String walletTransactionId = walletRpcService.moveMoney(id, buyerId, sellerId, amount);
            if (walletTransactionId != null) {
                this.walletTransactionId = walletTransactionId;
                this.status = STATUS.EXECUTED;
                return true;
            } else {
                this.status = STATUS.FAILED;
                return false;
            }
        } finally {
            if (isLocked) {
//                RedisDistributedLock.getSingletonIntance().unlockTransction(id);
            }
        }
    }

    protected boolean isExpired() {
        long executionInvokedTimestamp = System.currentTimeMillis();
        return executionInvokedTimestamp - createTimestamp > 14*24*3600*1000;
    }

    public STATUS getStatus() {
        return STATUS.ERROR;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}