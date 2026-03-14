package qcc.rule;

//顶级4大规则分类
public enum RuleType {

    GodRule,  //上帝规则，创世纪容器一次性完成，一旦发布不可再发
    SystemRule,  //系统规则，由规则治理地址发出，必须规则签名认证（未来支持多签）
    AsseIdRule,   //资产方规则，对发型资产的规则，需要资产方合约地址签名认证
    UserRule      //用户规则，用户签名认证，主要是支付回调，地址订阅服务
}
