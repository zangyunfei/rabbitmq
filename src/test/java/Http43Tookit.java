
/**
 * 基于 httpclient 4.3.1版本的 http工具类
 * 
 * @author mcSui
 * 
 */
public class Http43Tookit {/*
							 * 
							 * private static final CloseableHttpClient
							 * httpClient; public static final String CHARSET =
							 * "UTF-8";
							 * 
							 * static { RequestConfig config =
							 * RequestConfig.custom().setConnectTimeout(8000)
							 * .setSocketTimeout
							 * (5000).setConnectionRequestTimeout(5000)
							 * .build(); httpClient =
							 * HttpClientBuilder.create().
							 * setDefaultRequestConfig(config) .build(); }
							 * 
							 * public static String doGet(String url,
							 * Map<String, String> params) { return doGet(url,
							 * params, CHARSET); }
							 * 
							 * public static String doPost(String url,
							 * Map<String, String> params) { return doPost(url,
							 * params, CHARSET); }
							 */
	/**
	 * HTTP Get 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 */
	/*
	 * public static String doGet(String url, Map<String, String> params, String
	 * charset) { if (StringUtils.isBlank(url)) { return null; } try { if
	 * (params != null && !params.isEmpty()) { List<NameValuePair> pairs = new
	 * ArrayList<NameValuePair>( params.size()); for (Map.Entry<String, String>
	 * entry : params.entrySet()) { String value = entry.getValue(); if (value
	 * != null) { pairs.add(new BasicNameValuePair(entry.getKey(), value)); } }
	 * url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs,
	 * charset)); } HttpGet httpGet = new HttpGet(url); CloseableHttpResponse
	 * response = httpClient.execute(httpGet); int statusCode =
	 * response.getStatusLine().getStatusCode(); if (statusCode != 200) {
	 * httpGet.abort(); throw new
	 * RuntimeException("HttpClient,error status code :" + statusCode); }
	 * HttpEntity entity = response.getEntity(); String result = null; if
	 * (entity != null) { result = EntityUtils.toString(entity, "utf-8"); }
	 * EntityUtils.consume(entity); response.close(); return result; } catch
	 * (Exception e) { e.printStackTrace(); } return null; }
	 * 
	 * public static String post(String url, String json) {
	 * 
	 * HttpPost post = new HttpPost(url); String result = null; try {
	 * StringEntity s = new StringEntity(json); s.setContentEncoding("UTF-8");
	 * s.setContentType("application/json"); post.setEntity(s);
	 * 
	 * HttpResponse res = httpClient.execute(post); if
	 * (res.getStatusLine().getStatusCode() == 200) { HttpEntity entity =
	 * res.getEntity(); if (entity != null) { result =
	 * EntityUtils.toString(entity, "utf-8"); } EntityUtils.consume(entity);
	 * return result; } } catch (Exception e) { e.printStackTrace(); throw new
	 * RuntimeException(e); } return null; }
	 *//**
	 * HTTP Post 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 */
	/*
	 * public static String doPost(String url, Map<String, String> params,
	 * String charset) { if (StringUtils.isBlank(url)) { return null; } try {
	 * List<NameValuePair> pairs = null; if (params != null &&
	 * !params.isEmpty()) { pairs = new ArrayList<NameValuePair>(params.size());
	 * for (Map.Entry<String, String> entry : params.entrySet()) { String value
	 * = entry.getValue(); if (value != null) { pairs.add(new
	 * BasicNameValuePair(entry.getKey(), value)); } } } HttpPost httpPost = new
	 * HttpPost(url); if (pairs != null && pairs.size() > 0) {
	 * httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET)); }
	 * CloseableHttpResponse response = httpClient.execute(httpPost); int
	 * statusCode = response.getStatusLine().getStatusCode(); if (statusCode !=
	 * 200) { httpPost.abort(); throw new
	 * RuntimeException("HttpClient,error status code :" + statusCode); }
	 * HttpEntity entity = response.getEntity(); String result = null; if
	 * (entity != null) { result = EntityUtils.toString(entity, "utf-8"); }
	 * EntityUtils.consume(entity); response.close(); return result; } catch
	 * (Exception e) { e.printStackTrace(); } return null; }
	 * 
	 * public static void main(String[] args) { String getData =
	 * doGet("http://www.oschina.net/", null); System.out.println(getData);
	 * System.out.println("----------------------分割线-----------------------");
	 * String postData = doPost("http://www.oschina.net/", null);
	 * System.out.println(postData); }
	 */
}