package qcc.core.qccData;

public enum TranType {
    transaction,
    pay,
    open,    //撮合交易发起
    win       //碰撞交易完成
}
