package tw.mybatis;

import java.io.Serializable;


public class Page implements Serializable {

	private static final long serialVersionUID = -8523042447690232988L;
	
	// 每頁顯示記錄數
	private int perPage;
	// 撈取頁面
	private int currentPage;

	public Page(int currentPage, int perPage) {
		this.currentPage = currentPage;
		this.perPage = perPage;
	}

	/**
	 * 分頁查詢開始記錄位置
	 * @return the begin
	 */
	public int getBegin() {
		return (this.currentPage-1) * this.perPage ;
	}

    public int getPerPage() {
        return perPage;
    }

}