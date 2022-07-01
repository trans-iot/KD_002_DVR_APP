package tw.msigDvrBack.common;

import org.apache.commons.lang.builder.ToStringBuilder;

public class BaseForm {

	private String orderByClause;
	private String perPageNum = "10"; // 每頁筆數 default 10;
	private String pages = "1"; // 頁面 default 1;
	private String queryClicked = "false";

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(String perPageNum) {
		this.perPageNum = perPageNum;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getQueryClicked() {
		return queryClicked;
	}

	public void setQueryClicked(String queryClicked) {
		this.queryClicked = queryClicked;
	}
}
