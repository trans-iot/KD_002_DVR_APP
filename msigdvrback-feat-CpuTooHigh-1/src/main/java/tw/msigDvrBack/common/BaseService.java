package tw.msigDvrBack.common;

import java.lang.reflect.Type;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Configuration
@Lazy
@Service
public class BaseService {
    //json转对象
    public static <T> T parseJson2(String json, Class<T> cls){
        return new Gson().fromJson(json, cls);
    }
    public static Map<String, Object> fromJson(String json) {
        return fromJson(json, Map.class);
    }
    public static String toJson(Object src) {
        Gson gson = new Gson();
        return gson.toJson(src);
    }
    public static <T> T fromJson(String json, Type typeOfT) {
        Gson gson = new Gson();
        return (T) gson.fromJson(json, typeOfT);
    }
    //值为空也序列化
    public static String toJsonWithNull(Object object){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(object);
    }
    
    /**
     * @description SSRF弱掃用
     * 
     * 2020/08/25 
     * Marks
     * @param url
     * @return
     */
    public boolean urlValid(String url) throws Exception {
    	try {
    		if(StringUtils.isNotBlank(url)) {
	    		int idx = url.indexOf(":", 0);
	    		if(idx != -1) {
	    			url = url.substring(0, idx);
	        		if(url.contains("http") || url.contains("https")) {
	        			return true;
	        		}
	    		}
    		}
    	} catch(Exception e) {
    		throw new Exception("==========Invalid Url Pattern============>{}"+ e.getMessage())  ;
    	}
    	return false;
    }
}
