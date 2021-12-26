<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="shortcut icon" href="../imgs/instagram.png">
<script src="../js/nav.js"></script>

</head>
<body>

	<section id="container">

		<header id="header">
			<section class="inner">

				<h1 class="logo">
					<a href="/postCtrl/list">
						<div class="sprite_insta_icon"></div>
						<div class="sprite_write_logo"></div>
					</a>
				</h1>

				<div class="search_box">
					<input type="text" placeholder="검색" id="search-field">

					<div class="fake_field">
						<span class="sprite_small_search_icon"></span> <span>검색</span>
					</div>
				</div>

				<div class="right_icons">
					<a data-bs-toggle="modal" data-bs-target="#regModal"><div class="sprite_camera_icon"></div></a>
					<a href="login.html"><div class="sprite_compass_icon"></div></a> 
					<a href="/postCtrl/mylist?email=${ses.email }"><div class="sprite_heart_icon_outline"></div></a>
					<button type="button"
						class="sprite_user_icon_outline dropdown-toggle"
						data-bs-toggle="dropdown"></button>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="/userCtrl/profile?email=${ses.email }">프로필</a></li>
						<li><a class="dropdown-item" href="/userCtrl/detail?email=${ses.email }">프로필 편집</a></li>
						<li><a class="dropdown-item" href="/userCtrl/changePwd?email=${ses.email }">비밀번호 변경</a></li>
						<li><a class="dropdown-item" href="/userCtrl/changeAvatar?email=${ses.email }">프로필사진 변경</a></li>
						<c:if test="${access_token ne null }">
						<li><a class="dropdown-item" href="/userCtrl/kakaoUnlink?">카카오톡 연결끊기</a></li>
						</c:if>
						<li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item" href="/userCtrl/logout">로그아웃</a></li>
					</ul>
				</div>

			</section>

		</header>