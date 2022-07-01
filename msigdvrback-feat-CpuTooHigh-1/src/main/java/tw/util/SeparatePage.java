/*
 *	  version 1.0
 *    version 1.1 更改分頁顯示改用 javascript 產生 option
 */


package tw.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * @author derek_chang
 * @since 2003/11/09 上午 9:36:19
 * 
 */
public class SeparatePage extends TagSupport {

	private String totalRows; // 要轉成數字 總列數
	private String currentPageNum; // 要轉成數字 現在是第幾頁
	private String perPageNum; // 要轉成數字 每頁顯示幾筆
	private String target; // 目標網頁
	private String getParam; // 傳遞之變數 動態時侯用
	private String selectStyle = "";
	private String formName = "_NON_EXIST";

	private String firstImg;
	private String leftImg;
	private String rightImg;
	private String lastImg;

	private String firstImgEnd;
	private String leftImgEnd;
	private String rightImgEnd;
	private String lastImgEnd;
	private String showSelect = "";

	/**
	 * 取得 form 用以傳參數用
	 * 
	 * @param totalRows
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * 要轉成數字 共幾筆
	 * 
	 * @param totalRows
	 */
	public void setTotalRows(String totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * 要轉成數字 現在是第幾頁
	 * 
	 * @param currentPageNum
	 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	/**
	 * 要轉成數字 每頁顯示幾筆
	 * 
	 * @param perPageNum
	 */
	public void setPerPageNum(String perPageNum) {
		this.perPageNum = perPageNum;
	}

	/**
	 * 目標網頁
	 * 
	 * @param target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 傳遞之變數 動態時侯用
	 * 
	 * @param getParam
	 */
	public void setGetParam(String getParam) {
		this.getParam = getParam;
	}

	/**
	 * 拉 bar 樣式設定
	 * 
	 * @param selectStyle
	 */
	public void setSelectStyle(String selectStyle) {
		this.selectStyle = selectStyle;
	}

	/**
	 * 第一頁的圖型
	 * 
	 * @param firstImg
	 */
	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}

	/**
	 * 上一頁的圖型
	 */
	public void setLeftImg(String leftImg) {
		this.leftImg = leftImg;
	}

	/**
	 * 下一頁的圖型
	 * 
	 * @param rightImg
	 */
	public void setRightImg(String rightImg) {
		this.rightImg = rightImg;
	}

	/**
	 * 最後一頁的圖型
	 * 
	 * @param lastImg
	 *            最後一頁的圖型
	 */
	public void setLastImg(String lastImg) {
		this.lastImg = lastImg;
	}

	/**
	 * 第一頁的圖型
	 * 
	 * @param firstImg
	 */
	public void setFirstImgEnd(String firstImgEnd) {
		this.firstImgEnd = firstImgEnd;
	}

	/**
	 * 上一頁的圖型
	 */
	public void setLeftImgEnd(String leftImgEnd) {
		this.leftImgEnd = leftImgEnd;
	}

	/**
	 * 下一頁的圖型
	 * 
	 * @param rightImg
	 */
	public void setRightImgEnd(String rightImgEnd) {
		this.rightImgEnd = rightImgEnd;
	}

	/**
	 * 最後一頁的圖型
	 * 
	 * @param lastImg
	 *            最後一頁的圖型
	 */
	public void setLastImgEnd(String lastImgEnd) {
		this.lastImgEnd = lastImgEnd;
	}

	/**
	 * 顯示 select
	 * 
	 * @param showSelect
	 *            顯示 select
	 */
	public void setShowSelect(String showSelect) {
		this.showSelect = showSelect;
	}

