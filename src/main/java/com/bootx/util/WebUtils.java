
package com.bootx.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utils - Web
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public final class WebUtils {

	/**
	 * PoolingHttpClientConnectionManager
	 */
	private static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER;

	/**
	 * CloseableHttpClient
	 */
	private static final CloseableHttpClient HTTP_CLIENT;

	static {
		HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build());
		HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
		HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build();
		HTTP_CLIENT = HttpClientBuilder.create().setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER).setDefaultRequestConfig(requestConfig).build();
	}

	/**
	 * 不可实例化
	 */
	private WebUtils() {
	}

	/**
	 * 获取HttpServletRequest
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return requestAttributes != null && requestAttributes instanceof ServletRequestAttributes ? ((ServletRequestAttributes) requestAttributes).getRequest() : null;
	}

	/**
	 * 获取HttpServletResponse
	 * 
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return requestAttributes != null && requestAttributes instanceof ServletRequestAttributes ? ((ServletRequestAttributes) requestAttributes).getResponse() : null;
	}

	/**
	 * 参数解析
	 * 
	 * @param query
	 *            查询字符串
	 * @param encoding
	 *            编码格式
	 * @return 参数
	 */
	public static Map<String, String> parse(String query, String encoding) {
		Assert.hasText(query, "[Assertion failed] - query must have text; it must not be null, empty, or blank");

		Charset charset;
		if (StringUtils.isNotEmpty(encoding)) {
			charset = Charset.forName(encoding);
		} else {
			charset = StandardCharsets.UTF_8;
		}
		List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(query, charset);
		Map<String, String> parameterMap = new HashMap<>();
		for (NameValuePair nameValuePair : nameValuePairs) {
			parameterMap.put(nameValuePair.getName(), nameValuePair.getValue());
		}
		return parameterMap;
	}

	/**
	 * 解析参数
	 * 
	 * @param query
	 *            查询字符串
	 * @return 参数
	 */
	public static Map<String, String> parse(String query) {
		Assert.hasText(query, "[Assertion failed] - query must have text; it must not be null, empty, or blank");

		return parse(query, null);
	}

	/**
	 * 重定向
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param url
	 *            URL
	 * @param contextRelative
	 *            是否相对上下文路径
	 * @param http10Compatible
	 *            是否兼容HTTP1.0
	 */
	public static void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url, boolean contextRelative, boolean http10Compatible) {
		Assert.notNull(request, "[Assertion failed] - request is required; it must not be null");
		Assert.notNull(response, "[Assertion failed] - response is required; it must not be null");
		Assert.hasText(url, "[Assertion failed] - url must have text; it must not be null, empty, or blank");

		StringBuilder targetUrl = new StringBuilder();
		if (contextRelative && url.startsWith("/")) {
			targetUrl.append(request.getContextPath());
		}
		targetUrl.append(url);
		String encodedRedirectURL = response.encodeRedirectURL(String.valueOf(targetUrl));
		if (http10Compatible) {
			try {
				response.sendRedirect(encodedRedirectURL);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else {
			response.setStatus(303);
			response.setHeader("Location", encodedRedirectURL);
		}
	}

	/**
	 * 重定向
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param url
	 *            URL
	 */
	public static void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) {
		sendRedirect(request, response, url, true, true);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            URL
	 * @param xml
	 *            XML
	 * @return 返回结果
	 */
	public static String post(String url, String xml) {
		Assert.hasText(url, "[Assertion failed] - url must have text; it must not be null, empty, or blank");

		return post(url, null, new StringEntity(xml, ContentType.parse("UTF-8")));
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	public static String post(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url, "[Assertion failed] - url must have text; it must not be null, empty, or blank");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<>();
			if (parameterMap != null) {
				for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			return post(url, null, new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            URL
	 * @param header
	 *            Header
	 * @param entity
	 *            HttpEntity
	 * @return 返回结果
	 */
	public static String post(String url, Header header, HttpEntity entity) {
		return post(url, header, entity, String.class);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            URL
	 * @param header
	 *            Header
	 * @param entity
	 *            HttpEntity
	 * @param resultType
	 *            返回结果类型
	 * @return 返回结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T post(String url, Header header, HttpEntity entity, Class<T> resultType) {
		Assert.hasText(url, "[Assertion failed] - url must have text; it must not be null, empty, or blank");
		Assert.notNull(resultType, "[Assertion failed] - resultType is required; it must not be null");

		try {
			HttpPost httpPost = new HttpPost(url);
			if (header != null) {
				httpPost.setHeader(header);
			}
			if (entity != null) {
				httpPost.setEntity(entity);
			}
			CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost);
			HttpEntity httpEntity = null;
			try {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					if (String.class.isAssignableFrom(resultType)) {
						return (T) EntityUtils.toString(httpEntity, "UTF-8");
					} else if (resultType.isArray() && byte.class.isAssignableFrom(resultType.getComponentType())) {
						return (T) EntityUtils.toByteArray(httpEntity);
					}
				}
			} finally {
				EntityUtils.consume(httpEntity);
				IOUtils.closeQuietly(httpResponse);
			}
		} catch (IOException | ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
        return null;
	}

	/**
     * GET请求
     *
     * @param url URL
     * @return 返回结果
     */
	public static String get(String url,Map<String,Object> parameterMap) {
		Assert.hasText(url, "[Assertion failed] - url must have text; it must not be null, empty, or blank");

		String result = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<>();
			if (parameterMap != null) {
				for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs)));
			CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
			try {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					result = EntityUtils.toString(httpEntity);
					EntityUtils.consume(httpEntity);
				}
			} finally {
				IOUtils.closeQuietly(httpResponse);
			}
		} catch (ParseException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
        return result;
	}

	public static String get(String url,Map<String,String> headers, Map<String, Object> parameterMap) {
		Assert.hasText(url, "[Assertion failed] - url must have text; it must not be null, empty, or blank");

		String result = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<>();
			if (parameterMap != null) {
				for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8)));
			if (headers != null&& !headers.isEmpty()) {
				for (String key:headers.keySet()) {
					httpGet.setHeader(key,headers.get(key));
				}
			}
			CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
			try {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					result = EntityUtils.toString(httpEntity);
					EntityUtils.consume(httpEntity);
				}
			} finally {
				IOUtils.closeQuietly(httpResponse);
			}
		} catch (ParseException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}

	public static String get(String url) {
		Assert.hasText(url, "[Assertion failed] - url must have text; it must not be null, empty, or blank");

		String result = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
			try {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					result = EntityUtils.toString(httpEntity);
					EntityUtils.consume(httpEntity);
				}
			} finally {
				IOUtils.closeQuietly(httpResponse);
			}
		} catch (ParseException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}


	public static String postBody(String url, Map<String,Object> parameterMap) {
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			if(parameterMap!=null&& !parameterMap.isEmpty()){
				httpPost.setEntity(new StringEntity(JsonUtils.toJson(parameterMap), ContentType.parse("utf-8")));
			}

			CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost);
			HttpEntity httpEntity = null;
			try {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toString(httpEntity, "UTF-8") ;
				}
			} catch (ParseException e) {
                throw new RuntimeException(e);
            } finally {
				EntityUtils.consume(httpEntity);
				IOUtils.closeQuietly(httpResponse);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String postJson(String url, String json) {
		Assert.hasText(url,"");

		String result = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(json,"utf-8");//解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			try (CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost)) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					result = EntityUtils.toString(httpEntity);
					EntityUtils.consume(httpEntity);
				}
			}
		} catch (ParseException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}
}