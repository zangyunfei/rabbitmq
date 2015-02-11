import java.io.IOException;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class Http421Tookit {

	public static String CHARSET_ENCODING = "UTF-8";
	// USER_AGENT="Mozilla/4.0 (compatible; MSIE 6.0; Win32)";//ie6
	public static String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Win32)";// ie7

	private static DefaultHttpClient getDefaultHttpClient(final String charset) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				USER_AGENT);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? CHARSET_ENCODING : charset);

		// 浏览器兼容性
		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 超时时间
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				8000);
		// 定义重试策略
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);

		return httpclient;
	}

	/**
	 * 访问https的网站
	 * 
	 * @param httpclient
	 */
	private static void enableSSL(DefaultHttpClient httpclient) {
		// 调用ssl
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", sf, 443);
			httpclient.getConnectionManager().getSchemeRegistry()
					.register(https);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写验证方法，取消检测ssl
	 */
	private static TrustManager truseAllManager = new X509TrustManager() {

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	};

	/**
	 * 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
	 */
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试3次
			if (executionCount >= 3) {
				// 如果连接次数超过了最大值则停止重试
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// 如果服务器连接失败重试
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// 不要重试ssl连接异常
				return false;
			}
			HttpRequest request = (HttpRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// 重试，如果请求是考虑幂等
				return true;
			}
			return false;
		}
	};

	/**
	 * 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，
	 * 解决了对连接的释放管理
	 */
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_ENCODING
						: EntityUtils.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};

	/**
	 * 使用post方法获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @return
	 */
	public static String post(String url, List<NameValuePair> paramsList) {
		return httpRequest(url, paramsList, "POST", null);
	}

	/**
	 * 使用post方法并且通过代理获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @param proxy
	 * @return
	 */
	public static String post(String url, List<NameValuePair> paramsList,
			HttpHost proxy) {
		return httpRequest(url, paramsList, "POST", proxy);
	}

	/**
	 * 使用get方法获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @return
	 */
	public static String get(String url, List<NameValuePair> paramsList) {
		return httpRequest(url, paramsList, "GET", null);
	}

	/**
	 * 使用get方法并且通过代理获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @param proxy
	 * @return
	 */
	public static String get(String url, List<NameValuePair> paramsList,
			HttpHost proxy) {
		return httpRequest(url, paramsList, "GET", proxy);
	}

	/**
	 * 提交数据到服务器
	 * 
	 * @param url
	 * @param params
	 * @param authenticated
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpRequest(String url,
			List<NameValuePair> paramsList, String method, HttpHost proxy) {
		String responseStr = null;
		// 判断输入的值是是否为空
		if (null == url || "".equals(url)) {
			return null;
		}

		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);

		// 判断是否是https请求
		if (url.startsWith("https")) {
			enableSSL(httpclient);
		}
		String formatParams = null;
		// 将参数进行utf-8编码
		if (null != paramsList && paramsList.size() > 0) {
			formatParams = URLEncodedUtils.format(paramsList, CHARSET_ENCODING);
		}
		// 如果代理对象不为空则设置代理
		if (null != proxy) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}
		try {
			// 如果方法为Get
			if ("GET".equalsIgnoreCase(method)) {
				if (formatParams != null) {
					url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams)
							: (url.substring(0, url.indexOf("?") + 1) + formatParams);
				}
				HttpGet hg = new HttpGet(url);
				responseStr = httpclient.execute(hg, responseHandler);

				// 如果方法为Post
			} else if ("POST".equalsIgnoreCase(method)) {
				HttpPost hp = new HttpPost(url);
				if (formatParams != null) {
					StringEntity entity = new StringEntity(formatParams);
					entity.setContentType("application/x-www-form-urlencoded");
					hp.setEntity(entity);
				}
				responseStr = httpclient.execute(hp, responseHandler);

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseStr;
	}

	public static String httpRequest(String url, String json, String method,
			HttpHost proxy) {
		String responseStr = null;
		// 判断输入的值是是否为空
		if (null == url || "".equals(url)) {
			return null;
		}

		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);

		// 判断是否是https请求
		if (url.startsWith("https")) {
			enableSSL(httpclient);
		}
		String formatParams = json;
		// 如果代理对象不为空则设置代理
		if (null != proxy) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}
		try {
			// 如果方法为Get
			if ("GET".equalsIgnoreCase(method)) {
				if (formatParams != null) {
					url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams)
							: (url.substring(0, url.indexOf("?") + 1) + formatParams);
				}
				HttpGet hg = new HttpGet(url);
				responseStr = httpclient.execute(hg, responseHandler);

				// 如果方法为Post
			} else if ("POST".equalsIgnoreCase(method)) {
				HttpPost hp = new HttpPost(url);
				if (formatParams != null) {
					StringEntity entity = new StringEntity(formatParams);
					entity.setContentType("application/x-www-form-urlencoded");
					hp.setEntity(entity);
				}
				responseStr = httpclient.execute(hp, responseHandler);

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseStr;
	}

	/**
	 * 将相关文件和参数提交到相关服务器
	 * 
	 * @param url
	 * @param fileMap
	 * @param StringMap
	 * @return
	 */

	/**
	 * 将相关文件和参数提交到相关服务器
	 * 
	 * @param url
	 * @param fileMap
	 * @param StringMap
	 * @return
	 */

	/**
	 * 获取网络文件的字节数组
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static byte[] getUrlFileBytes(String url)
			throws ClientProtocolException, IOException {

		byte[] bytes = null;
		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);
		// 获取url里面的信息
		HttpGet hg = new HttpGet(url);
		HttpResponse hr = httpclient.execute(hg);
		bytes = EntityUtils.toByteArray(hr.getEntity());
		// 转换内容为字节
		return bytes;
	}

	/**
	 * 获取图片的字节数组
	 * 
	 * @createTime 2011-11-24
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static byte[] getImg(String url) throws ClientProtocolException,
			IOException {
		byte[] bytes = null;
		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);
		// 获取url里面的信息
		HttpGet hg = new HttpGet(url);
		HttpResponse hr = httpclient.execute(hg);
		bytes = EntityUtils.toByteArray(hr.getEntity());
		// 转换内容为字节
		return bytes;
	}

	public static void main(String[] args) throws URISyntaxException,
			ClientProtocolException, IOException {
		String url = "http://www.baidu.com/";
		String str = get(url, null);
		System.out.println(str);

	}
}
