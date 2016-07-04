package com.horizon.component.utilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * invoke api service
 *
 * @author Administrator
 */
public class HttpClientConnection {

    private static Logger LOG = LoggerFactory.getLogger(HttpClientConnection.class);

    /**
     * return the response message from the remoting server
     *
     * @param entity
     * @param url
     *
     * @return
     *
     * @throws Exception
     */
    public static StringBuffer getHttpResponse(String url) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response;
        StringBuffer sb;
        try {
            response = httpclient.execute(httpget);
            if (response.getStatusLine().getStatusCode() >= 400)
                throw new Exception(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            sb = processEntity(entity);
        } catch (ClientProtocolException e) {
            LOG.error("When connecting Api Server,raise an error:{}", e.getMessage());
            throw e;
        } catch (IOException e) {
            LOG.error("When connecting Api Server,raise an error:{}", e.getMessage());
            throw e;
        } finally {
            httpget.abort();
            httpclient.getConnectionManager().shutdown();
        }
        return sb;
    }

    /**
     * convert HttpEntity to StringBuffer
     *
     * @param entity
     */
    public static StringBuffer processEntity(HttpEntity entity) {

        StringBuffer sb = new StringBuffer();
        InputStreamReader iReader = null;
        try {
            // get InputStream from the message entity
            InputStream inputStream = entity.getContent();
            iReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(iReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\r\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                iReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb;
    }


    /**
     * Specify the URL to send GET requests
     *
     * @param url   the URL to Send the request
     * @param param The request parameters, the request parameters should be in
     *              the form of 'name1=value1&name2=value2'.
     */
    public static String sendGet(String url, String param, String authToken) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Type",
                    "application/json; charset=utf-8");
            connection.setRequestProperty("X-Auth-Token", authToken);
            connection.connect();
            // Get all the response header fields
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // Define the input stream to read the URL response
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("The GET request is abnormal!" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendGet(String url, String param) {
        LOG.info("----------------------HttpServletUtils Get Start--------------------------------");
        LOG.debug("[URL] ==> " + url);
        LOG.debug("[param] ==> " + param);
        Long startTime = System.currentTimeMillis();
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (null != param) {
                urlNameString = url + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Type",
                    "text/html; charset=utf-8");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                LOG.debug(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            LOG.debug("Send Get Request Meet Error: " + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        Long endTime = System.currentTimeMillis();
        LOG.debug("Call Get Method cost(unit: ms): " + (endTime - startTime));
        LOG.debug("----------------------HttpServletUtils Get End--------------------------------");
        return result;
    }

    public static String sendPost(String url, String param) {
        LOG.debug("----------------------HttpServletUtils Post Start--------------------------------");
        LOG.debug("[URL] ==> " + url);
        LOG.debug("[param] ==> " + param);
        Long startTime = System.currentTimeMillis();
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            if (null != param) {
                out.print(param);
            }
            out.flush();
            Map<String, List<String>> map = conn.getHeaderFields();
            for (String key : map.keySet()) {
                LOG.debug(key + "--->" + map.get(key));
            }
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            LOG.debug("Send POST Request Meet Error:" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Long endTime = System.currentTimeMillis();
        LOG.debug("Call Post Method cost(unit: ms): " + (endTime - startTime));
        LOG.debug("----------------------HttpServletUtils Post End--------------------------------");
        return result;
    }

    @SuppressWarnings("deprecation")
    public static String sendPost(String url, Map<String, String> parramMap) {
        List<BasicNameValuePair> params = getParrams(parramMap);

        String xmlStr = null;
        try {
            LOG.debug("----------------------HttpServletUtils Post Start--------------------------------");
            LOG.debug("[URL] ==> " + url);
            URI uri = new URI(url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postMethod = new HttpPost(uri.toString());
            HttpRequestBase submitMethod = null;
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
            postMethod.setEntity(entity);
            submitMethod = postMethod;

            Long startTime = System.currentTimeMillis();
            HttpResponse resp = httpClient.execute(submitMethod);
            Long endTime = System.currentTimeMillis();

            StatusLine line = resp.getStatusLine();
            LOG.debug("[status] ==> " + line.toString());

            if (line.getStatusCode() == 200) {
                xmlStr = EntityUtils.toString(resp.getEntity());
            } else {
                LOG.debug(resp.getEntity().getContent().toString());
            }
            LOG.debug("Call Post Method cost(unit: ms): "
                    + (endTime - startTime));
            LOG.debug("----------------------HttpServletUtils Post End--------------------------------");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlStr;
    }

    public static List<BasicNameValuePair> getParrams(
            Map<String, String> parramMap) {
        List<BasicNameValuePair> parrams = new ArrayList<BasicNameValuePair>();
        if (parramMap != null && !parramMap.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = parramMap.entrySet();
            Iterator<Map.Entry<String, String>> iter = entrySet.iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String key = entry.getKey();
                String value = entry.getValue();
                parrams.add(new BasicNameValuePair(key, value));
            }
        }
        return parrams;
    }

    public static String createParams(List<BasicNameValuePair> parrams,
                                      String encode) {
        StringBuffer sb = new StringBuffer();
        try {
            for (BasicNameValuePair vp : parrams) {
                String key = vp.getName();
                String value = vp.getValue();
                String entry;
                if (null != value && !value.equals("")) {
                    entry = key + "="
                            + URLEncoder.encode(value, encode).toString();
                } else {
                    entry = key + "=";
                }
                sb.append(entry + "&");
            }
            sb.deleteCharAt(sb.length() - 1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}