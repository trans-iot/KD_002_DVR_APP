package tw.msigDvrBack.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tw.mybatis.Page;

public class TbSysUserExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	protected List<Criteria> oredCriteria;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	protected Page page;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public TbSysUserExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andSysUserIdIsNull() {
			addCriterion("sys_user_id is null");
			return (Criteria) this;
		}

		public Criteria andSysUserIdIsNotNull() {
			addCriterion("sys_user_id is not null");
			return (Criteria) this;
		}

		public Criteria andSysUserIdEqualTo(String value) {
			addCriterion("sys_user_id =", value, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdNotEqualTo(String value) {
			addCriterion("sys_user_id <>", value, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdGreaterThan(String value) {
			addCriterion("sys_user_id >", value, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdGreaterThanOrEqualTo(String value) {
			addCriterion("sys_user_id >=", value, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdLessThan(String value) {
			addCriterion("sys_user_id <", value, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdLessThanOrEqualTo(String value) {
			addCriterion("sys_user_id <=", value, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdLike(String value) {
			addCriterion("sys_user_id like", value, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdNotLike(String value) {
			addCriterion("sys_user_id not like", value, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdIn(List<String> values) {
			addCriterion("sys_user_id in", values, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdNotIn(List<String> values) {
			addCriterion("sys_user_id not in", values, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdBetween(String value1, String value2) {
			addCriterion("sys_user_id between", value1, value2, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andSysUserIdNotBetween(String value1, String value2) {
			addCriterion("sys_user_id not between", value1, value2, "sysUserId");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNull() {
			addCriterion("user_name is null");
			return (Criteria) this;
		}

		public Criteria andUserNameIsNotNull() {
			addCriterion("user_name is not null");
			return (Criteria) this;
		}

		public Criteria andUserNameEqualTo(String value) {
			addCriterion("user_name =", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotEqualTo(String value) {
			addCriterion("user_name <>", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThan(String value) {
			addCriterion("user_name >", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("user_name >=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThan(String value) {
			addCriterion("user_name <", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLessThanOrEqualTo(String value) {
			addCriterion("user_name <=", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameLike(String value) {
			addCriterion("user_name like", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotLike(String value) {
			addCriterion("user_name not like", value, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameIn(List<String> values) {
			addCriterion("user_name in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotIn(List<String> values) {
			addCriterion("user_name not in", values, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameBetween(String value1, String value2) {
			addCriterion("user_name between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andUserNameNotBetween(String value1, String value2) {
			addCriterion("user_name not between", value1, value2, "userName");
			return (Criteria) this;
		}

		public Criteria andDeptNoIsNull() {
			addCriterion("dept_no is null");
			return (Criteria) this;
		}

		public Criteria andDeptNoIsNotNull() {
			addCriterion("dept_no is not null");
			return (Criteria) this;
		}

		public Criteria andDeptNoEqualTo(String value) {
			addCriterion("dept_no =", value, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoNotEqualTo(String value) {
			addCriterion("dept_no <>", value, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoGreaterThan(String value) {
			addCriterion("dept_no >", value, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoGreaterThanOrEqualTo(String value) {
			addCriterion("dept_no >=", value, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoLessThan(String value) {
			addCriterion("dept_no <", value, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoLessThanOrEqualTo(String value) {
			addCriterion("dept_no <=", value, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoLike(String value) {
			addCriterion("dept_no like", value, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoNotLike(String value) {
			addCriterion("dept_no not like", value, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoIn(List<String> values) {
			addCriterion("dept_no in", values, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoNotIn(List<String> values) {
			addCriterion("dept_no not in", values, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoBetween(String value1, String value2) {
			addCriterion("dept_no between", value1, value2, "deptNo");
			return (Criteria) this;
		}

		public Criteria andDeptNoNotBetween(String value1, String value2) {
			addCriterion("dept_no not between", value1, value2, "deptNo");
			return (Criteria) this;
		}

		public Criteria andEmailIsNull() {
			addCriterion("email is null");
			return (Criteria) this;
		}

		public Criteria andEmailIsNotNull() {
			addCriterion("email is not null");
			return (Criteria) this;
		}

		public Criteria andEmailEqualTo(String value) {
			addCriterion("email =", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailNotEqualTo(String value) {
			addCriterion("email <>", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailGreaterThan(String value) {
			addCriterion("email >", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailGreaterThanOrEqualTo(String value) {
			addCriterion("email >=", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailLessThan(String value) {
			addCriterion("email <", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailLessThanOrEqualTo(String value) {
			addCriterion("email <=", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailLike(String value) {
			addCriterion("email like", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailNotLike(String value) {
			addCriterion("email not like", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailIn(List<String> values) {
			addCriterion("email in", values, "email");
			return (Criteria) this;
		}

		public Criteria andEmailNotIn(List<String> values) {
			addCriterion("email not in", values, "email");
			return (Criteria) this;
		}

		public Criteria andEmailBetween(String value1, String value2) {
			addCriterion("email between", value1, value2, "email");
			return (Criteria) this;
		}

		public Criteria andEmailNotBetween(String value1, String value2) {
			addCriterion("email not between", value1, value2, "email");
			return (Criteria) this;
		}

		public Criteria andStatusIsNull() {
			addCriterion("status is null");
			return (Criteria) this;
		}

		public Criteria andStatusIsNotNull() {
			addCriterion("status is not null");
			return (Criteria) this;
		}

		public Criteria andStatusEqualTo(String value) {
			addCriterion("status =", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotEqualTo(String value) {
			addCriterion("status <>", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThan(String value) {
			addCriterion("status >", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(String value) {
			addCriterion("status >=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThan(String value) {
			addCriterion("status <", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThanOrEqualTo(String value) {
			addCriterion("status <=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLike(String value) {
			addCriterion("status like", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotLike(String value) {
			addCriterion("status not like", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusIn(List<String> values) {
			addCriterion("status in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotIn(List<String> values) {
			addCriterion("status not in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusBetween(String value1, String value2) {
			addCriterion("status between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotBetween(String value1, String value2) {
			addCriterion("status not between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andPwdIsNull() {
			addCriterion("password is null");
			return (Criteria) this;
		}

		public Criteria andPwdIsNotNull() {
			addCriterion("password is not null");
			return (Criteria) this;
		}

		public Criteria andPwdEqualTo(String value) {
			addCriterion("password =", value, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdNotEqualTo(String value) {
			addCriterion("password <>", value, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdGreaterThan(String value) {
			addCriterion("password >", value, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdGreaterThanOrEqualTo(String value) {
			addCriterion("password >=", value, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdLessThan(String value) {
			addCriterion("password <", value, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdLessThanOrEqualTo(String value) {
			addCriterion("password <=", value, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdLike(String value) {
			addCriterion("password like", value, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdNotLike(String value) {
			addCriterion("password not like", value, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdIn(List<String> values) {
			addCriterion("password in", values, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdNotIn(List<String> values) {
			addCriterion("password not in", values, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdBetween(String value1, String value2) {
			addCriterion("password between", value1, value2, "pwd");
			return (Criteria) this;
		}

		public Criteria andPwdNotBetween(String value1, String value2) {
			addCriterion("password not between", value1, value2, "pwd");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntIsNull() {
			addCriterion("login_fail_cnt is null");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntIsNotNull() {
			addCriterion("login_fail_cnt is not null");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntEqualTo(Short value) {
			addCriterion("login_fail_cnt =", value, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntNotEqualTo(Short value) {
			addCriterion("login_fail_cnt <>", value, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntGreaterThan(Short value) {
			addCriterion("login_fail_cnt >", value, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntGreaterThanOrEqualTo(Short value) {
			addCriterion("login_fail_cnt >=", value, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntLessThan(Short value) {
			addCriterion("login_fail_cnt <", value, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntLessThanOrEqualTo(Short value) {
			addCriterion("login_fail_cnt <=", value, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntIn(List<Short> values) {
			addCriterion("login_fail_cnt in", values, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntNotIn(List<Short> values) {
			addCriterion("login_fail_cnt not in", values, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntBetween(Short value1, Short value2) {
			addCriterion("login_fail_cnt between", value1, value2, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andLoginFailCntNotBetween(Short value1, Short value2) {
			addCriterion("login_fail_cnt not between", value1, value2, "loginFailCnt");
			return (Criteria) this;
		}

		public Criteria andCrUserIsNull() {
			addCriterion("cr_user is null");
			return (Criteria) this;
		}

		public Criteria andCrUserIsNotNull() {
			addCriterion("cr_user is not null");
			return (Criteria) this;
		}

		public Criteria andCrUserEqualTo(String value) {
			addCriterion("cr_user =", value, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserNotEqualTo(String value) {
			addCriterion("cr_user <>", value, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserGreaterThan(String value) {
			addCriterion("cr_user >", value, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserGreaterThanOrEqualTo(String value) {
			addCriterion("cr_user >=", value, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserLessThan(String value) {
			addCriterion("cr_user <", value, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserLessThanOrEqualTo(String value) {
			addCriterion("cr_user <=", value, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserLike(String value) {
			addCriterion("cr_user like", value, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserNotLike(String value) {
			addCriterion("cr_user not like", value, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserIn(List<String> values) {
			addCriterion("cr_user in", values, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserNotIn(List<String> values) {
			addCriterion("cr_user not in", values, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserBetween(String value1, String value2) {
			addCriterion("cr_user between", value1, value2, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrUserNotBetween(String value1, String value2) {
			addCriterion("cr_user not between", value1, value2, "crUser");
			return (Criteria) this;
		}

		public Criteria andCrDateIsNull() {
			addCriterion("cr_date is null");
			return (Criteria) this;
		}

		public Criteria andCrDateIsNotNull() {
			addCriterion("cr_date is not null");
			return (Criteria) this;
		}

		public Criteria andCrDateEqualTo(Date value) {
			addCriterion("cr_date =", value, "crDate");
			return (Criteria) this;
		}

		public Criteria andCrDateNotEqualTo(Date value) {
			addCriterion("cr_date <>", value, "crDate");
			return (Criteria) this;
		}

		public Criteria andCrDateGreaterThan(Date value) {
			addCriterion("cr_date >", value, "crDate");
			return (Criteria) this;
		}

		public Criteria andCrDateGreaterThanOrEqualTo(Date value) {
			addCriterion("cr_date >=", value, "crDate");
			return (Criteria) this;
		}

		public Criteria andCrDateLessThan(Date value) {
			addCriterion("cr_date <", value, "crDate");
			return (Criteria) this;
		}

		public Criteria andCrDateLessThanOrEqualTo(Date value) {
			addCriterion("cr_date <=", value, "crDate");
			return (Criteria) this;
		}

		public Criteria andCrDateIn(List<Date> values) {
			addCriterion("cr_date in", values, "crDate");
			return (Criteria) this;
		}

		public Criteria andCrDateNotIn(List<Date> values) {
			addCriterion("cr_date not in", values, "crDate");
			return (Criteria) this;
		}

		public Criteria andCrDateBetween(Date value1, Date value2) {
			addCriterion("cr_date between", value1, value2, "crDate");
			return (Criteria) this;
		}

		public Criteria andCrDateNotBetween(Date value1, Date value2) {
			addCriterion("cr_date not between", value1, value2, "crDate");
			return (Criteria) this;
		}

		public Criteria andUserstampIsNull() {
			addCriterion("userstamp is null");
			return (Criteria) this;
		}

		public Criteria andUserstampIsNotNull() {
			addCriterion("userstamp is not null");
			return (Criteria) this;
		}

		public Criteria andUserstampEqualTo(String value) {
			addCriterion("userstamp =", value, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampNotEqualTo(String value) {
			addCriterion("userstamp <>", value, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampGreaterThan(String value) {
			addCriterion("userstamp >", value, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampGreaterThanOrEqualTo(String value) {
			addCriterion("userstamp >=", value, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampLessThan(String value) {
			addCriterion("userstamp <", value, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampLessThanOrEqualTo(String value) {
			addCriterion("userstamp <=", value, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampLike(String value) {
			addCriterion("userstamp like", value, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampNotLike(String value) {
			addCriterion("userstamp not like", value, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampIn(List<String> values) {
			addCriterion("userstamp in", values, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampNotIn(List<String> values) {
			addCriterion("userstamp not in", values, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampBetween(String value1, String value2) {
			addCriterion("userstamp between", value1, value2, "userstamp");
			return (Criteria) this;
		}

		public Criteria andUserstampNotBetween(String value1, String value2) {
			addCriterion("userstamp not between", value1, value2, "userstamp");
			return (Criteria) this;
		}

		public Criteria andDatestampIsNull() {
			addCriterion("datestamp is null");
			return (Criteria) this;
		}

		public Criteria andDatestampIsNotNull() {
			addCriterion("datestamp is not null");
			return (Criteria) this;
		}

		public Criteria andDatestampEqualTo(Date value) {
			addCriterion("datestamp =", value, "datestamp");
			return (Criteria) this;
		}

		public Criteria andDatestampNotEqualTo(Date value) {
			addCriterion("datestamp <>", value, "datestamp");
			return (Criteria) this;
		}

		public Criteria andDatestampGreaterThan(Date value) {
			addCriterion("datestamp >", value, "datestamp");
			return (Criteria) this;
		}

		public Criteria andDatestampGreaterThanOrEqualTo(Date value) {
			addCriterion("datestamp >=", value, "datestamp");
			return (Criteria) this;
		}

		public Criteria andDatestampLessThan(Date value) {
			addCriterion("datestamp <", value, "datestamp");
			return (Criteria) this;
		}

		public Criteria andDatestampLessThanOrEqualTo(Date value) {
			addCriterion("datestamp <=", value, "datestamp");
			return (Criteria) this;
		}

		public Criteria andDatestampIn(List<Date> values) {
			addCriterion("datestamp in", values, "datestamp");
			return (Criteria) this;
		}

		public Criteria andDatestampNotIn(List<Date> values) {
			addCriterion("datestamp not in", values, "datestamp");
			return (Criteria) this;
		}

		public Criteria andDatestampBetween(Date value1, Date value2) {
			addCriterion("datestamp between", value1, value2, "datestamp");
			return (Criteria) this;
		}

		public Criteria andDatestampNotBetween(Date value1, Date value2) {
			addCriterion("datestamp not between", value1, value2, "datestamp");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_sys_user
     *
     * @mbg.generated do_not_delete_during_merge Mon Jun 01 18:05:55 GMT+08:00 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}