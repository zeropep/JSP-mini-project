<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <title>instagram</title>
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/login.css">
    <link rel="shortcut icon" href="imgs/instagram.png">

</head>
<body>
<c:if test="${ses.email ne '' && ses.email ne null  }">
	<a href="/post/list.jsp" id="list" style="display: none;"></a>
</c:if>

<section id="container">

    <div id="main_container">

        <div class="form_container">

            <div class="form">


                <h1 class="sprite_insta_big_logo title"></h1>

                <form action="/userCtrl/login">
                    <p class="login_user_name">
                        <input type="text" name="email" placeholder="이메일" id="email">
                    </p>

                    <p class="login_user_password">
                        <input type="text" name="pwd" placeholder="비밀번호" id="pwd">
                    </p>

                    <input type="submit" value="로그인" class="submit_btn">
                </form>



            </div>

            <div class="bottom_box">
            
                <a href="https://kauth.kakao.com/oauth/authorize?client_id=47aebbe977c3814499235ae9556522cc&redirect_uri=http://localhost:8081/userCtrl/kakaologin&response_type=code" id="kakao"><img src="../imgs/kakao_login_medium_narrow.png"></a>
            </div>
            
            <div class="bottom_box">
            
                <div>
                    <span>아이디가 없으신가요?</span><a href="/userCtrl/register">회원가입</a>
                </div>
            </div>


        </div>

    </div>


</section>


<!-- <script src="../js/index.js"></script> -->
<script>
	let msg_u_login = '<c:out value="${msg_u_login }"/>';
	if (msg_u_login.length > 0) {
		alert("로그인 실패");
	}
</script>
</body>
</html>
