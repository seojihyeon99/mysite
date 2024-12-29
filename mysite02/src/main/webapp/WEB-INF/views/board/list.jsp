<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="get">
					<input type="text" id="keyword" name="keyword" value="${keyword}">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list}" var="vo" varStatus="status">
						<tr>
							<td>[${navigation.totalCount - (start + status.index)}]</td>
							<td style="text-align:left; padding-left:${vo.depth * 20}px">
								<c:if test="${vo.depth > 0}">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if>
								<a href="${pageContext.request.contextPath}/board?a=view&id=${vo.id}">${vo.title}</a>
							</td>
							<td>${vo.name}</td>
							<td>${vo.hit}</td>
							<td>${vo.regDate}</td>
							<td>
								<c:if test="${not empty authUser and authUser.id == vo.userId}">
									<a href="${pageContext.request.contextPath}/board?a=delete&id=${vo.id}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<c:set var="currentPage" value="${navigation.currentPage}" />
				<c:set var="totalPageCount" value="${navigation.totalPageCount}" />
				<c:set var="naviSize" value="${navigation.naviSize}" />
				<c:set var="startPage" value="${navigation.startPage}" />
				<c:set var="endPage" value="${navigation.endPage}" />
				<c:if test="${endPage > totalPageCount}">
				    <c:set var="endPage" value="${totalPageCount}" />
				</c:if>
				<div class="pager">
					<ul>
						<!-- 이전 버튼 -->
						<li>
						    <c:choose>
						        <c:when test="${navigation.startRange}">
						            <!-- 버튼 비활성화 -->
						            <a href="#" class="disabled">◀</a>
						        </c:when>
						        <c:otherwise>
						            <!-- 버튼 활성화 -->
        							<c:if test="${not empty keyword && keyword != ''}">
							            <a href="${pageContext.request.contextPath}/board?page=${startPage > 1 ? startPage - 1 : 1}&keyword=${keyword}">◀</a>
									</c:if>
									<c:if test="${empty keyword || keyword == ''}">
							            <a href="${pageContext.request.contextPath}/board?page=${startPage > 1 ? startPage - 1 : 1}">◀</a>
									</c:if>
						        </c:otherwise>
						    </c:choose>
						</li>
						
						<!-- 페이지 번호 -->
				        <c:forEach var="i" begin="${startPage}" end="${endPage}">
				            <c:choose>
				                <c:when test="${currentPage == i}">
				                    <li class="selected">${i}</li>
				                </c:when>
				                <c:otherwise>
									<c:if test="${not empty keyword && keyword != ''}">
									    <li><a href="${pageContext.request.contextPath}/board?page=${i}&keyword=${keyword}">${i}</a></li>
									</c:if>
									<c:if test="${empty keyword || keyword == ''}">
									    <li><a href="${pageContext.request.contextPath}/board?page=${i}">${i}</a></li>
									</c:if>
				                </c:otherwise>
				            </c:choose>
				        </c:forEach>
				        						
				       	<!-- 다음 버튼 -->
				        <li>
						    <c:choose>
						        <c:when test="${navigation.endRange}">
						            <!-- 버튼 비활성화 -->
						            <a href="#" class="disabled">▶</a>
						        </c:when>
						        <c:otherwise>
						            <!-- 버튼 활성화 -->
        							<c:if test="${not empty keyword && keyword != ''}">
							            <a href="${pageContext.request.contextPath}/board?page=${endPage < totalPageCount ? endPage + 1 : totalPageCount}&keyword=${keyword}">▶</a>
									</c:if>
									<c:if test="${empty keyword || keyword == ''}">
										<a href="${pageContext.request.contextPath}/board?page=${endPage < totalPageCount ? endPage + 1 : totalPageCount}">▶</a>
									</c:if>
						        </c:otherwise>
						    </c:choose>				        
				        </li>
					</ul>
				</div>					
				<!-- pager 추가 -->	
							
				<div class="bottom">
					<c:if test="${not empty authUser}">
						<a href="${pageContext.request.contextPath}/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>