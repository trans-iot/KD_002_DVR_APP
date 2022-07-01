package tw.msigDvrBack.common;

import java.io.Serializable;

import tw.msigDvrBack.persistence.TbSysUser;

public class BillObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 使用者資料
	 */
	private TbSysUser userProf;

	/**
	 * 使用者訊息
	 * 
	 * @return
	 */
	public TbSysUser getUserProf() {
		return userProf;
	}

	public void setUserProf(TbSysUser userProf) {
		this.userProf = userProf;
	}

}
