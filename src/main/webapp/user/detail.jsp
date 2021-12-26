<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../header1.jsp" />
<link rel="stylesheet" href="../css/login.css">
<jsp:include page="../header2.jsp" />

<div id="main_container">
	<div class="form_container">
		<div class="detail">
			<div class="top">
			</div>
		</div>
		<div class="form">
			<h1 class="sprite_insta_big_logo title"></h1>
			<form action="/userCtrl/modify">
				<p class="login_user_name">
					<input type="hidden" name="email" id="email" value="${uvo.email }">
				</p>

				<p class="login_user_password">
					<input type="text" name="age" placeholder="나이" value="${uvo.age }">
				</p>

				<p class="login_user_password">
					<input type="text" name="name" placeholder="성명"
						value="${uvo.name }">
				</p>

				<p class="login_user_password">
					<input type="text" name="nickName" placeholder="사용자이름"
						value="${uvo.nickname }">
				</p>

				<p class="login_user_password">
					<input type="hidden" name="isAdmin" value="false">
				</p>

				<input type="submit" value="제출" class="submit_btn">
			</form>
		</div>
		<button type="button" class="btn btn-danger" id="del">계정삭제</button>
	</div>
</div>
</section>

<script src="../js/remove.js"></script>
</body>
</html>
