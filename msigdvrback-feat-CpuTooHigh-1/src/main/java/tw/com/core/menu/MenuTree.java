/**
 *  for 左邊選單
 *  
 *  @since: 1.0 
 **/
package tw.com.core.menu;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.util.AESUtil;

public class MenuTree {

	private final static String NAV_S = "<div>";
	private final static String NAV_E = "</div>\r\n";

	private final static String ROOT_S = "<div class=\"divDir\"><span>";
	private final static String ROOT_E = "</span></div>";
	private final static String NODE_S = "<div class=\"divDirMiddle\"><span>";
	private final static String NODE_E = "</span></div>";
	private final static String LEAF_S = "<div class=\"menuFront\"><div class=\"menuBack\"><span class=\"menucontent\">";
	
	private final static String LEAF_E = "</span></div></div>";
	private final static String BLOCK_S = "<div style=\"display: none;\">";
	private final static String BLOCK_E = "</div>\r\n";
	
	private final static Logger logger = LoggerFactory.getLogger(MenuTree.class);
	
	/**
	 * Use List of MenuData build-up HTML menu tree
	 * link data is encrypt by userId
	 * @param list
	 * @param userId
	 * @return HTML string
	 */
	public static String getHtmlTree(List<MenuData> list, String userId, String devUrl) {
		
		String root = "";		//parent module name
		String node = "";		//module name
		String leaf = "";		//pg name
		
		StringBuffer sb = new StringBuffer();
		sb.append(NAV_S);
		boolean pmFlag = false;
		for(MenuData menuData : list) {
			if (!StringUtils.equals(root, menuData.getParentModName())) {
				if (StringUtils.isNotBlank(root)) {
					sb.append(BLOCK_E);	// close previous node
					sb.append(BLOCK_E);	// close previous root
					pmFlag = false;
				}
				root = menuData.getParentModName();
				sb.append(ROOT_S);
				sb.append(root);
				sb.append(ROOT_E);
				sb.append(BLOCK_S);
			}
			if (!StringUtils.equals(node, menuData.getModName())) {
				if (StringUtils.isNotBlank(node)) {
					if (pmFlag) {
						sb.append(BLOCK_E);	// if next node, close previous tag
					}
				}
				node = menuData.getModName();
				sb.append(NODE_S);
				sb.append(node);
				sb.append(NODE_E);
				sb.append(BLOCK_S);
				if (!pmFlag) pmFlag = true;
			}
			leaf = menuData.getPgName();
			
			sb.append(LEAF_S);
			sb.append(leaf);
			if (StringUtils.isNotBlank(devUrl)) {
				menuData.setSysUrl(devUrl);
			}
			sb.append(buildLink(menuData, userId));
			sb.append(LEAF_E);
		}
		sb.append(BLOCK_E);	//close node loop
		sb.append(BLOCK_E);	//close root loop
		sb.append(NAV_E);
		return sb.toString();
	}
	
	private static String buildLink(MenuData menuData, String userId) {
		String sysUserId = menuData.getSysUserId();
		String modId = menuData.getModId();
		String pgId  = menuData.getPgId();
		String url = menuData.getSysUrl();
		String mixData =  sysUserId + ";" + modId + ";" + pgId;
		try {
			mixData = AESUtil.encStr(mixData, userId);
		} catch (Exception e) {
			logger.error("Encrypt exeception : {},{}", e.getMessage(), e);
			mixData = "ERROR";
			url = "ERROR";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<input type='hidden' name='msig' value='").append(mixData).append("' />");
		sb.append("<input type='hidden' name='path' value='").append(url).append("' />");
		return sb.toString();
	}
}