	/**
	 * 標籤開始作用, 產生圖樣選擇工具, 並傳startRow endRow 到前端
	 * 
	 * */
	public int doStartTag() throws JspException {
		// 取得現有頁數
		currentPageNum = this.pageContext.getRequest().getParameter("pages");
		int toRows; // 總筆數
		int curPageNum; // 現在的頁數號碼
		int pPageNum; // 每一頁有幾列
		int allPages; // 總頁數;

		if (firstImgEnd == null) {
			firstImgEnd = firstImg;
		}
		if (leftImgEnd == null) {
			leftImgEnd = leftImg;
		}
		if (rightImgEnd == null) {
			rightImgEnd = rightImg;
		}
		if (lastImgEnd == null) {
			lastImgEnd = lastImg;
		}
		/** 產生圖樣選擇工具 */
		try {

			toRows = new Integer(totalRows).intValue(); // 總筆數
			
			//資料量過大則show只能選擇6頁內資料,不然維持原分頁下拉選單  by Frank 2014/4/25
//			if (toRows>10000){
//				showSelect = "box";
//			}else{
				showSelect = "";
//			}
			
			if (currentPageNum == null || currentPageNum.equals("")) {
				currentPageNum = "1";
			}
			curPageNum = new Integer(currentPageNum).intValue(); // 現在的頁數號碼
			pPageNum = new Integer(perPageNum).intValue(); // 每一頁有幾列

			if (toRows % pPageNum == 0) { // 如果是整除的頁數不加 1
				allPages = toRows / pPageNum; // 以防產生空頁
			} else {
				allPages = toRows / pPageNum + 1;
			}

			// 設定最大頁數
			if (curPageNum > allPages) {
				curPageNum = allPages;
			}

			// 寫 java script 參數
			writeJavaScript(allPages, curPageNum);

			
			// 是否要顯示 拉bar
			if (showSelect.equals("no")) {
			} else if (showSelect.equals("text")) {
				writeText(allPages, curPageNum);
			} else if (showSelect.equals("box")) {
				addBackImgLink(toRows, curPageNum, allPages, pPageNum);
				writeBox(allPages, curPageNum);
				addForwardImgLink(toRows, curPageNum, allPages, pPageNum);
				//addBackLink(toRows, curPageNum, allPages, pPageNum);
				//addForwardLink(toRows, curPageNum, allPages, pPageNum);
			} else {
				addBackImgLink(toRows, curPageNum, allPages, pPageNum);
				writeSelection(allPages, curPageNum);
				addForwardImgLink(toRows, curPageNum, allPages, pPageNum);
			}

		} catch (IOException ioe) {
			throw new JspTagException(ioe.getMessage());
		} catch (NumberFormatException nfe) {
			// do nothing
		}
		return SKIP_BODY; // 忽略 body content
	}
	
	/**
	 * 前一頁
	 * @param toRows
	 * @param curPageNum
	 * @param allPages
	 * @param pPageNum
	 * @throws IOException
	 */
	private void addBackLink(int toRows, int curPageNum, int allPages, int pPageNum)throws IOException{
		String rightImgEnd = "<span class=\"nbrBoxEnd\" >&gt;</span>";
		String rightImg = "<span class=\"nbrBoxHover\" >&gt;</span>";
		String lastImgEnd = "<span class=\"nbrBoxEnd\" >&gt;&gt;</span>";
		String lastImg = "<span class=\"nbrBoxHover\" >&gt;&gt;</span>";
		
		String leftImgEnd = "<span class=\"nbrBoxEnd\" >&lt;</span>";
		String leftImg = "<span class=\"nbrBoxHover\" >&lt;</span>";
		String firstImgEnd = "<span class=\"nbrBoxEnd\" >&lt;&lt;</span>";		
		String firstImg = "<span class=\"nbrBoxHover\" >&lt;&lt;</span>";	
		
		if (toRows == 0 || curPageNum == 1) {
			pageContext.getOut().write(firstImgEnd + " "); // picture
		} else {
			pageContext.getOut().write("<span style='cursor:pointer' onclick=\"javascript:ReOrdering(1)\">" + firstImg + "</span> "); // picture
		}
		if (toRows == 0 || curPageNum == 1) {
			pageContext.getOut().write(leftImgEnd + " "); // picture
		} else {
			pageContext.getOut().write("<span style='cursor:pointer' onclick=\"javascript:ReOrdering(" + (curPageNum - 1) + ")\">" + leftImg + "</span> "); // picture
		}
	}
	
