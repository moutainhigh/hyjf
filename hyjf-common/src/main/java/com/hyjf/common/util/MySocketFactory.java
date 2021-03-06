/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.util;

import org.apache.axis.components.net.JSSESocketFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

/**
 * @author PC-LIUSHOUYI
 * @version MySocketFactory, v0.1 2018/12/10 11:19
 */
public class MySocketFactory extends JSSESocketFactory {
    /**
     * Constructor JSSESocketFactory
     *
     * @param attributes
     */
    public MySocketFactory(Hashtable attributes) {
        super(attributes);
    }

    protected void initFactory() throws IOException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Trust always
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Trust always
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // Create empty HostnameVerifier
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };

        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        sslFactory = sc.getSocketFactory();
    }
}
