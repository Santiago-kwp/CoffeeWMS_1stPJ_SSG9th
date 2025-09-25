package exception.transaction;

import constant.transaction.ErrorCode;

/*
    사용자 정의 입출고 예외
 */
public class TransactionException extends RuntimeException{
    public final ErrorCode error;

    public TransactionException(ErrorCode error) {
        super(error.getMsg());
        this.error = error;
    }
}