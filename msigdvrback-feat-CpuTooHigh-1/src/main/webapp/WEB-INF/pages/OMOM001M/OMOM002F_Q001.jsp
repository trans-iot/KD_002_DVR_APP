<!-- 
    title 會員服務異動
    version 1.0 
    author Bob
    since 2018/10/31
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<script>
	
	</script>
</head>
<body>

<div class="mainDiv">
<br/><br/><br/>
	<c:if test="${not empty tbCustCar}">
		<div id="blockB">
	        <table class="resultTable" id="mainTable">
	            <tr>
					<!-- 車號 -->
					<th><spring:message code="omom002f.header9.label"/>：</th>
					<td>
						<input type="text"  id="carNo" 
								name="carNo" value="${tbCustCar.carNo}" maxlength="20" />
					</td>
					<!-- 保險到期日 -->
					<th><spring:message code="omom002f.header31.label"/>：</th>
					<td>
						<input type="text"  id="effUbiDate" 
								name="effUbiDate" value="<fmt:formatDate value="${tbCustCar.effUbiDate}" type="Both" pattern="yyyy/MM/dd"/>" maxlength="20" />
					</td>
				</tr>
				<tr>
					<!-- VIP類別 -->
					<th><spring:message code="omom002f.header11.label"/>：</th>
					<td>
						<input type="text"  id="vipType" 
								name="vipType" value="${tbCustCar.vipType}" maxlength="20" />
					</td>
					<!-- 保險到期日 -->
					<th><spring:message code="omom002f.header10.label"/>：</th>
					<td>
						<input type="text"  id="endUbiDate" 
								name="endUbiDate" value="<fmt:formatDate value="${tbCustCar.endUbiDate}" type="Both" pattern="yyyy/MM/dd"/>" maxlength="20" />
					</td>
				</tr>
				<tr>
					<!-- 推薦代碼 -->
					<th><spring:message code="omom002f.header13.label"/>：</th>
					<td>
						<input type="text"  id="promCode" 
								name="promCode" value="${tbCustCar.promCode}" maxlength="20" />
					</td>
					<!-- 服務人員 -->
					<th><spring:message code="omom002f.header12.label"/>：</th>
					<td>
						<input type="text"  id="servicer" 
								name="servicer" value="${tbCustCar.servicer}" maxlength="20" />
					</td>
				</tr>
				<tr>
					<!-- 起始里程數 -->
					<th><spring:message code="omom002f.header15.label"/>：</th>
					<td>
						<input type="text"  id="effMileage" 
								name="effMileage" value="${tbCustCar.effMileage}" maxlength="20" />
					</td>
					<!-- 起始里程計算日期 -->
					<th><spring:message code="omom002f.header16.label"/>：</th>
					<td>
						<input type="text"  id="effMileageDate" 
								name="effMileageDate" value="<fmt:formatDate value="${tbCustCar.effMileageDate}" type="Both" pattern="yyyy/MM/dd"/>" maxlength="20" />
					</td>
				</tr>
				<tr>
					<!-- 起始里程數照片URL -->
					<th><spring:message code="omom002f.header17.label"/>：</th>
					<td colspan="3">
						<c:if test="${tbCustCar.effMileageImg ne null}">
							<a href="${tbCustCar.effMileageImg}"  target="_blank">
								<input type="text"  id="effMileageImg" 
									name="effMileageImg" value="${tbCustCar.effMileageImg}" maxlength="20"   style=" cursor: pointer"/>
							</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<!-- 結束里程數 -->
					<th><spring:message code="omom002f.header18.label"/>：</th>
					<td>
						<input type="text"  id="endMileage" 
								name="endMileage" value="${tbCustCar.endMileage}" maxlength="20" />
					</td>
					<!-- 結束里程計算日期 -->
					<th><spring:message code="omom002f.header19.label"/>：</th>
					<td>
						<input type="text"  id="endMileageDate" 
								name="endMileageDate" value="<fmt:formatDate value="${tbCustCar.endMileageDate}" type="Both" pattern="yyyy/MM/dd"/>" maxlength="20" />
					</td>
				</tr>
				<tr>
					<!-- 結束里程數照片URL -->
					<th><spring:message code="omom002f.header20.label"/>：</th>
					<td colspan="3">
						<c:if test="${tbCustCar.endMileageImg ne null}">
							<a href="${tbCustCar.endMileageImg}"  target="_blank">
								<input type="text"  id="endMileageImg" 
										name="endMileageImg" value="${tbCustCar.endMileageImg}" maxlength="20"  style=" cursor: pointer"/>
							</a>
						</c:if>
					</td>
				</tr>
	        </table>    
	    </div>
	    
	</c:if>
</div>
</body>
</html>