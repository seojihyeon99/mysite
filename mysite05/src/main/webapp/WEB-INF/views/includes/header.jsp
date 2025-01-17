<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
window.addEventListener("load", function() {
	var anchors = document.querySelectorAll('#languages a');
	anchors.forEach(function(el) {
		el.addEventListener("click", function(event) {
			event.preventDefault();
			document.cookie =
				"lang=" + this.getAttribute('data-lang') + ";" +
				"path=" + "${pageContext.request.contextPath }" + ";" +
				"max-age=" + (30*24*60*60);
			
			location.reload();
		})	
	});
});
</script>
		<div id="header">
			<!-- ${siteVo.title } -->
			<h1>${site.title }</h1>
			<div id="languages">
				<c:choose>
					<c:when test="${lang == 'en' }">
						<a href="" data-lang="ko">KO</a><a href="" class="active" data-lang="en">EN</a>
					</c:when>
					<c:otherwise>
						<a href="" data-lang="ko" class="active">KO</a><a href="" data-lang="en">EN</a>
					</c:otherwise>
				</c:choose>
			</div>			
			<ul>
				<c:choose>
					<c:when test="${empty authUser }" >
						<li><a href="${pageContext.request.contextPath }/user/login"><spring:message code="header.gnb.login"/></a></li>
						<li><a href="${pageContext.request.contextPath }/user/join"><spring:message code="header.gnb.join"/></a></li>
					</c:when>
					<c:otherwise>			
						<li><a href="${pageContext.request.contextPath }/user/update"><spring:message code="header.gnb.settings"/></a></li>
						<li><a href="${pageContext.request.contextPath }/user/logout"><spring:message code="header.gnb.logout"/></a></li>
						<li><spring:message code="header.gnb.greeting"/> ${authUser.name }</li>
					</c:otherwise>
				</c:choose>	
			</ul>
		</div>