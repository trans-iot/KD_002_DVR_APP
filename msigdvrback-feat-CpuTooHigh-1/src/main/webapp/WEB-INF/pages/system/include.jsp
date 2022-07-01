
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cw" uri="http://www.com.tw/tag/util" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:set var="jsVer" value="1.0" />
<spring:eval expression="T(tw.msigDvrBack.common.AjaxResultEnum).QUERY_FAIL" var="ajaxQueryFail" />
<spring:eval expression="T(tw.msigDvrBack.common.AjaxResultEnum).INSERT_FAIL" var="ajaxInsertFail" />
<spring:eval expression="T(tw.msigDvrBack.common.AjaxResultEnum).UPDATE_FAIL" var="ajaxUpdateFail" />
<spring:eval expression="T(tw.msigDvrBack.common.AjaxResultEnum).COPY_FAIL" var="ajaxCopyFail" />
<spring:eval expression="T(tw.msigDvrBack.common.AjaxResultEnum).DELETE_FAIL" var="ajaxDeleteFail" />
<spring:eval expression="T(tw.msigDvrBack.common.AjaxResultEnum).OK" var="ajaxOk" />
<spring:eval expression="T(tw.msigDvrBack.common.AjaxResultEnum).FAIL" var="ajaxFail" />
<spring:eval expression="T(tw.msigDvrBack.common.AjaxResultEnum).LOGIN_TIMEOUT" var="ajaxLoginTimeout" />
<spring:eval expression="T(tw.msigDvrBack.common.CwConstants).SPLIT_CHAR" var="splitChar" />
<spring:eval expression="T(tw.msigDvrBack.common.CwConstants).ARRAY_CHAR" var="arrayChar" />

<c:set var="prevPage"><spring:message code="page.previous.label"/></c:set>
<c:set var="nextPage"><spring:message code="page.next.label"/></c:set>
<c:set var="firstPage"><spring:message code="page.first.label"/></c:set>
<c:set var="lastPage"><spring:message code="page.last.label"/></c:set>  
<c:set var="firstImg"><div class="button-page" title='${firstPage}' >&lt;&lt;</div></c:set>
<c:set var="leftImg"><div class="button-page" title='${prevPage}' >&lt;</div></c:set>
<c:set var="rightImg"><div class="button-page" title='${nextPage}'>&gt;</div></c:set>
<c:set var="lastImg"><div class="button-page" title='${lastPage}'>&gt;&gt;</div></c:set>
<c:set var="firstImgEnd"><div class="button-page-end">&lt;&lt;</div></c:set>
<c:set var="leftImgEnd"><div class="button-page-end">&lt;</div></c:set>
<c:set var="rightImgEnd"><div class="button-page-end">&gt;</div></c:set>
<c:set var="lastImgEnd"><div class="button-page-end">&gt;&gt;</div></c:set>
<c:if test="${empty totalCount}">
    <c:set var="totalCount" value="0" />
</c:if>

<jsp:useBean id="today" class="java.util.Date" scope="page" />

<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>