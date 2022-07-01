/**
 *  保單申請資料查詢
 *  
 * @author Bob
 * @since 2018/10/22
 **/
package tw.msigDvrBack.omom001m;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.manual.TbCustCarManualMapper;
import tw.msigDvrBack.manual.TbCustomerManualMapper;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.manual.TbNewsManualMapper;
import tw.msigDvrBack.manual.TbNewsUserManualMapper;
import tw.msigDvrBack.manual.VwNewsuserCustcarManualMapper;
import tw.msigDvrBack.persistence.TbCustCar;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.TbNews;
import tw.msigDvrBack.persistence.TbNewsUser;
import tw.msigDvrBack.persistence.VwNewsuserCustcar;
import tw.mybatis.Page;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CwDateUtils;
import tw.util.PageUtils;

@Service
public class OMOM004FService extends BaseService {

	@Autowired
	private TbLookupCdeManualMapper tbLookupCdeManualMapper;

	@Autowired
	private TbNewsManualMapper tbNewsManualMapper;

	@Autowired
	private TbNewsUserManualMapper tbNewsUserManualMapper;

	@Autowired
	private VwNewsuserCustcarManualMapper vwNewsuserCustcarManualMapper;

	@Autowired
	private TbCustomerManualMapper tbCustomerManualMapper;

	@Autowired
	private TbCustCarManualMapper tbCustCarManualMapper;

	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	/**
	 * 
	 * <pre>
	 * Method Name : queryList
	 * Description :
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param params
	 * @return List<Map<String,Object>>
	 * @throws ParseException
	 */
	public List<TbNews> queryList(OMOM004FQueryForm form) throws Exception {
		Map<String, Object> map = fillupCri(form);

		int perPage = PageUtils.getPageInt(form.getPerPageNum());
		int curPage = PageUtils.getPageInt(form.getPages());

		if (perPage > 0) {
			Page page = new Page(curPage, perPage);
			map.put("perPage", perPage);
			map.put("curPage", curPage);
			map.put("begin", page.getBegin());
			map.put("page", "page");
		}

		// order by
		String orderByClause = form.getOrderByClause();
		if (StringUtils.isNotBlank(orderByClause)) {
			map.put("orderByClause", orderByClause);
		} else {
			// example.setOrderByClause("woNbr");
		}

		List<TbNews> list = tbNewsManualMapper.selectByExample(map);

		return list;
	}

	public String checkPage(String pages, String perPage, int totalCount) {
		int ip = 1;
		int ipp = 10;
		if (StringUtils.isNotBlank(pages)) {
			ip = Integer.parseInt(pages);
		}
		if (StringUtils.isNotBlank(perPage)) {
			ipp = Integer.parseInt(perPage);
		}

		int rp = ip - 1;
		if (rp * ipp + 1 > totalCount) {
			int cp = ip == 1 ? 1 : rp;
			return String.valueOf(cp);
		} else {
			return pages;
		}
	}

	/**
	 * 
	 * <pre>
	 * Method Name : countTotal
	 * Description :
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param params
	 * @return int
	 * @throws ParseException
	 */
	public Long countTotal(OMOM004FQueryForm form) throws ParseException {
		Map<String, Object> map = fillupCri(form);
		Long size = tbNewsManualMapper.countByExample(map);
		return size;
	}

