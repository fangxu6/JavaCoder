package com.gougou.testable;



public class testExecute_with_TransactionIsExpired {

    public void testExecute_with_TransactionIsExpired() throws Exception {
        Long buyerId = 123L;
        Long sellerId = 234L;
        Long productId = 345L;
        Long orderId = 456L;
        Transaction transaction = new Transaction(null, buyerId, sellerId, productId, orderId) {

            @Override
            protected boolean isExpired() {
                return true;
            }

        };
        boolean actualResult = transaction.execute();
//        assertFalse(actualResult);
//        assertEquals(STATUS.EXPIRED, transaction.getStatus());
    }

    public static void main(String[] args) {
        testExecute_with_TransactionIsExpired test = new testExecute_with_TransactionIsExpired();
        try {
            System.out.println("testExecute_with_TransactionIsExpired");
            test.testExecute_with_TransactionIsExpired();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
