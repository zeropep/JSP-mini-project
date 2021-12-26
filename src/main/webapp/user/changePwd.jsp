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
			<form action="/userCtrl/modifyPwd" method="POST" id="form">
				<p class="login_user_name">
					<input type="hidden" id="email" name="email" value="${ses.email }">
				</p>

				<p class="login_user_password">
					<input type="text" id="pwd" name="pwd" placeholder="이전 비밀번호">
				</p>

				<p class="login_user_password">
					<input type="text" id="newPwd" name="newPwd" placeholder="새 비밀번호">
				</p>

				<p class="login_user_password">
					<input type="text" id="newPwdConfirm" name="newPwdConfirm"
						placeholder="새 비밀번호 확인">
				</p>

				<input type="submit" value="제출" class="submit_btn">
			</form>
		</div>
	</div>
</div>
</section>


<!-- <script src="../js/insta.js"></script> -->
<script src="../js/changePwd.js"></script>
</body>
</html>
