package tw.com.core.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuAdminData {

	
	
	public static List<MenuData> getList() {
		List<MenuData> list = new ArrayList<MenuData>();
		list.add(newMenu("系統管理", "程式權限維護", "使用者基本資料維護查詢", "admin", "SYS001M", "SYS001F"));
		list.add(newMenu("系統管理", "程式權限維護", "模組維護查詢", "admin", "SYS001M", "SYS002F"));
		list.add(newMenu("系統管理", "程式權限維護", "系統維護", "admin", "SYS001M", "SYS003F"));
		list.add(newMenu("系統管理", "程式權限維護", "程式維護查詢", "admin", "SYS001M", "SYS004F"));
		list.add(newMenu("系統管理", "程式權限維護", "角色維護", "admin", "SYS001M", "SYS005F"));
		list.add(newMenu("系統管理", "程式權限維護", "群組權限維護", "admin", "SYS001M", "SYS006F"));
		
		list.add(newMenu("系統管理", "基本資料維護", "參考資料代碼維護查詢", "admin", "SMDD001M", "SMDD001F"));
        list.add(newMenu("系統管理", "基本資料維護", "設備維護查詢", "admin", "SMDD001M", "SMDD002F"));
        list.add(newMenu("系統管理", "基本資料維護", "匯率維護查詢", "admin", "SMDD001M", "SMDD003F"));
        list.add(newMenu("系統管理", "基本資料維護", "城市維護查詢", "admin", "SMDD001M", "SMDD004F"));
        list.add(newMenu("系統管理", "基本資料維護", "廣告BANNER頁面維護查詢", "admin", "SMDD001M", "SMDD005F"));
        list.add(newMenu("系統管理", "基本資料維護", "廣告BANNER維護查詢", "admin", "SMDD001M", "SMDD006F"));
        list.add(newMenu("系統管理", "基本資料維護", "目錄檔維護查詢", "admin", "SMDD001M", "SMDD007F"));
        
        
		list.add(newMenu("系統管理", "儲值卡管理", "儲值卡產生配對作業", "admin", "SMPC001M", "SMPC001F"));
		list.add(newMenu("系統管理", "儲值卡管理", "儲值卡配對查詢作業", "admin", "SMPC001M", "SMPC002F"));
        list.add(newMenu("系統管理", "儲值卡管理", "儲值紙卡資料批次核對作業", "admin", "SMPC001M", "SMPC003F"));
        list.add(newMenu("系統管理", "儲值卡管理", "儲值卡配對序號總覽查詢功能", "admin", "SMPC001M", "SMPC004F"));
        list.add(newMenu("系統管理", "儲值卡管理", "儲值卡資訊維護作業", "admin", "SMPC001M", "SMPC005F"));
        list.add(newMenu("系統管理", "儲值卡管理", "儲值卡批次移轉作業", "admin", "SMPC001M", "SMPC006F"));
        list.add(newMenu("系統管理", "儲值卡管理", "儲值卡批次移轉記錄查詢", "admin", "SMPC001M", "SMPC007F"));
        list.add(newMenu("系統管理", "儲值卡管理", "儲值卡異動記錄查詢", "admin", "SMPC001M", "SMPC008F"));
        
		list.add(newMenu("繁簡詞庫管理", "繁簡詞庫管理", "繁體詞轉簡體詞維護", "admin", "PMTS001M", "PMTS001F"));
		list.add(newMenu("繁簡詞庫管理", "繁簡詞庫管理", "簡體詞轉繁體詞維護", "admin", "PMTS001M", "PMTS002F"));
		list.add(newMenu("繁簡詞庫管理", "繁簡詞庫管理", "繁體字轉簡體字維護", "admin", "PMTS001M", "PMTS003F"));
		list.add(newMenu("繁簡詞庫管理", "繁簡詞庫管理", "簡體字轉繁體字維護", "admin", "PMTS001M", "PMTS004F"));
		list.add(newMenu("繁簡詞庫管理", "繁簡詞庫管理", "全文檢索分詞維護", "admin", "PMTS001M", "PMTS005F"));
        list.add(newMenu("合作商管理", "合作商資料管理", "合作商維護查詢", "admin", "SPPI001M", "SPPI001F"));
        list.add(newMenu("合作商管理", "合作商資料管理", "商店維護查詢", "admin", "SPPI001M", "SPPI002F"));
        list.add(newMenu("合作商管理", "合作商資料管理", "商店銷售人員維護查詢", "admin", "SPPI001M", "SPPI003F"));
        list.add(newMenu("合作商管理", "合作商資料管理", "旅店維護查詢", "admin", "SPPI001M", "SPPI004F"));
        list.add(newMenu("合作商管理", "合作商資料管理", "旅行團維護查詢", "admin", "SPPI001M", "SPPI005F"));
        list.add(newMenu("合作商管理", "合作商資料管理", "旅行團旅店維護查詢", "admin", "SPPI001M", "SPPI006F"));
        list.add(newMenu("合作商管理", "合作商資料管理", "旅行團導遊維護查詢", "admin", "SPPI001M", "SPPI007F"));
        list.add(newMenu("合作商管理", "拆帳管理模組", "拆帳對象設定", "admin", "SPSE001M", "SPSE001F"));
        list.add(newMenu("合作商管理", "拆帳管理模組", "拆帳模式維護查詢", "admin", "SPSE001M", "SPSE002F"));
        list.add(newMenu("合作商管理", "拆帳管理模組", "拆帳結算作業", "admin", "SPSE001M", "SPSE003F"));
        list.add(newMenu("合作商管理", "拆帳管理模組", "拆帳報表", "admin", "SPSE001M", "SPSE004F"));
        list.add(newMenu("客戶管理", "客戶資料管理", "客戶基本資料維護查詢", "admin", "CMCI1001M", "CMCI001F"));
        list.add(newMenu("客戶管理", "客戶資料管理", "客戶團次資料維護查詢", "admin", "CMCI1001M", "CMCI002F"));
        list.add(newMenu("客戶管理", "客戶資料管理", "客戶租機資料維護查詢", "admin", "CMCI1001M", "CMCI003F"));
        list.add(newMenu("客戶管理", "客戶資料管理", "客戶餘額歷史紀錄查詢", "admin", "CMCI1001M", "CMCI004F"));
        list.add(newMenu("客戶管理", "客戶資料管理", "客戶退款作業", "admin", "CMCI1001M", "CMCI005F"));
        list.add(newMenu("客戶管理", "客戶資料管理", "客戶通話記錄查詢", "admin", "CMCI1001M", "CMCI007F"));
			
		return list;
	}
	
	private static MenuData newMenu(String parentModName, String modName, String pgName, String sysUserId, String modId, String pgId) {
		MenuData menuData = new MenuData();
		menuData.setParentModName(parentModName);
		menuData.setModName(modName);
		menuData.setPgName(pgName);
		menuData.setSysUserId(sysUserId);
		menuData.setModId(modId);
		menuData.setPgId(pgId);
		return menuData;
	}
	
	
}
