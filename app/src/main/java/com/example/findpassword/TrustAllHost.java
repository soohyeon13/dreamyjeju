package com.example.findpassword;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

class TrustAllHost {
    public void httpsUrlConnection() {
        trustAllHost();
    }

    private void trustAllHost() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {

                return new java.security.cert.X509Certificate[]{};

            }

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)

                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub
            }

            @Override
            public void checkServerTrusted(

                    java.security.cert.X509Certificate[] chain, String authType)

                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub
            }

        }};
    }
}
