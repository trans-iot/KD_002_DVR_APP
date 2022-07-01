package tw.firebase.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;


/**
 * Firebase Cloud Messaging (FCM) can be used to send messages to clients on
 * iOS, Android and Web.
 *
 * This sample uses FCM to send two types of messages to clients that are
 * subscribed to the `news` topic. One type of message is a simple notification
 * message (display message). The other is a notification message (display
 * notification) with platform specific customizations, for example, a badge is
 * added to messages that are sent to iOS devices.
 */
//@Service
public class Messaging {
	
//	@ComLogger
//	private Logger logger;

	public static String GOOGLE_KEY = "";
	public static String PROJECT_ID = "";
	private static final String URL = "https://fcm.googleapis.com/fcm/send";
	public static String FCM_SEND_ENDPOINT = "";

	public static final String MESSAGE_KEY = "message";

	/**
	 * Retrieve a valid access token that can be use to authorize requests to
	 * the FCM REST API.
	 *
	 * @return Access token.
	 * @throws IOException
	 */
	// [START retrieve_access_token]
//	private String getAccessToken() throws IOException {
//		GoogleCredential googleCredential = GoogleCredential.fromStream(new FileInputStream("D:\\service-account.json"))
//				.createScoped(Arrays.asList(SCOPES));
//		googleCredential.refreshToken();
//		return googleCredential.getAccessToken();
//	}
	// [END retrieve_access_token]

	/**
	 * Create HttpURLConnection that can be used for both retrieving and
	 * publishing.
	 *
	 * @return Base HttpURLConnection.
	 * @throws IOException
	 */
	private HttpURLConnection getConnection() throws IOException {
		// [START use_access_token]
		URL url = new URL(URL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "key=" + GOOGLE_KEY);
		httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setDoOutput(true);
		httpURLConnection.connect();
		return httpURLConnection;
		// [END use_access_token]
	}

	/**
	 * Send request to FCM message using HTTP.
	 *
	 * @param fcmMessage
	 *            Body of the HTTP request.
	 * @throws IOException
	 */
	private String sendMessage(JSONObject fcmMessage) throws IOException {
		HttpURLConnection connection = getConnection();
		OutputStream os = connection.getOutputStream();
		os.write(fcmMessage.toString().getBytes("UTF-8"));
		
		os.flush();
		os.close();
		String response;
		
		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			InputStream inputStream = connection.getInputStream();
			response = inputstreamToString(inputStream);
			inputStream.close();
//			logger.info("Message sent to Firebase for delivery, response:");
//			logger.info(response);
		} else {
			InputStream errorStream = connection.getErrorStream();
			response = inputstreamToString(errorStream);
			errorStream.close();
//			logger.info("Unable to send message to Firebase:");
//			logger.info(response);
		}
		return response;
	}

	public String sendCommonMessage(String gfbDeviceId,String msgTitle,String msgText) throws IOException {
		JSONObject notificationMessage = buildNotificationMessage(gfbDeviceId, msgTitle, msgText);
//		prettyPrint(notificationMessage);
		return sendMessage(notificationMessage);
	}

	private JSONObject buildNotificationMessage(String gfbDeviceId,String msgTitle,String msgText) {
		JSONObject jNotification = new JSONObject();
		jNotification.put("title", msgTitle);
		jNotification.put("body", msgText);
//		jNotification.addProperty("title", msgTitle);
//		jNotification.addProperty("body", msgText);

		JSONObject jMessage = new JSONObject();
//		JSONObject jData = new JSONObject();
		jMessage.put("notification", jNotification);
//		jMessage.add("data", jData);
		jMessage.put("to", gfbDeviceId);

		return jMessage;
	}

	private String inputstreamToString(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNext()) {
			stringBuilder.append(scanner.nextLine());
		}
		return stringBuilder.toString();
	}


	public String doMessage(String gfbDeviceId,String msgTitle,String msgText) throws IOException {
		return sendCommonMessage(gfbDeviceId,msgTitle,msgText);
	}

}