package com.netty.server.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {

	private static Logger log = Logger.getLogger(HttpUtils.class);

	public final static String DEFAULT_CHARSET = "UTF-8";
	public final static String STRING_EQUALS_MARK = "=";
	public final static String STRING_QUESTION_MARK = "?";
	public final static String STRING_AND_MARK = "&";

	private final static TrustManager easyTrustManager = new X509TrustManager() {
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] x509Certificates, String s)
				throws java.security.cert.CertificateException {
		}
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] x509Certificates, String s)
				throws java.security.cert.CertificateException {
		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[0]; 
		}
	};
	
	private final static HttpClient httpClient;

	static {
		SSLContext sslcontext;
		SSLSocketFactory sf = null;
		try {
			sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { easyTrustManager }, null);
			sf = new SSLSocketFactory(sslcontext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, sf));
		ClientConnectionManager cm = new ThreadSafeClientConnManager(schemeRegistry);
		// cm.setMaxTotal(200);
		// cm.setDefaultMaxPerRoute(20);
		httpClient = new DefaultHttpClient(cm);
	}

	public static String getUrlContent(String urlStr, String charSet) {
		HttpGet httpget = null;
		try {
			httpget = new HttpGet(urlStr);
			httpget.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.2; .NET CLR 2.0.50727; MS-RTC LM 8)");
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			// 读取内容
			if (response.getStatusLine().getStatusCode() == 200 && entity != null) {
				return EntityUtils.toString(entity, charSet == null ? "utf-8" : charSet);
			}
		} catch (Throwable e) {
			log.error("Can't get connection for url (" + urlStr + ")", e);
		} finally {
			// 释放连接
			if (null != httpget) {
				httpget.abort();
				httpget = null;
			}
		}
		return "";
	}
	
	public static String getUrlContentAnyWay(String urlStr) {
		HttpGet httpget = null;
		try {
			httpget = new HttpGet(urlStr);
			httpget.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.2; .NET CLR 2.0.50727; MS-RTC LM 8)");
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			// 读取内容
			if (entity != null) {
				return EntityUtils.toString(entity, "utf-8");
			}
		} catch (Throwable e) {
			log.error("Can't get connection for url (" + urlStr + ")", e);
		} finally {
			// 释放连接
			if (null != httpget) {
				httpget.abort();
				httpget = null;
			}
		}
		return "";
	}

	public static String getUrlContent(String url, Map<String, String> params, String charSet) {

		if (params == null) {
			params = new HashMap<String, String>();
		}

		if (StringUtils.isBlank(charSet)) {
			charSet = DEFAULT_CHARSET;
		}

		StringBuilder builder = new StringBuilder();

		builder.append(url);

		if (url.indexOf("?") != -1) {
			builder.append(STRING_AND_MARK);
		} else {
			builder.append(STRING_QUESTION_MARK);
		}

		for (Entry<String, String> entry : params.entrySet()) {
			builder.append(entry.getKey());
			builder.append(STRING_EQUALS_MARK);
			builder.append(entry.getValue());
			builder.append(STRING_AND_MARK);
		}

		log.info(builder.toString());

		return getUrlContent(builder.toString(), charSet);
	}

	/**
	 * 参数在url后面
	 * 
	 * @param url
	 * @param params
	 * @param charSet
	 * @return
	 */
	public static String postFormByUrlParam(String url, Map<String, String> params, String charSet) {

		if (params == null) {
			params = new HashMap<String, String>();
		}

		if (StringUtils.isBlank(charSet)) {
			charSet = DEFAULT_CHARSET;
		}

		StringBuilder builder = new StringBuilder();

		builder.append(url);

		if (url.indexOf("?") != -1) {
			builder.append(STRING_AND_MARK);
		} else {
			builder.append(STRING_QUESTION_MARK);
		}

		for (Entry<String, String> entry : params.entrySet()) {
			builder.append(entry.getKey());
			builder.append(STRING_EQUALS_MARK);
			builder.append(entry.getValue());
			builder.append(STRING_AND_MARK);
		}

		log.info(builder.toString());

		return postForm(builder.toString(), params, charSet);
	}

	/**
	 * 参数在post体内
	 * 
	 * @param url
	 * @param params
	 * @param charSet
	 * @return
	 */
	public static String postForm(String url, Map<String, String> params, String charSet) {
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.2; .NET CLR 2.0.50727; MS-RTC LM 8)");
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, charSet == null ? "utf-8" : charSet);
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			HttpEntity repEntity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200 && entity != null) {
				return EntityUtils.toString(repEntity, charSet == null ? "utf-8" : charSet);
			}
			log.error("Can't get connection for url (" + url + "),the status code of response is " + response.getStatusLine().getStatusCode());
		} catch (Throwable e) {
			log.error("Can't get connection for url (" + url + ")", e);
		} finally {
			// release any connection resources used by the method
			if (post != null) {
				post.abort();
				post = null;
			}
		}
		return "";
	}

	/**
	 * E支付渠道使用
	 * 
	 * @param url
	 * @param params
	 * @param charSet
	 * @param isSign
	 *            1:签名
	 * @return
	 */
	public static String postStringForEpay(String url, String messageBody, String charSet, boolean isSign) {
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			StringEntity entity = new StringEntity(messageBody, charSet);
			if (isSign) {// 签名
				entity.setContentType("INFOSEC_SIGN/1.0;charSet=" + charSet);
			} else {
				entity.setContentType("application/x-www-form-urlencoded");
			}
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			HttpEntity repEntity = response.getEntity();
			int resStatus = response.getStatusLine().getStatusCode();
			if (resStatus == 200 && entity != null) {
				String resultStr = EntityUtils.toString(repEntity, charSet == null ? "GBK" : charSet);
				return resultStr;
			}
			log.error("Can't get connection for url (" + url + "),the status code of response is " + response.getStatusLine().getStatusCode());
		} catch (Throwable e) {
			log.error("Can't get connection for url (" + url + ")", e);
		} finally {
			if (post != null) {
				post.abort();
				post = null;
			}
		}
		return "";
	}

	public static String httpsPostForm(String url, Map<String, String> params, String charSet) {
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, charSet == null ? "utf-8" : charSet);
			post.setEntity(entity);
			log.info(EntityUtils.toString(entity));
			post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.2; .NET CLR 2.0.50727; MS-RTC LM 8)");
			HttpResponse response = httpClient.execute(post);
			HttpEntity repEntity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200 && repEntity != null) {
				String result = EntityUtils.toString(repEntity, charSet == null ? "utf-8" : charSet);
				log.info(result);
				return result.replaceAll("\n", "");
			}

		} catch (Exception e) {
			log.error("access (" + url + ") error:", e);
		} finally {
			if (post != null) {
				post.abort();
				post = null;
			}
		}
		return "";
	}

	public static String httpsUrlContent(String url, String charSet) {
		HttpGet httpget = null;
		try {
			httpget = new HttpGet(url);
			httpget.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.2; .NET CLR 2.0.50727; MS-RTC LM 8)");
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200 && entity != null) {
				return EntityUtils.toString(entity, charSet == null ? "utf-8" : charSet);
			}
		} catch (Exception e) {
			log.error("access (" + url + ") error:", e);
		} finally {
			if (httpget != null) {
				httpget.abort();
				httpget = null;
			}
		}
		return "";
	}

}
