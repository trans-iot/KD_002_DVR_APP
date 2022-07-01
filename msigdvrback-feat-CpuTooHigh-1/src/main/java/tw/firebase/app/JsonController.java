package tw.firebase.app;

import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tw.msigDvrBack.common.BaseController;
import tw.spring.ComLogger;
import tw.spring.MyContext;

@Controller
@RequestMapping("/json")
public class JsonController extends BaseController {
	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private JsonService jsonService;
	

	/**
	 * firebase推撥
	 * 
	 */
	@RequestMapping(value = "/JobPush", method = RequestMethod.GET)
	public ResponseEntity<String> executeJobPush() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = jsonService.executeJobPush();
			Map map = jsonService.executePrintIdLog();
			logger.info("id log ====> "+ map.get("id"));
			jsonObject.put("id", map.get("id"));
//			System.out.println("id log ====> "+ map.get("id"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonObject.put("err_cde", "999");
			jsonObject.put("err_msg", e.getMessage());
		}
		logger.info(jsonObject.toString());

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(jsonObject.toString(), responseHeaders, HttpStatus.OK);
	}
	/**
	 * firebase推撥
	 * 
	 */
	@RequestMapping(value = "/JobGfbDeviceGroup", method = RequestMethod.GET)
	public ResponseEntity<String> executeJobGfbDeviceGroup() {
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = jsonService.executeJobGfbDeviceGroup();
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonObject.put("err_cde", "999");
			jsonObject.put("err_msg", e.getMessage());
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(jsonObject.toString(), responseHeaders, HttpStatus.OK);
	}
	/**
	 * @description 取得版本資訊
	 * @param
	 * @return JSONObject
	 * 
	 * @author Nick
	 * @since 2018/12/05
	 * 
	 */
	@RequestMapping(value = "/getVersion", method = RequestMethod.GET)
	public ResponseEntity<String> getVersion() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = jsonService.getVersion();
		} catch (Exception e) {
			logger.error("API Get Version Error =>" + e.getMessage());
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(jsonObject.toString(), responseHeaders, HttpStatus.OK);
	}
}
