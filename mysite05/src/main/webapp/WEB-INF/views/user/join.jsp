<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	$("#btn-check").click(function() {
		var email = $("#email").val();
		if(email == "") {
			return;
		}
		
		$.ajax({
			url: "${pageContext.request.contextPath}/api/user/checkemail?email=" + email,
			type: "get",
			dataType: "json",
			success: function(response) {
				if(response.result != "success") {
					console.error(response.message);
					return;
				}
				
				if(response.data.exist) {
					alert("이메일이 존재합니다. 다른 이메일을 사용해 주세요.");
					$("#email").val("");
					$("#email").focus();
					
					return;
				}
				
				$("#img-check").show();
				$("#btn-check").hide();
			},
			error: function(xhr, status, err) {
				console.error(err);
			}
		});
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form:form
					modelAttribute="userVo"
					id="join-form"
					name="joinForm"
					method="post"
					action="${pageContext.request.contextPath}/user/join">
					
					<label class="block-label" for="name"><spring:message code="user.join.label.name" /></label>
					<form:input path="name" />
					<p style="color:#f00; text-align:left; padding:0">
						<form:errors path="name" />
					</p>
					
					<spring:message code="user.join.label.email.check" var="userJoinLabelEmailCheck" />
					<label class="block-label" for="email"><spring:message code="user.join.label.email" /></label>
					<form:input path="email" />
					<input id="btn-check" type="button" value="${userJoinLabelEmailCheck }" style="display:;">
					<img id="img-check" src="${pageContext.request.contextPath}/assets/images/check.png" style="vertical-align:bottom; width:24px; display: none">
					<p style="color:#f00; text-align:left; padding:0">
						<form:errors path="email" />
					</p>					
					
					<label class="block-label"><spring:message code="user.join.label.password" /></label>
					<form:password path="password" />
					<p style="color:#f00; text-align:left; padding:0">
						<form:errors path="password" />
					</p>					

					<spring:message code="user.join.label.gender.male" var="userJoinLabelGenderMale"/>
					<spring:message code="user.join.label.gender.female" var="userJoinLabelGenderFemale"/>
					<fieldset>
						<legend><spring:message code="user.join.label.gender" /></legend>
						<form:radiobutton path="gender" value="female" label="${userJoinLabelGenderFemale }" checked="checked" />
						<form:radiobutton path="gender" value="male" label="${userJoinLabelGenderMale }"/>
					</fieldset>
					
					<fieldset>
						<legend><spring:message code="user.join.label.terms" /></legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label><spring:message code="user.join.label.terms.message" /></label>
					</fieldset>
					
					<spring:message code="user.join.button.signup" var="userJoinButtonSignup"/>
					<input type="submit" value="${userJoinButtonSignup }">		
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
