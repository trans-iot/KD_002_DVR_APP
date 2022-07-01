package tw.msigDvrBack.common;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.codehaus.jettison.json.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.*;

import tw.spring.*;
import tw.util.*;

/**
 * 
 * 
 * CommonController.java
 * @author gary
 * @since 2013/1/17
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController
{
	public static final String ERR_CDE = "errCde";
	public static final String ERR_MSG = "errMsg";
	
	private static final String UPLOAD_VIEW = "system/upload";
	@ComLogger
	private Logger logger;
	
	@Value("#{propertyConfigurer['upload.path']}")
	private String absUploadPath;
	
	private String unknownErrorDscr = "upload.unknown.error";
	
	@Autowired
	private MyContext myContext;
	
	/**
	 * (多檔)上傳到web server固定的位置
	 * <pre>
	 * Method Name : upload
	 * Description : 
	 * </pre>
	 * @since 2013/1/17
	 * @author gary 
	 *
	 * @param multiReq
	 * @return ModelAndView
	 * @throws Exception ModelAndView
	 */
//	@RequestMapping("/upload")
//	public ModelAndView upload(MultipartRequest multiReq) throws Exception {
//		logger.debug("this.relativeUploadPath: {}", this.absUploadPath);
//		
//		JSONObject json = new JSONObject();
//		MultiValueMap<String, MultipartFile> fileMap = multiReq.getMultiFileMap();
//		File parentFile = new File(this.absUploadPath);
//		ModelAndView mav = new ModelAndView(UPLOAD_VIEW);
//		try {
//			logger.debug("upload.parentFile: {}", parentFile.getCanonicalPath().replace("\\", "/"));
//			JSONObject pathInfo = new JSONObject();
//			for (Map.Entry<String, List<MultipartFile>> elem : fileMap.entrySet()) {
//				String fieldName = elem.getKey();
//				List<MultipartFile> files = elem.getValue();
//				JSONArray array = new JSONArray();
//				for (MultipartFile mfile : files) {
//					String realFname = mfile.getOriginalFilename();
//					logger.debug("upload.realFname: {}", realFname);
//					File file = new File(parentFile, realFname);
//					FileUtils.writeByteArrayToFile(file, mfile.getBytes());
//					String absPath = file.getCanonicalPath().replace("\\", "/");
//						
//					logger.debug("uploaded file: " + absPath);
//					array.put(absPath);
//				}
//				pathInfo.put(fieldName, array);
//			}
//			json.put("pathInfo", pathInfo).put(ERR_CDE, "00").put(ERR_MSG, "");
//			logger.debug("upload.allAbsPath: " + json);
//		} catch(Exception e) {
//			logger.debug("upload.exception: {}", ExceptionUtils.getFullStackTrace(e));
//			String errMsg = ExceptionUtils.getErrorMassage(e, myContext.getMessage(unknownErrorDscr));
//			json.put(ERR_CDE, "99");
//			json.put(ERR_MSG, errMsg);
//		}
//		mav.addObject("pathInfo", json.toString().replace("'", "\\'"));
//		return mav;
//	}
}
