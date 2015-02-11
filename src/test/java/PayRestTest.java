import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class PayRestTest {
	private Map<String, String> map;
	private RequestParameter parameter;
	private String url = "http://127.0.0.1:12912/pay/";
	private String url2 = "http://127.0.0.1:12911/pay/";
	private String url3 = "http://192.168.179.133:12911/pay/";

	@Before
	public void init() {
		parameter = new RequestParameter();
		parameter.setEncoding("utf-8");
		parameter.setContentType("application/json;charset=utf-8");
		map = new HashMap<String, String>();
		map.put("merchantid", "jrj_ylb");
		map.put("password", "123");
		map.put("merchantbizid", "8292151262776040");
	}

	@Test
	public void testDoApplyPay() {
		map.put("merchantbizid", "" + System.currentTimeMillis());
		map.put("tradetype", "1");
		map.put("tradeexplan", "代付5");
		map.put("todepositacct", "6226200103289030");
		map.put("todepositacctname", "臧云飞5");
		map.put("tosubbankname", "民生银行");
		map.put("bankno", "MSYH");
		map.put("applymoney", "0.05");
		map.put("paychannel", "b2e_chinapay");

		String resMes = getMessage(url3 + "apply");
		Assert.assertTrue(resMes.indexOf("\"retcode\":0") != -1);
	}

	private String getMessage(String url) {
		String content = JSON.toJSONString(map);

		String str = Http421Tookit.httpRequest(url, content, "post", null);
		System.out.println(str);
		return str;
	}

	@Test
	public void testQueryApplyPay() {
		map.put("applyid", "201312170000002");
		String resMes = getMessage(url + "query");
		Assert.assertTrue(resMes.indexOf("\"retcode\":0") != -1);
	}

	@Test
	public void testQueryApplyPayList() {
		map.put("begintime", "2013-05-01 12:00:00");
		map.put("endtime", "2014-06-21 12:00:00");
		map.put("paystatus", "0");
		map.put("tradetype", "1");
		map.put("size", "10");
		String resMes = getMessage(url + "queryrange");
		Assert.assertTrue(resMes.indexOf("\"retcode\":0") != -1);
	}

}
