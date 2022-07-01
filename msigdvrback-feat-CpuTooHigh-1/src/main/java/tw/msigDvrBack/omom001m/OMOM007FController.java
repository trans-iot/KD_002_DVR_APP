/**
 *  營運管理 - 營運管理
 *  歡迎頁面維護
 *  
 *  @since: 1.0 
 *  @author: Bob
 **/
package tw.msigDvrBack.omom001m;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tw.msigDvrBack.common.BaseController;
import tw.spring.ComLogger;
import tw.spring.MyContext;

/**
 * 長期未使用DVR的客戶清單
 * OMOM007F_OMOM007F
 * 
 * @author mingkun
 * @since 2020/06/12
 */
@Controller
@RequestMapping("/OMOM007F")
public class OMOM007FController extends BaseController
{
	private final static String VIEW = "OMOM001M/OMOM007F";

	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private OMOM007FService omom007FService;

	private OMOM007FQueryForm queryForm;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * <pre>
	 * Method Name : initQueryForm
	 * Description : 查詢頁面form
	 * </pre>
	 * @since 2020/06/12
	 * @author mingkun
	 *
	 * @return OMOM007FQueryForm
	 */
	@ModelAttribute("queryForm")
	public OMOM007FQueryForm initQueryForm() {
		if (this.queryForm != null) {
			return this.queryForm;
		}
		return new OMOM007FQueryForm();
	}

	/**
	 *
	 * <pre>
	 * Method Name : index
	 * Description :
	 * </pre>
	 * @since 2020/06/12
	 * @author mingkun
	 *
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView(VIEW);
		this.queryForm = null;
		this.queryForm = initQueryForm();
		List<Map<String, Object>> unUsedDaysList = omom007FService.getUnUsedDaysList();
		mav.addObject("unUsedDaysList", unUsedDaysList);
		mav.addObject("queryForm", this.queryForm);
		mav.addObject("totalCount", new Integer(0));
		return mav;
	}

	/**
	 *
	 * <pre>
	 * Method Name : query
	 * Description :
	 * </pre>
	 * @since 2020/06/12
	 * @author mingkun
	 *
	 * @param queryForm
	 * @return ModelAndView
	 */
	@RequestMapping("/query")
	public ModelAndView query(@ModelAttribute("queryForm") OMOM007FQueryForm queryForm) {
		ModelAndView mav = new ModelAndView(VIEW);
		queryForm.setQueryClicked("true");
		List<Map<String, Object>> totalList = omom007FService.getTotalList(queryForm);
		Map<String, Object> map = totalList.get(0);
		if ("00".equals(String.valueOf(map.get("errCode")))) {
			totalList.remove(0);
			List<Map<String, Object>> realList = omom007FService.getRealList(totalList);
			List<Map<String, Object>> queryList = omom007FService.queryList(queryForm, realList);

			long totalCount = realList.size();
			String checkPage = checkPage(queryForm.getPages(), queryForm.getPerPageNum(), totalCount);
			queryForm.setPages(checkPage);
			mav.addObject("list", queryList);
			mav.addObject("totalCount", totalCount);
			logger.debug("list: {}", queryList);
			logger.debug("list size: {}", totalCount);
		} else {
			String errMsg = String.valueOf(map.get("errMsg"));
			mav.addObject("errMsg", errMsg);
		}

		List<Map<String, Object>> unUsedDaysList = omom007FService.getUnUsedDaysList();
		mav.addObject("unUsedDaysList", unUsedDaysList);
		return mav;
	}

	@RequestMapping("/doExport")
	public ModelAndView doExport(OMOM007FQueryForm queryForm,HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		logger.debug("doExport => bean:{};", queryForm);

		String message = "";
		String fileName;
		try {
			List<Map<String, Object>> totalList = omom007FService.getTotalList(queryForm);
			Map<String, Object> map = totalList.get(0);
			if ("00".equals(String.valueOf(map.get("errCode")))) {
				totalList.remove(0);
				List<Map<String, Object>> realList = omom007FService.getRealList(totalList);
				fileName = myContext.getMessage("omom007f.exportName") + "_" + simpleDateFormat.format(new Date()) + ".xlsx";
				XSSFWorkbook wb = omom007FService.getExcelWorkbook(request, realList);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

				ServletOutputStream sos = response.getOutputStream();
				wb.write(sos);
				sos.close();
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.debug("doExport catch exception : {}{} ",message, e);
		}

		return null;
	}

}