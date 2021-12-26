<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header1.jsp" />
<link rel="stylesheet" href="../css/profile.css">
<jsp:include page="../header2.jsp" />

<div class="hidden_menu">
	<div class="scroll_inner">
		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">asdfasdf</div>
		</div>

		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">kindtigerrr</div>
		</div>
		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">kindtigerrr</div>
		</div>
		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">kindtigerrr</div>
		</div>
		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">kindtigerrr</div>
		</div>
		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">kindtigerrr</div>
		</div>
		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">kindtigerrr</div>
		</div>
		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">kindtigerrr</div>
		</div>
		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">kindtigerrr</div>
		</div>
		<div class="user">
			<div class="thumb_img">
				<img src="imgs/thumb.jpeg" alt="프로필사진">
			</div>
			<div class="id">kindtigerrr</div>
		</div>

	</div>
</div>


<section id="main_container">
	<div class="inner">
		<!-- 세션 이메일 -->
		<input type="hidden" id="userSession" name="userSession"
			value="${ses.email }">
		<!-- 게시물 반복 시작 -->
		<div id="postList">
			<c:if test="${empty postList }">
				<h2>게시글이 없습니다.</h2>
			</c:if>
			<c:forEach items="${postList }" var="post">
				<div class="contents_box" id="post${post.postId }">
					<article class="contents">
						<header class="top" style="width: 614px;">
							<div class="user_container">
								<div class="profile_img">
									<img class="avatar"
										src="../_fileUpload/avatar/${empty post.avatar ? 'default_avatar.jpeg' : post.avatar }"
										alt="" />
								</div>
								<div class="user_name">
									<div class="nick_name m_text">
										<a class="decoration_none"
											href="/userCtrl/profile?email=${post.writer }">${post.writer }</a>
									</div>
									<div class="country s_text">Seoul, South Korea
										${post.postId }</div>
								</div>

							</div>

							<div class="sprite_more_icon" data-name="more" id="toggle_more">
								<ul class="toggle_box">
									<!-- 본인 작성 글일때만 수정삭제 가능, 그 외엔 팔로우 버튼 노출 -->
									<c:choose>
										<c:when test="${ses.email == post.writer }">
											<li id="updatePost" data-pid="${post.postId }"><a
												data-bs-toggle="modal" data-bs-target="#updateModal">수정</a></li>
											<li id="delPost" data-pid="${post.postId }">삭제</li>
										</c:when>
										<c:otherwise>
											<c:set value="팔로우" var="text" />
											<c:forEach items="${followingList }" var="each">
												<c:if test="${each.email == post.writer }">
													<c:set value="언팔로우" var="text" />
												</c:if>
											</c:forEach>
											<li><input type="submit" class="follow" value="${text}"
												data-email="${post.writer }"></li>
										</c:otherwise>
									</c:choose>

								</ul>
							</div>
						</header>

						<div class="img_section">
							<div class="trans_inner">
								<div>
									<a href="/postCtrl/detail?pid=${post.postId }"
										id="detailToggle" data-pid="${post.postId }"> <c:choose>
											<c:when test="${empty post.files }">
												<img src="../imgs/img_section/img01.jpg" alt="visual01">
											</c:when>
											<c:otherwise>
												<img src="../_fileUpload/post/${post.files}" alt="visual01">
											</c:otherwise>
										</c:choose>
									</a>
								</div>
							</div>
						</div>

						<div class="bottom_icons">
							<div class="left_icons" style="align-items: center">
								<div class="heart_btn">
									<a id="likeBtn" data-pid="${post.postId}"> <!-- like테이블에서 목록을 가져온 후, 처리를 어떻게? -->
										<c:set var="heart" value="bi-heart" /> <c:forEach
											items="${likeList }" var="likedPost">
											<c:if test="${post.postId == likedPost.postId }">
												<c:set var="heart" value="bi-heart-fill red" />
											</c:if>
										</c:forEach>
										<div class="bi ${heart } icon" name="39"
											data-pid="${post.postId }"></div>
									</a>
								</div>
								<div class="sprite_bubble_icon"></div>
								<div class="sprite_share_icon" data-name="share"></div>
							</div>
							<div class="right_icon">
								<div class="sprite_bookmark_outline" data-name="bookmark"></div>
							</div>
						</div>

						<div class="likes m_text">
							좋아요 <span id="like-count${post.postId }">${post.likeCnt}</span> <span
								id="bookmark-count-39"></span> 개
						</div>
						<div id="comment_area${post.postId}">
							<c:set value="cmt${post.postId }" var="pid" />
							<c:forEach items="${requestScope[pid]}" var="p" varStatus="st"
								begin="0">
								<div class="comment_container">
									<div class="comment" id="comment-list-ajax-post37">
										<div class="comment-detail">
											<div class="nick_name m_text">${p.writer }</div>
											<div>${p.content }</div>

										</div>
									</div>
									<!-- <div class="small_heart">
										<div class="sprite_small_heart_icon_outline"></div>
									</div> -->
								</div>
							</c:forEach>
						</div>
						<c:set value="${post.modAt == post.regAt ? '' : '수정됨' }" var="txt" />
						<div class="timer">${post.regAt}</div>

						<div class="comment_field" id="add-comment-post37">
							<input id="commentInput" type="text" placeholder="댓글달기..."
								name="comment">
							<div id="commentUploadBtn" class="upload_btn m_text"
								data-pid="${post.postId}">게시</div>
						</div>
					</article>
				</div>
			</c:forEach>
			<div class="page">
		<c:forEach begin="1" end="${ Math.ceil(cnt / 5) }" varStatus="st">
			<a href="/postCtrl/list?page=${st.count }&query=${param.query}">${st.count }</a>
		</c:forEach>
			</div>
		</div>
		<!-- 게시물 반복 끝 -->

		<!-- register모달 -->
		<div class="modal" id="regModal">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">새 게시물 만들기</h4>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<img alt="" src="" id="registerImgView">
						<form action="/postCtrl/insert" method="post" id="registerImgFile" enctype="multipart/form-data">
							<input type="file" name="imgFile" value="" accept="image/x-png, image/gif, image/jpeg"> 
							<input type="text" name="content" value=""> 
							<input type="text" name="writer" readonly value="${ses.email }">
							<button type="submit">submit</button>
						</form>

					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-danger"
							data-bs-dismiss="modal">Close</button>
					</div>

				</div>
			</div>
		</div>

		<!-- update modal -->
		<div class="modal" id="updateModal">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">게시물 수정</h4>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<img id="updateImgView" alt="" src="">
						<form action="/postCtrl/update" method="post"
							enctype="multipart/form-data">
							<input type="hidden" id="prevImgFile" name="prevImgFile" value="">
							<input type="file" name="imgFile" id="updateImgFile" value="" accept="image/x-png, image/gif, image/jpeg"> 
							<input type="hidden" name="pid" id="updatePid" value=""> 
							<input type="text" name="content" id="updateContent" value="">
							<input type="text" readonly name="writer" value="${ses.email }">
							<button type="submit">submit</button>
						</form>

					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-danger"
							data-bs-dismiss="modal">Close</button>
					</div>

				</div>
			</div>
		</div>
		<!--  -->
		<input type="hidden" id="page" value="1">

		<div class="side_box">
			<div class="user_profile">
				<div class="profile_thumb">
					<img
						src="../_fileUpload/avatar/${empty ses.avatar ? 'default_avatar.jpeg'  : ses.avatar }"
						alt="프로필사진">
				</div>
				<div class="detail">
					<div class="id m_text">${ses.nickname }(${ses.email })</div>
					<div class="ko_name">${ses.name }</div>
				</div>
			</div>

			<article class="story">
				<header class="story_header">
					<div>스토리</div>
					<div class="more">모두 보기</div>
				</header>

				<div class="scroll_inner" id="followingList">

					<c:forEach items="${followingList }" var="following">
						<div class="thumb_user" data-followuser="${following.email }">
							<div class="profile_thumb">
								<img
									src="../_fileUpload/avatar/${empty following.avatar ? 'default_avatar.jpeg' : following.avatar }"
									alt="프로필사진">
							</div>
							<div class="detail">
								<div class="id">
									<a class="decoration_none"
										href="/userCtrl/profile?email=${following.email }">${following.email}</a>
								</div>
								<div class="time">1시간 전</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</article>

		</div>
	</div>
</section>
</section>

<script src="../js/list.js"></script>
</body>
</html>