	/**
	 * 預備查詢條件，提供模糊查詢
	 * 
	 * @throws ParseException
	 */
	private Map<String,Object> fillupCri(OMOM004FQueryForm form) throws ParseException {
		String msgClass = form.getMsgClass();
		String pushStatus = form.getPushStatus();
		String title = form.getTitle();
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(msgClass)) {
			map.put("msgClass", msgClass);
		}
		if (StringUtils.isNotBlank(pushStatus)) {
			map.put("pushStatus", pushStatus);
		}
		if (StringUtils.isNotBlank(title)) {
			if (StringUtils.contains(title, "%")) {
				map.put("titleLike", title);
			} else {
				map.put("title", title);
			}
		}
		return map;

	}

	// 取得帳號狀態下拉選單
	public List<TbLookupCde> getDscrByType(String lkupType) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", lkupType);
		List<TbLookupCde> list = tbLookupCdeManualMapper.selectByExample(queryMap);
		return list;
	}

	public OMOM004FModForm queryByKey(Integer seqNo) {
		OMOM004FModForm modForm = new OMOM004FModForm();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("seqNo", seqNo);
		// 查詢主檔
		List<TbNews> tbNewsList = this.tbNewsManualMapper.selectByExample(queryMap);
		logger.debug("TbNews :{}", ToStringBuilder.reflectionToString(seqNo));

		if (tbNewsList == null) {
			logger.info("not found TbNews({})", seqNo);
		} else {
			if(tbNewsList.size() > 0 ) {
				modForm = (this.transMod(tbNewsList.get(0)));
			}
		}
		
		
		Map<String,Object> queryMap2 = new HashMap<String,Object>();
		queryMap2.put("lookupType", Constants.APPLY_STATUS);
		List<TbLookupCde> list = tbLookupCdeManualMapper.selectByExample(queryMap2);
		for (TbLookupCde tbLookupCde : list) {
			if (tbLookupCde.getLookupCde().equals(modForm.getPushStatus())) {
				modForm.setPushStatusDscr(tbLookupCde.getDscr());
			}
		}
		return modForm;
	}

	private OMOM004FModForm transMod(TbNews obj) {

		OMOM004FModForm form = new OMOM004FModForm();
		form.setSeqNo(obj.getSeqNo());
		form.setMsgClass(obj.getMsgClass());
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		SimpleDateFormat format2 = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
		form.setPrePushTime(format.format(obj.getPrePushTime()));
		form.setPrePushTimeDscr(format2.format(obj.getPrePushTime()));
		form.setTitle(obj.getTitle());
		form.setContent(obj.getContent());
		form.setLinkUrl(obj.getLinkUrl());
		form.setCsPhone(obj.getCsPhone());
		form.setPushStatus(obj.getPushStatus());
		SimpleDateFormat format3 = new SimpleDateFormat("yyyy/MM/dd");
		if (obj.getPushTime() != null) {
			form.setPushTime(format2.format(obj.getPushTime()));
		}
		if (obj.getEffDate() != null) {
			form.setEffDate(format3.format(obj.getEffDate()));
		}
		if (obj.getEndDate() != null) {
			form.setEndDate(format3.format(obj.getEndDate()));
		}
		form.setUserstamp(obj.getUserstamp());
		form.setDatestamp(obj.getDatestamp());
		return form;
	}

	public int updateData(OMOM004FModForm modForm) throws Exception {
		int updateCount = 0;
		TbNews record = this.transformTbCustomer(modForm);
		TbNews tbNews = tbNewsManualMapper.selectByPrimaryKey(modForm.getSeqNo());
		tbNews.setMsgClass(record.getMsgClass());
		tbNews.setPrePushTime(record.getPrePushTime());
		tbNews.setContent(record.getContent());
		tbNews.setTitle(record.getTitle());
		tbNews.setCsPhone(record.getCsPhone());
		tbNews.setLinkUrl(record.getLinkUrl());
		tbNews.setEffDate(record.getEffDate());
		tbNews.setEndDate(record.getEndDate());
		updateCount = tbNewsManualMapper.updateByPrimaryKey(tbNews);
		return updateCount;
	}

	private TbNews transformTbCustomer(OMOM004FModForm form) throws ParseException {
		TbNews tbNews = new TbNews();
		tbNews.setSeqNo(form.getSeqNo());
		tbNews.setMsgClass(form.getMsgClass());
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		SimpleDateFormat format3 = new SimpleDateFormat("yyyy/MM/dd");
		tbNews.setPrePushTime(format.parse(form.getPrePushTime()));
		tbNews.setTitle(form.getTitle());
		tbNews.setContent(form.getContent());
		tbNews.setLinkUrl(form.getLinkUrl());
		tbNews.setCsPhone(form.getCsPhone());
		tbNews.setPushStatus(form.getPushStatus());
		if (StringUtils.isNotBlank(form.getEffDate())) {
			tbNews.setEffDate(format3.parse(form.getEffDate()));
		}
		if (StringUtils.isNotBlank(form.getEndDate())) {
			tbNews.setEndDate(format3.parse(form.getEndDate()));
		}
		tbNews.setUserstamp(form.getUserstamp());
		tbNews.setDatestamp(form.getDatestamp());
		return tbNews;
	}

	public int deleteByKey(Integer deleteSeqNo) {
		int countTbNews = tbNewsManualMapper.deleteByPrimaryKey(deleteSeqNo);
		if (countTbNews > 0) {
			logger.debug("CwTbSsoModProf  Pkey => seqNo  : " + deleteSeqNo);
		}
		int countTbNewsUser = tbNewsUserManualMapper.deleteByPrimaryKey(deleteSeqNo);
		if (countTbNewsUser > 0) {
			logger.debug("CwTbSsoModProf  Pkey => newsSeqNo  : " + deleteSeqNo);
		}
		return countTbNewsUser + countTbNews;
	}

	public List<VwNewsuserCustcar> queryVwNewsuserCustcarBySeqNo(Integer settingSeqNo, String orderByClause) {
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("newsSeqNo", settingSeqNo);
		querymap.put("orderByClause", orderByClause);
		return vwNewsuserCustcarManualMapper.selectByExample(querymap);
	}

	public List<Map<String, Object>> queryListByBatchInsertQueryForm(
			OMOM004FBatchInsertQueryForm form) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(form.getUserName())){
			if(StringUtils.contains(form.getUserName(), "%")){
				map.put("userNameLike", form.getUserName());
			}else{
				map.put("userName", form.getUserName());
			}
		}
		if(StringUtils.isNotBlank(form.getUserId())){
			if(StringUtils.contains(form.getUserId(), "%")){
				map.put("userIdLike", form.getUserId());
			}else{
				map.put("userId", form.getUserId());
			}
		}
		if(StringUtils.isNotBlank(form.getEmail())){
			if(StringUtils.contains(form.getEmail(), "%")){
				map.put("emailLike", form.getEmail());
			}else{
				map.put("email", form.getEmail());
			}
		}
		if(StringUtils.isNotBlank(form.getCarNo())){
			if(StringUtils.contains(form.getCarNo(), "%")){
				map.put("carNoLike", form.getCarNo());
			}else{
				map.put("carNo", form.getCarNo());
			}
		}
		return tbCustomerManualMapper.getCustomerAndCustCar(map);
	}

	public Integer checkUserIdExit(String checkedUserId, Integer checkedSeqNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("newsSeqNo", checkedSeqNo);
		map.put("userId", checkedUserId);
		List<TbNewsUser> tbNewsUserList = tbNewsUserManualMapper.selectByExample(map);
		return tbNewsUserList.size();
	}

	public JSONArray checkUserIdExitAll(JSONArray array, Integer checkedSeqNo) {
		JSONArray duplicatedArray = new JSONArray();
		for (Integer i = 0; i < array.length(); i++) {
			String checkedUserId = array.getString(i);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("newsSeqNo", checkedSeqNo);
			map.put("userId", checkedUserId);
			List<TbNewsUser> tbNewsUserList = tbNewsUserManualMapper.selectByExample(map);
			if (tbNewsUserList.size() > 0) {
				duplicatedArray.put(checkedUserId);
			}
		}
		return duplicatedArray;
	}

	public Integer executeBatchInsert(JSONArray array, String loginUser) throws Exception {
		Integer count = 0;

		for (Integer i = 0; i < array.length(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TbNewsUser tbNewsUser = new TbNewsUser();
			String userId = jsonObject.getString("userId");
			String seqNo = jsonObject.getString("seqNo");
			tbNewsUser.setUserId(userId);
			tbNewsUser.setCrDate(new Date());
			tbNewsUser.setCrUser(loginUser);
			tbNewsUser.setNewsSeqNo(Integer.parseInt(seqNo));
			tbNewsUser.setPushTime(null);
			tbNewsUser.setPushStatus("N");
			int insertCount = tbNewsUserManualMapper.insertSelective(tbNewsUser);
			count = count + insertCount;
		}
		return count;
	}

	public List<HashMap<String, Object>> executeFileInsert(MultipartFile multipart, String loginUser, String seqNo)
			throws Exception {
		byte[] bytes = multipart.getBytes();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
		String line = "";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		while ((line = bufferedReader.readLine()) != null) {

			String[] carNoArray = line.split(",");

			lable1: for (String carNo : carNoArray) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				HashMap<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("carNo", carNo);
				List<TbCustCar> tbCustCarList = tbCustCarManualMapper.selectByExample(queryMap);

				map.put("carNo", carNo);
				if (tbCustCarList.size() == 0) {
					map.put("result", "車號不存在");
				} else {
					TbCustCar tbCustCar = tbCustCarList.get(0);
					map.put("userId", tbCustCar.getUserId());
					Map<String,Object> querymap = new HashMap<String,Object>();
					querymap.put("newsSeqNo", Integer.parseInt(seqNo));
					querymap.put("userId", tbCustCar.getUserId());
					List<TbNewsUser> tbNewsUserList = tbNewsUserManualMapper.selectByExample(querymap);
					if (tbNewsUserList.size() > 0) {
						map.put("result", "會員編號已在名單內");
						list.add(map);
						continue lable1;
					}
					map.put("result", "OK");
					TbNewsUser tbNewsUser = new TbNewsUser();
					tbNewsUser.setNewsSeqNo(Integer.parseInt(seqNo));
					tbNewsUser.setUserId(tbCustCar.getUserId());
					tbNewsUser.setPushTime(null);
					tbNewsUser.setPushStatus("N");
					tbNewsUser.setCrDate(new Date());
					tbNewsUser.setCrUser(loginUser);
					int count = tbNewsUserManualMapper.insertSelective(tbNewsUser);
				}
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * <pre>
	 * Method Name : insertData Description : 新增
	 * 
	 * <pre>
	 * 
	 * @author Bob
	 * @since 2018/11/02
	 * @param form
	 *            OMOM004FModForm
	 * @return int
	 * @throws Exception
	 */
	public int insertData(OMOM004FModForm form) throws Exception {
		TbNews record = transformTbCustomer(form);
		int count = tbNewsManualMapper.insertSelective(record);
		logger.debug("insert TbNews total count:{}", count);
		return count;
	}

}