	/**
	 * 前一頁 圖示
	 * @param toRows
	 * @param curPageNum
	 * @param allPages
	 * @param pPageNum
	 * @throws IOException
	 */
	private void addBackImgLink(int toRows, int curPageNum, int allPages, int pPageNum)throws IOException{
		
		pageContext.getOut().write("<table width=\"320\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"text-align:center;background-color:#2B2B2B;\">");

		if (toRows == 0 || curPageNum == 1) {
			pageContext.getOut().write("<td style=\" width:20px;background-color:#2B2B2B;\" align=\"center\">"+firstImgEnd + " " + "</td>"); // picture
		} else {
			pageContext.getOut().write("<td style=\" width:20px;background-color:#2B2B2B;\" align=\"center\">"+"<span style='cursor:pointer' onclick=\"javascript:ReOrdering(1)\">" + firstImg + "</span> "+ "</td>"); // picture
		}
		if (toRows == 0 || curPageNum == 1) {
			pageContext.getOut().write("<td style=\" width:20px;background-color:#2B2B2B;\" align=\"center\">"+leftImgEnd + " "+ "</td>"); // picture
		} else {
			pageContext.getOut().write("<td style=\" width:20px;background-color:#2B2B2B;\" align=\"center\">"+"<span style='cursor:pointer' onclick=\"javascript:ReOrdering(" + (curPageNum - 1) + ")\">" + leftImg + "</span> "+ "</td>"); // picture
		}
	}
	
	/**
	 * 後一頁 
	 * 
	 * @param toRows
	 * @param curPageNum
	 * @param allPages
	 * @param pPageNum
	 * @throws IOException
	 */
	private void addForwardLink(int toRows, int curPageNum, int allPages, int pPageNum) throws IOException {
		
		String rightImgEnd = " <span class=\"nbrBoxEnd\" >&gt;</span> ";
		String rightImg = " <span class=\"nbrBoxHover\" >&gt;</span> ";
		String lastImgEnd = " <span class=\"nbrBoxEnd\" >&gt;&gt;</span> ";
		String lastImg = " <span class=\"nbrBoxHover\" >&gt;&gt;</span> ";
		
		String leftImgEnd = " <span class=\"nbrBoxEnd\" >&lt;</span> ";
		String leftImg = " <span class=\"nbrBoxHover\" >&lt;</span> ";
		String firstImgEnd = " <span class=\"nbrBoxEnd\" >&lt;&lt;</span> ";		
		String firstImg = " <span class=\"nbrBoxHover\" >&lt;&lt;</span> ";	
		
		
		if (toRows == 0 || curPageNum == allPages) {
			pageContext.getOut().write(rightImgEnd + " "); // picture
		} else {
			pageContext.getOut().write("<span style='cursor:pointer' onclick=\"javascript:ReOrdering(" + (curPageNum + 1) + ")\">" + rightImg + "</span> "); // picture
		}
		if (toRows == 0 || curPageNum == allPages) {
			pageContext.getOut().write(lastImgEnd + " ");
		} else {
			pageContext.getOut().write("<span style='cursor:pointer' onclick=\"javascript:ReOrdering(" + allPages + ")\">" + lastImg + "</span> "); // picture
		}

		// arraylist 迴圈中 用來決定有幾筆 for(int i=startRow;i<endRow;i++)
		if (pPageNum > toRows) {
			pageContext.setAttribute("startRow", new Integer(0).toString());
			pageContext.setAttribute("endRow", new Integer(toRows).toString());
		} else if (curPageNum * pPageNum > toRows) {
			pageContext.setAttribute("startRow", new Integer((curPageNum - 1) * pPageNum).toString());
			pageContext.setAttribute("endRow", new Integer((curPageNum - 1) * pPageNum + toRows % pPageNum).toString());
		} else {
			pageContext.setAttribute("startRow", new Integer((curPageNum - 1) * pPageNum).toString());
			pageContext.setAttribute("endRow", new Integer(curPageNum * pPageNum).toString());
		}

		int endRowInt = new Integer((String) pageContext.getAttribute("endRow")).intValue();
		if (endRowInt == toRows && toRows % pPageNum != 0) {
			Integer maxLimit = new Integer(toRows % pPageNum);
			pageContext.setAttribute("rowLimit", maxLimit.toString());
		} else {
			Integer maxLimit = new Integer(pPageNum);
			pageContext.setAttribute("rowLimit", maxLimit.toString());
		}
		pageContext.setAttribute("perPageNum", perPageNum);
		pageContext.setAttribute("allPages", new Integer(allPages).toString());

		this.firstImg = null;
		this.leftImg = null;
		this.rightImg = null;
		this.lastImg = null;

		this.firstImgEnd = null;
		this.leftImgEnd = null;
		this.rightImgEnd = null;
		this.lastImgEnd = null;
	}

	/**
	 * 前一頁 後一頁 圖示
	 * 
	 * @param toRows
	 * @param curPageNum
	 * @param allPages
	 * @param pPageNum
	 * @throws IOException
	 */
	private void addForwardImgLink(int toRows, int curPageNum, int allPages, int pPageNum) throws IOException {
		if (toRows == 0 || curPageNum == allPages) {
			pageContext.getOut().write("<td style=\" width:20px;background-color:#2B2B2B;\" align=\"center\">"+rightImgEnd + " "+"</td>"); // picture
		} else {
			pageContext.getOut().write("<td style=\" width:20px;background-color:#2B2B2B;\" align=\"center\">"+"<span style='cursor:pointer' onclick=\"javascript:ReOrdering(" + (curPageNum + 1) + ")\">" + rightImg + "</span> "+"</td>"); // picture
		}
		if (toRows == 0 || curPageNum == allPages) {
			pageContext.getOut().write("<td style=\" width:20px;background-color:#2B2B2B;\" align=\"center\">"+lastImgEnd+"</td>");
		} else {
			pageContext.getOut().write("<td style=\" width:20px;background-color:#2B2B2B;\" align=\"center\">"+"<span style='cursor:pointer' onclick=\"javascript:ReOrdering(" + allPages + ")\">" + lastImg + "</span> "+"</td>"); // picture
		}
		pageContext.getOut().write("</table>");

		// arraylist 迴圈中 用來決定有幾筆 for(int i=startRow;i<endRow;i++)
		if (pPageNum > toRows) {
			pageContext.setAttribute("startRow", new Integer(0).toString());
			pageContext.setAttribute("endRow", new Integer(toRows).toString());
		} else if (curPageNum * pPageNum > toRows) {
			pageContext.setAttribute("startRow", new Integer((curPageNum - 1) * pPageNum).toString());
			pageContext.setAttribute("endRow", new Integer((curPageNum - 1) * pPageNum + toRows % pPageNum).toString());
		} else {
			pageContext.setAttribute("startRow", new Integer((curPageNum - 1) * pPageNum).toString());
			pageContext.setAttribute("endRow", new Integer(curPageNum * pPageNum).toString());
		}

		int endRowInt = new Integer((String) pageContext.getAttribute("endRow")).intValue();
		if (endRowInt == toRows && toRows % pPageNum != 0) {
			Integer maxLimit = new Integer(toRows % pPageNum);
			pageContext.setAttribute("rowLimit", maxLimit.toString());
		} else {
			Integer maxLimit = new Integer(pPageNum);
			pageContext.setAttribute("rowLimit", maxLimit.toString());
		}
		pageContext.setAttribute("perPageNum", perPageNum);
		pageContext.setAttribute("allPages", new Integer(allPages).toString());

		this.firstImg = null;
		this.leftImg = null;
		this.rightImg = null;
		this.lastImg = null;

		this.firstImgEnd = null;
		this.leftImgEnd = null;
		this.rightImgEnd = null;
		this.lastImgEnd = null;
	}

	/**
	 * 畫出 selection
	 * 
	 * @param allPages
	 * @param curPageNum
	 * @throws Exception
	 */
	public void writeSelection(int allPages, int curPageNum) throws IOException {
		pageContext.getOut().write("<td style=\" width:50px;background-color:#2B2B2B;\" align=\"center\"><div class=\"styled-select-page\"><select id=\"currentPage\" name=\"currentPage\" " + selectStyle + " onchange=ReOrdering(this.value) >");
		// allPages =
//		for (int i = 0; i < allPages; i++) {
//			String valuePage = "";
//			if (curPageNum == (i + 1)) {
//				valuePage = "selected";
//			}
//			pageContext.getOut().write("<option value=\"" + (i + 1) + "\" " + valuePage + " >" + (i + 1) + "</option>\n");
//		}
		writeJavaScriptOption(allPages,curPageNum);
		pageContext.getOut().write("</select></div></td>\n");

	}

	/**
	 * 畫出 input text
	 * 
	 * @param allPages
	 * @param curPageNum
	 * @throws Exception
	 */
	public void writeText(int allPages, int curPageNum) throws IOException {
		pageContext.getOut().write(
				"<input type=\"text\" maxlength=\"6\" size=\"4\" value=\"" + curPageNum + "\" onkeydown=\"return event.keyCode != 13 ||  ReOrdering(this.value)\" style=\"text-align:right\" >");
	}

	/**
	 * 畫出 multi box 供選擇
	 * 
	 * @param allPages
	 * @param curPageNum
	 * @throws Exception
	 */
	public void writeBox(int allPages, int curPageNum) throws IOException {
		// 最大 show 5 頁
		int maxShowPage = 6;
		// 小於maxShowPage show 全部 box 且 selected 不移動
		if (allPages < maxShowPage) {
			for (int i = 0; i < allPages; i++) {
				int boxPage = i + 1;
				if (curPageNum == boxPage) {
					pageContext.getOut().write("<span onclick=\"ReOrdering(" + boxPage + ")\" class=\"nbrBoxHover\" >" + boxPage + "</span>");
				} else {
					pageContext.getOut().write("<span onclick=\"ReOrdering(" + boxPage + ")\" class=\"nbrBox\">" + boxPage + "</span>");
				}
			}
		} else {
			int middlePage = maxShowPage / 2;
			int startPage = curPageNum - middlePage;
			int endPage = curPageNum + middlePage;
//			System.out.println("=====????>>>>" + startPage + "===>>" + endPage);
//			System.out.println(curPageNum + ">" + middlePage + " and " + curPageNum + "<" + (allPages - (middlePage)));

			// selected 移動
			if (curPageNum >= middlePage && curPageNum <= (allPages - (middlePage))) {
				for (int i = startPage; i < endPage; i++) {
					int boxPage = i + 1;
					if (curPageNum == boxPage) {
						pageContext.getOut().write("<span onclick=\"ReOrdering(" + boxPage + ")\" class=\"nbrBoxHover\" >" + boxPage + "</span>");
					} else {
						pageContext.getOut().write("<span onclick=\"ReOrdering(" + boxPage + ")\" class=\"nbrBox\" >" + boxPage + "</span>");
					}
				}
			} else {
				// 不變動
				if (curPageNum < middlePage) {
					for (int i = 0; i < maxShowPage; i++) {
						int boxPage = i + 1;
						if (curPageNum == boxPage) {
							pageContext.getOut().write("<span onclick=\"ReOrdering(" + boxPage + ")\" class=\"nbrBoxHover\" >" + boxPage + "</span>");
						} else {
							pageContext.getOut().write("<span onclick=\"ReOrdering(" + boxPage + ")\" class=\"nbrBox\" >" + boxPage + "</span>");
						}
					}
				}
				// 不變動
				if (curPageNum > (allPages - (middlePage))) {
					for (int i = (allPages - maxShowPage); i < allPages; i++) {
						int boxPage = i + 1;
						if (curPageNum == boxPage) {
							pageContext.getOut().write("<span onclick=\"ReOrdering(" + boxPage + ")\" class=\"nbrBoxHover\" >" + boxPage + "</span>");
						} else {
							pageContext.getOut().write("<span onclick=\"ReOrdering(" + boxPage + ")\" class=\"nbrBox\" >" + boxPage + "</span>");
						}
					}
				}
			}
		}
	}
	
	public void writeJavaScriptOption(int allPages, int curPageNum)throws IOException{
		StringBuffer jsStringBuffer = new StringBuffer(" <script language='JavaScript'>");		
		jsStringBuffer.append("for(var i=1;i<="+allPages+";i++){");
		jsStringBuffer.append("var valPage='';");
		jsStringBuffer.append("if("+curPageNum+"== i )");
		jsStringBuffer.append("valPage='selected';");
		jsStringBuffer.append("document.write(\"<option value='\"+i+\"' \"+valPage+\" >\"+i+\"</option>\");");
		jsStringBuffer.append("}");
		jsStringBuffer.append(" </script>\n");
		//<option value=\"" + (i + 1) + "\" " + valuePage + " >" + (i + 1) + "</option>\n
		pageContext.getOut().write(jsStringBuffer.toString());		
	}

	/**
	 * 產生 Java Script 參數
	 * 
	 * @param allPages
	 * @param curPageNum
	 * @throws Exception
	 */
	public void writeJavaScript(int allPages, int curPageNum) throws IOException {
		// 下面是javascript 用來使選擇的數字作用產生超連結
		StringBuffer jsStringBuffer = new StringBuffer(" <script language='JavaScript'>");
		jsStringBuffer.append(" function beforeReOrdering(){  }");
		jsStringBuffer.append(" function ReOrdering(val) {  beforeReOrdering(); ");
		jsStringBuffer.append(" val = val+'';");
		jsStringBuffer.append(" var data = val.match(/[^0-9]/g);  ");
		jsStringBuffer.append(" if(data == null ){}else{ return;}");
		jsStringBuffer.append(" if(val >" + allPages + "){");
		jsStringBuffer.append("  val=" + allPages + ";");
		jsStringBuffer.append(" } ");
		jsStringBuffer.append(" if(val < 1 ){");
		jsStringBuffer.append("  val=1;");
		jsStringBuffer.append(" } ");
		jsStringBuffer.append(" var form = $(\"#" + formName + "\"); ");
		jsStringBuffer.append("if(form){");
		jsStringBuffer.append("\n");
		/*
		jsStringBuffer.append(" var input=document.createElement('input'); ");
		jsStringBuffer.append(" input.name='pages';");
		jsStringBuffer.append(" input.type='hidden'; ");
		jsStringBuffer.append(" var perPageNum=document.createElement('input'); ");
		jsStringBuffer.append(" perPageNum.name='perPageNum'; ");
		jsStringBuffer.append(" perPageNum.value='" + perPageNum + "';");
		jsStringBuffer.append(" perPageNum.type='hidden'; ");
		jsStringBuffer.append(" if(!form.pages){ ");
		jsStringBuffer.append("  input.value=val;");
		jsStringBuffer.append("  form.appendChild(input);");
		jsStringBuffer.append("  form.appendChild(perPageNum)");
		jsStringBuffer.append(" }else{"); */
		jsStringBuffer.append("   form.find('input[name=\"pages\"]').val(val);");
		jsStringBuffer.append("\n");
		/*
		jsStringBuffer.append("   form.perPageNum.value='" + perPageNum + "';");
		jsStringBuffer.append(" } "); */
		jsStringBuffer.append(" form.submit(); ");
		jsStringBuffer.append("}else{");
		jsStringBuffer.append(" location.href =\"?pages=\"+val+\"&" + getParam + "\" ;");
		jsStringBuffer.append("}");
		jsStringBuffer.append(" } ");
		jsStringBuffer.append(" </script>\n");
		pageContext.getOut().write(jsStringBuffer.toString());
	}
}
