<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>Title</title>

<link rel="stylesheet" href="../css/reset.css">
<link rel="stylesheet" href="../css/common.css">
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/login.css">
<link rel="shortcut icon" href="../imgs/instagram.png">

</head>
<body>

	<section id="container">

    <div id="main_container">

        <div class="form_container">

            <div class="form">


                <h1 class="sprite_insta_big_logo title"></h1>

                <form action="/userCtrl/insert" method="POST">
                    <p class="login_user_name">
                        <input type="text" name="email" id="email" placeholder="이메일 주소" required>
                        <button type="button" class="btn btn-sm" id="checkEmail">중복확인</button>
                    </p>
                    
                    <p class="login_user_password">
                        <input type="text" name="age" id="age" placeholder="나이" required>
                    </p>

                    <p class="login_user_password">
                        <input type="text" name="name" id="name" placeholder="성명" required>
                    </p>
                    
                    <p class="login_user_password">
                        <input type="text" name="nickName" id="nickName" placeholder="사용자이름" required>
                    </p>
                    
                    <p class="login_user_password">
                        <input type="text" name="pwd" id="pwd" placeholder="비밀번호" required>
                    </p>
                    
                    <p class="login_user_password">
                        <input type="hidden" name="isAdmin" id="isAdmin" value="false" required>
                    </p>

                    <input type="submit" value="회원가입" class="submit_btn" id="reg">
                </form>



            </div>

            <div class="bottom_box">
                <div>
                    <span>계정이 있으신가요?</span><a href="/index.jsp">로그인</a>
                </div>
            </div>


        </div>

    </div>


</section>


<script src="../js/register.js"></script>
</body>
</html>
