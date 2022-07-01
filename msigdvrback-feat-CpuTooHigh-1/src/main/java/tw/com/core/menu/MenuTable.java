/**
 *  處理方式類似 MenuTree, 但此程式判斷為透過 Table 的樹狀排版
 *  for 權限維護使用
 *  
 *  @since: 1.0 
 **/
package tw.com.core.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.persistence.TbSysMod;


public class MenuTable {
	
	private final static String NAV_S = "<div id=\"%s\">";
	private final static String NAV_E = "</div>";
	private final static String NAV_SUB_S = "<div id=\"sub_%s\">";
	private final static String NAV_SUB_E = "</div>";
	private final static String INPUT_SOURCE = "<input type=\"checkbox\" id=\"src" + Constants.SPLIT_CHAR + "%s\" name=\"src" + Constants.SPLIT_CHAR + "%s\"/>";
	private final static String INPUT_ORIGINAL = "<input type=\"checkbox\" id=\"org" + Constants.SPLIT_CHAR + "%s\" name=\"org" + Constants.SPLIT_CHAR + "%s\" disabled=\"disabled\"/>";
	
//	private final static String IMG_OPEN = "<img src=\"../images/tree/open.gif\" align=\"absmiddle\" style=\"cursor:pointer;\" class=\"imgOpen\">";
	private final static String IMG_CROSS = "<img src=\"../images/tree/cross.gif\" align=\"absmiddle\">";
	private final static String IMG_OPEN = "<img src=\"../images/tree/open.gif\" align=\"absmiddle\" style=\"cursor:pointer;\" class=\"imgOpen\">";
//	private final static String IMG_CROSS = "<img src=\"../images/tree/folder1.png\" align=\"absmiddle\">";
	private final static String IMG_VERTICAL = "<img src=\"../images/tree/vertical.gif\" align=\"absmiddle\" >";
	private final static String IMG_FOLDER = "<img name=\"isPg\" src=\"../images/tree/folder.gif\" align=\"absmiddle\">";
	private final static String IMG_BLANK = "<img src=\"../images/tree/blank.gif\" align=\"absmiddle\">";
	private final static String IMG_ANGLE = "<img src=\"../images/tree/rightAngle.gif\" align=\"absmiddle\">";
	private final static String V = ";";
	
	private final static Logger logger = LoggerFactory.getLogger(MenuTable.class);
	
