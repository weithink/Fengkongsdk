package com.weithink.fengkong.network;

import android.util.Log;

import com.weithink.fengkong.Contents;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.weithink.fengkong.Contents.ONE_MINUTE;

public class UtilNetworking {
    static ConnectionOptions connectionOptions;
    static TrustManager[] trustAllCerts;
    static HostnameVerifier hostnameVerifier;

    static String ENCODING = "UTF-8";


    public static UtilNetworking.HttpResponse sendPostI(String path) {
        return sendPostI(path, null,  null);
    }

    public static UtilNetworking.HttpResponse sendPostI(String path, String clientSdk) {
        return sendPostI(path, clientSdk,  null);
    }

    public static UtilNetworking.HttpResponse sendPostI(String path, String clientSdk,  Object postBody) {
        String targetURL = Contents.baseUrl + path;

        try {
            connectionOptions.clientSdk = clientSdk;

            HttpsURLConnection connection = createPOSTHttpsURLConnection(
                    targetURL, postBody, connectionOptions);
            UtilNetworking.HttpResponse httpResponse = readHttpResponse(connection);

            debug("Response: %s", httpResponse.response);

            httpResponse.headerFields = connection.getHeaderFields();
            debug("Headers: %s", httpResponse.headerFields + "");

            return httpResponse;
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    private static String getPostDataString(Map<String, String> body) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : body.entrySet()) {
            String encodedName = URLEncoder.encode(entry.getKey(), ENCODING);
            String value = entry.getValue();
            String encodedValue = value != null ? URLEncoder.encode(value, ENCODING) : "";

            if (result.length() > 0) {
                result.append("&");
            }

            result.append(encodedName);
            result.append("=");
            result.append(encodedValue);
        }

        return result.toString();
    }

    static {
        trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        debug("getAcceptedIssuers");

                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                        debug("checkClientTrusted");
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                        debug("checkServerTrusted");
                    }
                }
        };
        hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        connectionOptions = new ConnectionOptions();
    }

    public static class HttpResponse {
        public String response = null;
        public Integer responseCode = null;
        public Map<String, List<String>> headerFields = null;
    }

    public interface IConnectionOptions {
        void applyConnectionOptions(HttpsURLConnection connection);
    }

    static class ConnectionOptions implements IConnectionOptions {
        public String clientSdk;


        @Override
        public void applyConnectionOptions(HttpsURLConnection connection) {
            if (clientSdk != null) {
                connection.setRequestProperty("Client-SDK", clientSdk);
            }
            //Inject local ip address for Jenkins script
            connection.setRequestProperty("Local-Ip", getIPAddress(true));
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setConnectTimeout(ONE_MINUTE);
            connection.setReadTimeout(ONE_MINUTE);
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                connection.setSSLSocketFactory(sc.getSocketFactory());

                connection.setHostnameVerifier(hostnameVerifier);
                debug("applyConnectionOptions");
            } catch (Exception e) {
                debug("applyConnectionOptions %s", e.getMessage());
            }
        }
    }

    static HttpsURLConnection createPOSTHttpsURLConnection(String urlString,Object postBody,
                                                           IConnectionOptions connectionOptions)
            throws IOException {
        DataOutputStream wr = null;
        HttpsURLConnection connection = null;

        try {
            debug("POST request: %s", urlString);
            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();

            connectionOptions.applyConnectionOptions(connection);

            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if (postBody instanceof Map) {
                if (postBody != null && ((Map) postBody).size() > 0) {
                    wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(getPostDataString((Map) postBody));
                }
            }
            if (postBody instanceof String) {
                if (postBody != null && ((String) postBody).length() > 0) {
                    wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes((String) postBody);
                }
            }



            return connection;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (wr != null) {
                    wr.flush();
                    wr.close();
                }
            } catch (Exception e) {
            }
        }
    }


    static HttpResponse readHttpResponse(HttpsURLConnection connection) throws Exception {
        StringBuffer sb = new StringBuffer();
        HttpResponse httpResponse = new HttpResponse();

        try {
            connection.connect();

            httpResponse.responseCode = connection.getResponseCode();
            InputStream inputStream;

            if (httpResponse.responseCode >= 400) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            error("Failed to read response. (%s)", e.getMessage());
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        httpResponse.response = sb.toString();
        return httpResponse;
    }

    static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error("Failed to read ip address (%s)", ex.getMessage());
        }

        return "";
    }


    static void error(String s, String msg) {
        Log.e(s, msg+"");
    }

    static void debug(String s, String msg) {
        Log.d(s, msg+"");
    }

    static void debug(String msg) {
        Log.d("AAA>>>>", msg+"");
    }
}
