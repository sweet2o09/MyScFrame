package com.caihan.scframe.api.ssl;

import com.zhouyou.http.utils.HttpLog;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * https的全局访问规则
 *
 * @author caihan
 * @date 2019/3/20
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class UnSafeHostnameVerifier implements HostnameVerifier {
    private String host;

    public UnSafeHostnameVerifier(String host) {
        this.host = host;
        HttpLog.i("############### UnSafeHostnameVerifier " + host);
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        HttpLog.i("############### verify " + hostname + " " + this.host);
        if (this.host == null || "".equals(this.host) || !this.host.contains(hostname))
            return false;
        return true;
    }

}
