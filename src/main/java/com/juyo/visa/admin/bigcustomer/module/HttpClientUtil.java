package com.juyo.visa.admin.bigcustomer.module;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.common.collect.Maps;

public class HttpClientUtil {

	/**
	 * 发送post请求
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, IdentityHashMap<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;

		HttpPost post = postForm(url, params);

		body = invoke(httpclient, post);

		try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	/**
	 * 发送get请求
	 *
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;

		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	public static String upload(String url, File file, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		String fileName = file.getName();
		builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, fileName);

		if (null != params && !params.isEmpty()) {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				String value = params.get(key);
				if (null != value && !"".equals(value.trim())) {
					builder.addTextBody(key, value);
				}
			}
		}

		HttpEntity entity = builder.build();
		post.setEntity(entity);
		String body = invoke(httpclient, post);
		return body;
	}

	public static final int cache = 10 * 1024;

	public static String download(String url, String filePath) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);

			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			byte[] buffer = new byte[cache];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			is.close();
			fileout.flush();
			fileout.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 远程post方式请求代理下载地址，进行文件下载
	 *
	 * @param requestUrl  代理文件下载的地址
	 * @param fileUrl     欲下载的文件地址
	 * @param localFile   下载完成后本地保存的文件全路径
	 */
	public static void agentPostDownload(String requestUrl, String fileUrl, String localFile) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(requestUrl);

			Map<String, String> params = Maps.newHashMap();
			params.put("fileUrl", fileUrl);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			try {
				httppost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			} catch (Exception e) {
				e.printStackTrace();
			}

			HttpResponse response = client.execute(httppost);

			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			File file = new File(localFile);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			byte[] buffer = new byte[2048];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			is.close();
			fileout.flush();
			fileout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String invoke(HttpClient httpclient, HttpUriRequest httpost) {
		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);
		return body;
	}

	private static String paseResponse(HttpResponse response) {
		String body = null;
		HttpEntity httpEntity = response.getEntity();
		if (httpEntity.getContentEncoding() != null) {
			if ("gzip".equalsIgnoreCase(httpEntity.getContentEncoding().getValue())) {
				httpEntity = new GzipDecompressingEntity(httpEntity);
			} else if ("deflate".equalsIgnoreCase(httpEntity.getContentEncoding().getValue())) {
				httpEntity = new DeflateDecompressingEntity(httpEntity);
			}
		}
		ContentType contentType = ContentType.getOrDefault(httpEntity);
		Charset charset = contentType.getCharset();

		try {
			body = EntityUtils.toString(httpEntity, charset);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	private static HttpResponse sendRequest(HttpClient httpclient, HttpUriRequest httpost) {
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpPost postForm(String url, IdentityHashMap<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 需要传一个token
		//httpost.setHeader("token", "c7a4e021-6527-11e6-96be-fcaa14c3021a1");
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return httpost;
	}

}
