package qcc.rule.userRule;

import com.alibaba.fastjson2.JSON;
import qcc.rule.RuleBaseObj;
import qcc.util.CryptoUtil;
import java.util.List;

public class MultiSignature extends RuleBaseObj {
    public String multiaddress;        // 最终生成的多签虚拟地址
    public List<String> owners;
   // public List<String> signHashs;// 持有人列表 (例如 3 个人)
    public int threshold;         // 多签总人数
    public String credentialHash; // 凭证哈希
    public long nonce;            // 【新增】纳秒因子


    public MultiSignature() {
        this.nonce = System.nanoTime();
    }

    /**
     * 生成多签进程的唯一 Hash (PID)
     * Pool 服务器根据此 Hash 建立“多签构建容器”
     */
    public String getProcessHash() {
        // 将参与人、阈值、nonce 混合哈希
        String raw = JSON.toJSONString(owners) + threshold + nonce;
        return  CryptoUtil.sha3256Hex(raw);
    }

    public String toJson() { return JSON.toJSONString(this); }
}