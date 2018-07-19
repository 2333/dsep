<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div id="hori_menu">
	<ul id="menu_holder">
		<!-- 动态生成菜单权限 -->
		<c:forEach items="${tree}" var="tree" varStatus="status">
			<li class="left">
							
				<a class="hori_lvl1" href="${tree.url}"><span>${tree.name}</span></a>
				
				<c:if test="${!empty tree.children}">				
					<ul class="hori_lvl2">
						<li>
							<c:forEach items="${tree.children}" var="children">
								<a href="${children.url}">${children.name}</a>
							</c:forEach>
						</li>
					</ul>
				</c:if>
			</li>
		</c:forEach>		
		<div class="clear"></div>
	</ul>
</div>