	/**
	 * convert flat table into tree view
	 * @param menuList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String convList(List<MenuData> menuList, Map<String, TbSysMod> mod_Map) {
		
		TreeMap<String, TreeMap> map = new TreeMap<String, TreeMap>();
		
		for(MenuData menuData : menuList) {
			List<String> nodeArr = new ArrayList<String>();
			String pNode = menuData.getPmId();
			int x = 0;
			if (StringUtils.isBlank(pNode)){
				nodeArr.add(menuData.getModId());
			}
			while (null != pNode && !"".equals(pNode)){
				if (x==0){ 
					nodeArr.add(menuData.getModId());
				}
				TbSysMod mod = mod_Map.get(pNode);
				nodeArr.add(pNode);
				pNode = mod.getpPgProjId();
				x++;
				if (x>=100) break;
			}
			
			int sysOrder = menuData.getSysOrder();
			String sysKey = sysOrder + V + menuData.getSysName() + V + menuData.getApId();
			TreeMap<String, TreeMap> sysMap = checkMap(map, sysKey);
			map.put(sysKey, sysMap);
			
			int level = nodeArr.size();
			String pgKey = menuData.getPgOrder() + V + menuData.getPgName() + V + menuData.getPgId();
			TreeMap<String, TreeMap> prevModMap = new TreeMap<String, TreeMap>();
			for (int i=level;0<i;i--){
				//array real index 0~length-1
				int idx = i-1;
				String key = nodeArr.get(idx);
				String modId = mod_Map.get(key).getPgProjId();
				String modName = mod_Map.get(key).getPgProjDscr();
				int modSeq = mod_Map.get(key).getSeqNo();
				String modKey = modSeq + V  + modName +  V + modId;
				
				if (i==1 && i==level) {
					TreeMap<String, MenuData> pgMap = checkModMap(sysMap, modKey);
					pgMap.put(pgKey, menuData);
					sysMap.put(modKey, pgMap);
				}
				else if (i==1) {
					TreeMap<String, MenuData> pgMap = checkModMap(prevModMap, modKey);
					pgMap.put(pgKey, menuData);
					prevModMap.put(modKey, pgMap);
				}
				else if (i==level){
					TreeMap<String, TreeMap> modMap = checkMap(sysMap, modKey);
					sysMap.put(modKey, modMap);
					prevModMap = modMap;
				}
				else {
					TreeMap<String, TreeMap> modMap = checkMap(prevModMap, modKey);
					prevModMap.put(modKey, modMap);
					prevModMap = modMap;
				}
			}
		}
		
		boolean flag = map.size()==1?true:false;
		boolean[] flags = new boolean[1];
		flags[0] = flag;
		return makeTreeHtml(map, 0, flags, "");
	}
	
	/**
	 * makeTreeHtml  丟入自己添加圖片和html tag
	 * @param map 總map
	 * @param depth  層數  第三層表示為底層程式面
	 * @param isLastNode  是否為最後一個的判斷 (是否要給他往右拐)
	 * @param prefix  不太重要  就只是前面添加的html tag
	 * @return String 組合後的html <div> tag
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String makeTreeHtml(TreeMap<String, TreeMap> map, int depth, boolean[] isLastNode, String prefix) {
		Set<String> keySet = map.keySet();
		Iterator<String> keyIter = keySet.iterator();
		int count = 0;  // compare for map size, base on 1
		int mapSize = map.size();
		StringBuffer sb = new StringBuffer();
		boolean checkFlag = false;
		
		while(keyIter.hasNext()) {
			count = count + 1;
			String key = keyIter.next();
			Object obj = map.get(key);
			if (obj instanceof TreeMap) {
				checkFlag = false;
			}
			else {
				checkFlag = true;
			}
			
			logger.debug("key:{}", key);
			String[] ss = key.split(V);
			logger.debug("ss : {}{}", "", ss);
			String name = ss[1];
			String id = prefix + ss[2];
			logger.debug("prefix :[{}]", id);
			sb.append(String.format(NAV_S, id));
			sb.append(String.format(INPUT_SOURCE, id, id));
			sb.append(String.format(INPUT_ORIGINAL, id, id));
			
			boolean[] flags = new boolean[isLastNode.length+1];
			for (int j = 1; j < isLastNode.length; j++) {
				if (isLastNode[j]) {
					sb.append(IMG_BLANK);
				} else {
					sb.append(IMG_VERTICAL);
				}
				flags[j] = isLastNode[j];
			}
			
			//是否到該層最後一個節點
			int len = isLastNode.length;
			flags[len] = mapSize==count?true:false;
			if (flags[len]) {
				sb.append(IMG_ANGLE);
			} else {
				sb.append(IMG_CROSS);
			}
			
			//map中如果是TreeMap 表示為可拆解之模組  則再進去makeTreeHtml做分解
			if (!checkFlag) {
				sb.append(IMG_OPEN);
				sb.append(name);
				TreeMap childMap = (TreeMap)obj;
				
				depth = depth + 1;
				sb.append(String.format(NAV_SUB_S, id));
				sb.append(makeTreeHtml(childMap, depth, flags, id + Constants.SPLIT_CHAR));
				sb.append(NAV_SUB_E);
				depth = depth - 1;
			}
			//map中如果是menuData 表示已經為最底層程式 不須再分解
			else {
				sb.append(makePgMenuHtml((MenuData)obj));
			}
			sb.append(NAV_E);
		}
		return sb.toString();
	}
	
	private static String makePgMenuHtml(MenuData menuData) {
		StringBuffer sb = new StringBuffer();
		sb.append(IMG_FOLDER);
		sb.append(menuData.getPgName());
		return sb.toString();
	}
	
	/*
	 * 取得map(modMap)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static TreeMap<String, MenuData> checkModMap(TreeMap<String, TreeMap>parentMap, String key) {
		TreeMap<String, MenuData> map = null;
		if (parentMap.containsKey(key)) {
			map = parentMap.get(key);
		}
		else {
			map = new TreeMap<String, MenuData>();
		}
		return map;
	}
	
	/*
	 * 取得map (sysMap, pmMap)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static TreeMap<String, TreeMap> checkMap(TreeMap<String, TreeMap> parentMap, String key) {
		TreeMap<String, TreeMap> map = null;
		if (parentMap.containsKey(key)) {
			map = parentMap.get(key);
		}
		else {
			map = new TreeMap<String, TreeMap>();
		}
		return map;
	}
	
	
}
