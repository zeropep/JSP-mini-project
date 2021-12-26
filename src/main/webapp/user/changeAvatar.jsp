<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../header1.jsp" />
<link rel="stylesheet" href="../css/login.css">
<jsp:include page="../header2.jsp" />

<div id="main_container">
	<div class="form_container">
		<div class="form">
			<h1 class="sprite_insta_big_logo title"></h1>
			<form action="/userCtrl/modifyAvatar" method="POST" id="form" enctype="multipart/form-data">
				<p class="login_user_name">
					<input type="hidden" name="email" value="${ses.email }">
					<c:if test="${ses.avatar ne null || ses.avatar ne '' }">
					<input type="hidden" name="avatar" value="${ses.avatar}">
					</c:if>
				</p>
				<p class="login_user_password">
					<input type="file" name="profileImage" accept="image/png, image/jpeg, image/jpg, image/gif">
				</p>

				<input type="submit" value="제출" class="submit_btn">
			</form>
		</div>
	</div>
</div>
</section>


</body>
</html>
