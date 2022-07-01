package tw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;
import org.owasp.encoder.Encode;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CallWebApi {

	private static final Logger logger = LoggerFactory.getLogger(CallWebApi.class);
	
	/**
	 * http client timeout設3 秒 容許 timeOut 1次
	 * 
	 * @param content
	 * @param url
	 * @param contentType
	 * @param method
	 * @param count
	 * @return
	 * @throws IOException
	 */
	public static String callHttps(String content, String url, String contentType, String method, int count,
			String encode) throws IOException {
		// String resultXml = callHttps(xml, url, "application/xml", "POST", 0,
		// "utf-8");
		// 取得呼叫此method 的method name
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement parent = stacktrace[2];// maybe this number needs to
													// be corrected
		String methodName = parent.getMethodName();

		if (count == 3) {
			return "error";
		}
		OutputStream os = null;
		InputStream inputStream = null ;
		JSONObject errorJson = new JSONObject();
		try {
			errorJson = new JSONObject();
			
			//edit by Marks 2020/10/08 Privacy Violation
			content = Encode.forHtmlContent(content);
			
			logger.info("callHttps start method===========>" + methodName);
			HttpURLConnection httpcon = (HttpURLConnection) ((new URL(url).openConnection()));
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", contentType);
			httpcon.setConnectTimeout(10000);
			httpcon.setReadTimeout(10000);
			httpcon.setRequestMethod(method);
			httpcon.connect();
			byte[] outputBytes = content.getBytes("UTF-8");
			os = httpcon.getOutputStream();
			os.write(outputBytes);
			os.close();

			StringBuilder sb = new StringBuilder();
			int HttpResult = httpcon.getResponseCode();
			logger.info("HttpResultCode=======>{}"+HttpResult);
			inputStream = httpcon.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encode);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line = ESAPI.encoder().encodeForHTML(br.readLine());
			while (line != null) {
				sb.append(line + "\n");
				line = ESAPI.encoder().encodeForHTML(br.readLine());
			}

			br.close();
			inputStreamReader.close();
			inputStream.close();
			// 回傳結果
//			System.out.println(sb.toString());

			if (HttpResult == HttpURLConnection.HTTP_OK) {
				return ESAPI.encoder().decodeForHTML(sb.toString());
			} else {
//				System.out.println("http accpet error =" + httpcon.getResponseMessage());
				errorJson.put("error", "error");
				return errorJson.toString();
			}

		} catch (SocketTimeoutException e) {
			// 增加暫停一秒機制 Aden
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				logger.error("sleep ERROR=========>{}"+e1.getMessage());
			}
			// time out 遞迴
//			System.out.println("method=" + method + "  error=" + e.getMessage());
//			System.out.println("methodName=" + methodName + "   再一次");
			count++;
			callHttps(content, url, contentType, method, count, encode);

		} catch (Exception e) {
			logger.error("==========call HTTP ERROR===========>{}"+e.getMessage());
		} finally {
			if (os != null) {
				os.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		errorJson.put("error", "error");
		return errorJson.toString();
	}

	public static String getUserStatusInfo(String content, String url, String contentType, String method, int count,
			String encode) throws IOException {

		return callHttps(content, url, contentType, method, count, encode);
	}
}