package tw.msigDvrBack.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.aop.DBHelper;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.manual.TbSqlManualMapper;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.TbLookupCdeKey;
import tw.util.AESUtilDmm;

@Service
public class CommonService extends BaseService {

	@Autowired
	TbLookupCdeManualMapper tbLookupCdeMapper;

	@Autowired
	private TbSqlManualMapper tbSqlManualMapper;

	@Autowired
	DBHelper dBHelper;

	private String userId;

	public String getUserId() {
		userId = dBHelper.getSessionData().getSysUserId();
		return userId;
	}

	/**
	 * 依傳入lookup_type取得lookup_cde,dscr
	 * 
	 * @param lookup_type
	 * @return
	 */
	public Map<String, String> selectLookupCdeAndDscr(String lookupType) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("lookupType", lookupType);
		queryMap.put("orderByClause", "value");
		List<TbLookupCde> list = tbLookupCdeMapper.selectByExample(queryMap);
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (list != null && list.size() > 0) {
			for (TbLookupCde cde : list) {
				map.put(cde.getLookupCde(), cde.getDscr());
			}
		}
		return map;
	}

	/**
	 * 依傳入TbLookupCdeKey取得dscr
	 * 
	 * @param TbLookupCdeKey
	 * @return
	 */
	public TbLookupCde selectLookupCdebyPK(TbLookupCdeKey key) {
		TbLookupCde tbLookupCde = tbLookupCdeMapper.selectByPrimaryKey(key);
		return tbLookupCde;
	}

	/**
	 * @description 查詢代碼描述
	 * @method fnPublicQueryLookupDscr
	 * 
	 * @param map
	 * @return 代碼描述
	 * 
	 * @author Marks
	 * @since 2020/06/03
	 */
	public String getLookupCdeDscr(String lookupType, String lookupCde) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("lookupType", lookupType);
		searchMap.put("lookupCde", lookupCde);
		//fixed by Marks 2020/09/09
		return ESAPI.encoder().encodeForHTML(tbSqlManualMapper.fnPublicQueryLookupDscr(searchMap));
	}

	/**
	 * <pre>
	 * 
	 * @method getAESKey
	 * @description 共用取得加密Key
	 * 
	 *              <pre>
	 * 
	 * @author Marks
	 * @since 2020/06/05
	 */
	public TbLookupCde getAESKey() {
		TbLookupCdeKey key = new TbLookupCdeKey();
		key.setLookupCde(Constants.TIOTDVR);
		key.setLookupType(Constants.AESKEY);
		return tbLookupCdeMapper.selectByPrimaryKey(key);
	}

	/**
	 * <pre>
	 * 
	 * @method encryptSecretData
	 * @description 共用加密secretData
	 * 
	 *              <pre>
	 * 
	 * @author Marks
	 * @since 2020/06/05
	 */
	public String encryptSecretData(String secretData) throws Exception {
		TbLookupCde tbLookupCde = getAESKey();
		return AESUtilDmm.encrypt(secretData, tbLookupCde.getType1(), tbLookupCde.getType2());
	}

	/**
	 * <pre>
	 * 
	 * @method decryptSecretData
	 * @description 共用解密secretData
	 * 
	 *              <pre>
	 * 
	 * @author Marks
	 * @since 2020/06/05
	 */
	public String decryptSecretData(String secretData) throws Exception {
		TbLookupCde tbLookupCde = getAESKey();
		return AESUtilDmm.decrypt(secretData, tbLookupCde.getType1(), tbLookupCde.getType2());
	}

	/**
	 * <pre>
	 * 
	 * @method getApiUrl
	 * @description 共用取得API Url
	 * 
	 *              <pre>
	 * 
	 * @author Marks
	 * @since 2020/06/05
	 */
	public String getApiUrl() {
		TbLookupCdeKey key = new TbLookupCdeKey();
		key.setLookupCde(Constants.TIOTDVR);
		key.setLookupType(Constants.WEBURL);
		TbLookupCde lookupCde = tbLookupCdeMapper.selectByPrimaryKey(key);
		return lookupCde.getType1();
	}
}
