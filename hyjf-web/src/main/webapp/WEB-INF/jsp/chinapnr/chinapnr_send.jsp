<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="fm" name="fm" action='${empty chinapnrForm.action ? "/hyjf-web/error/systemerror.jsp" : chinapnrForm.action}' method="post">
	<c:if test='${!empty chinapnrForm.allParams }'>
		<c:forEach items='${chinapnrForm.allParams }' var="record" begin="0" step="1" varStatus="status">
			<input type="hidden" size=800 name='${record.key }' id='${record.key }' value='<c:out value='${record.value}' escapeXml="true" />'/><br>
		</c:forEach>
	</c:if>
</form>
<script>
document.getElementById("fm").submit();
</script>
