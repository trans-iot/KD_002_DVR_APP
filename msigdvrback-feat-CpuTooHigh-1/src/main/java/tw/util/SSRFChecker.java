package tw.util;

import org.apache.commons.net.util.SubnetUtils;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;

public class SSRFChecker {

    private static int connectTime = 5*1000;  // 设置连接超时时间5s

    /**
     * 解析url的ip，判断ip是否是内网ip，所以TTL设置为0的情况不适用。
     * url只允许https或者http，并且设置默认连接超时时间。
     * 该修复方案会主动请求重定向后的链接。最好用Hook方式获取到所有url后，进行判断，代码待续…
     *
     * @param url check的url
     * @return 安全返回true，危险返回false
     */
    public static Boolean checkSSRF(String url) {

        HttpURLConnection connection;
        String finalUrl = url;
        try {
            do {
                // 判断当前请求的URL是否是内网ip
                Boolean bRet = isInnerIPByUrl(finalUrl);
                if (bRet) {
                    return false;  // 内网ip直接return，非内网ip继续判断是否有重定向
                }

                connection = (HttpURLConnection) new URL(finalUrl).openConnection();
                connection.setInstanceFollowRedirects(false);
                connection.setUseCaches(false); // 设置为false，手动处理跳转，可以拿到每个跳转的URL
                connection.setConnectTimeout(connectTime);
                //connection.setRequestMethod("GET");
                connection.connect(); // send dns request
                int responseCode = connection.getResponseCode(); // 发起网络请求
                if (responseCode >= 300 && responseCode <=307 && responseCode != 304 && responseCode != 306) {
                    String redirectedUrl = connection.getHeaderField("Location");
                    if (null == redirectedUrl)
                        break;
                    finalUrl = redirectedUrl;
                    // System.out.println("redirected url: " + finalUrl);
                } else
                    break;
            } while (connection.getResponseCode() != HttpURLConnection.HTTP_OK);
            connection.disconnect();
        } catch (Exception e) {
            return true;  // 如果异常了，认为是安全的，防止是超时导致的异常而验证不成功。
        }
        return true; // 默认返回true
    }


    /**
     * 判断一个URL的IP是否是内网IP
     *
     * @return 如果是内网IP，返回true；非内网IP，返回false。
     */
    public static Boolean isInnerIPByUrl(String url) {
        String host = url2host(url);
        if (host.equals("")) {
            return true; // 异常URL当成内网IP等非法URL处理
        }

        String ip = host2ip(host);
        if(ip.equals("")){
            return true; // 如果域名转换为IP异常，则认为是非法URL
        }

        return isInnerIp(ip);
    }


    /**
     * 使用SubnetUtils库判断ip是否在内网网段
     *
     * @param strIP ip字符串
     * @return 如果是内网ip，返回true，否则返回false。
     */
    private static boolean isInnerIp(String strIP){

        String blackSubnetlist[] = {"10.0.0.0/8", "172.16.0.0/12", "192.168.0.0/16", "127.0.0.0/8", "0.0.0.0/32"};

        for (String subnet: blackSubnetlist) {
            SubnetUtils utils = new SubnetUtils(subnet);
            if (utils.getInfo().isInRange(strIP)) {
                return true;
            }
        }

        return false;

    }

    /**
     * host转换为IP
     * 会将各种进制的ip转为正常ip
     * 167772161转换为10.0.0.1
     * 127.0.0.1.xip.io转换为127.0.0.1
     *
     * @param host 域名host
     */
    private static String host2ip(String host) {
        try {
            InetAddress IpAddress = InetAddress.getByName(host); //  send dns request
            return IpAddress.getHostAddress();
        }
        catch (Exception e) {
            return "";
        }
    }

    /**
     * 从URL中获取host，限制为http/https协议。只支持http:// 和 https://，不支持//的http协议。
     *
     * @param url http的url
     */
    private static String url2host(String url) {
        try {
            // 使用URI，而非URL，防止被绕过。
            URI u = new URI(url);
            if (!url.startsWith("http://") && ! url.startsWith("https://")) {
                return "";
            }

            return u.getHost();

        } catch (Exception e) {
            return "";
        }

    }

}
