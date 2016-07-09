package com.android.pluginapk.net;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class NetUtils {

    private static int MAX_CON_TIMEOUT = 5000;
    private static int MAX_SO_TIMEOUT = 10000;

    /**
     * @param url
     * @param content
     * @return
     * @throws Exception
     */
//    public static String doHttpPostRequest(final String url, final String content)
//            throws Exception {
//        String httpUrl = url;
//        String resultData = "";
//        URL u = null;
//        try {
//            u = new URL(httpUrl);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        if (null != u) {
//            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
//            urlConnection.setDoOutput(true);
//            urlConnection.setDoInput(true);
//            urlConnection.setRequestMethod("POST");
//            urlConnection.setUseCaches(false);
//            urlConnection.setInstanceFollowRedirects(true);
//            urlConnection.setConnectTimeout(MAX_CON_TIMEOUT);
//            urlConnection.setReadTimeout(MAX_SO_TIMEOUT);
//            urlConnection.connect();
//            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
//            out.writeBytes(content);
//            out.flush();
//            out.close();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String inputLine = null;
//            while (((inputLine = reader.readLine()) != null)) {
//                resultData += inputLine + "\n";
//            }
//            reader.close();
//            urlConnection.disconnect();
//            return resultData;
//        }
//        return null;
//    }

    /**
     * @param url
     * @param content
     * @return
     * @throws Exception
     */
    public static String doHttpPostRequest(final String url, final String content)
            throws Exception {
        String ret = null;
        HttpResponse httpResponse;
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, MAX_CON_TIMEOUT);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, MAX_SO_TIMEOUT);
        int statusCode;
        try {
            HttpPost request = new HttpPost(url);
            StringEntity entity = new StringEntity(content);
            request.setEntity(entity);
            httpResponse = httpclient.execute(request);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            // 200请求成功 303重定向 400请求错误 401未授权 403禁止访问 404文件未找到 500服务器错误
            if (statusCode == HttpStatus.SC_OK) {
                ret = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            throw new Exception(null, e);
        }
        return ret;
    }

}
