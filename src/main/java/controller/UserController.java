package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.KakaoAPI;
import api.pwdHash;
import domain.CommentVO;
import domain.PostVO;
import domain.UserVO;
import net.coobird.thumbnailator.Thumbnails;
import service.CommentService;
import service.CommentServiceImple;
import service.LikeService;
import service.LikeServiceImple;
import service.PostService;
import service.PostServiceImple;
import service.UserService;
import service.UserServiceImple;

@WebServlet("/userCtrl/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private final UserService usv;
	private final PostService psv;
	private final CommentService csv;
	private final LikeService lsv;
	private RequestDispatcher rdp;
	private int isUp;
	private String UTF8 = "utf-8";
	private UserVO uvo;
	private PostVO pvo;
	private PrintWriter out;
	private KakaoAPI kakaoApi = new KakaoAPI();
	private pwdHash hash = new pwdHash();

	public UserController() {
		usv = new UserServiceImple();
		psv = new PostServiceImple();
		csv = new CommentServiceImple();
		lsv = new LikeServiceImple();
		
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding(UTF8);
		res.setCharacterEncoding(UTF8);
		res.setContentType("text/html; charset=utf-8");

		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/") + 1);

		// 세션에 유저가 없으면 로그인페이지로
		switch (path) {
		case "kakaologin":
			log.info(req.getParameter("code"));
			String accessToken = kakaoApi.getAccessToken(req.getParameter("code"));
			HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

			log.info("login_info : {}", userInfo.toString());

			if (userInfo.get("email") == null) {
				req.setAttribute("msg_kakao_login", 0);
				req.getRequestDispatcher("/index.jsp").forward(req, res);
				break;
			}
			String email = userInfo.get("email").toString();
			
			// 기존의 회원인지 체크
			isUp = usv.checkEmail(email);
			
			// 기존회원이 아니면 레지스터
			String emptyPwd = "";
			try {
				emptyPwd = hash.bytesToHex(hash.sha256(""));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			log.info("기존회원인가요? {}", isUp > 0 ? "yes" : "no");
			if (isUp < 1) {
				usv.register(new UserVO(email, 0, "", emptyPwd, false, ""));
			}
			
			// 로그인시키기
			uvo = usv.logInWithKakao(email);
			if (uvo != null) {
				HttpSession ses = req.getSession();
				ses.setAttribute("ses", uvo);
				ses.setAttribute("access_token", accessToken);
				if (req.getParameter("iska") != null) {
					ses.setAttribute("isSocial", true);
				}
				ses.setMaxInactiveInterval(60 * 20);
			}
			
			if (uvo.getPwd().equals(emptyPwd)) {
				req.getRequestDispatcher("/userCtrl/changePwd?email=" + email).forward(req, res);
			} else {
				req.getRequestDispatcher("/postCtrl/list").forward(req, res);				
			}
			break;
		case "kakaoUnlink":
			HttpSession kakaoSes = req.getSession();
			log.info(kakaoSes.getAttribute("accessToken").toString());
			
			break;
		case "register":
			req.getRequestDispatcher("/user/register.jsp").forward(req, res);
			break;
		case "insert":
			String loginPwd = "";
			try {
				loginPwd = hash.bytesToHex(hash.sha256(req.getParameter("pwd")));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			isUp = usv.register(new UserVO(req.getParameter("email"), Integer.parseInt(req.getParameter("age")),
					req.getParameter("name"), loginPwd,
					Boolean.parseBoolean(req.getParameter("isAdmin")), req.getParameter("nickName")));
			log.info(">> register {}", isUp > 0 ? "Success" : "Fail");
			res.sendRedirect("/");
			break;
		case "profile":
			uvo = usv.getDetail(req.getParameter("email"));
			List<String> follower = new ArrayList<>();
			List<String> following = new ArrayList<>();
			
			if (uvo.getFollower() != null && uvo.getFollower().length() != 0) {
				for (String folwer : uvo.getFollower().split(",")) {
					follower.add(folwer);
				}
			}
			if (uvo.getFollowing() != null && uvo.getFollowing().length() != 0) {
				for (String foling : uvo.getFollowing().split(",")) {
					following.add(foling);
				}
			}
			log.info(Integer.toString(following.size()));
			
			req.setAttribute("follower", follower);
			req.setAttribute("following", following);
			req.setAttribute("uvo", usv.getDetail(req.getParameter("email")));
			req.getRequestDispatcher("/user/profile.jsp").forward(req, res);
			break;
		case "postlist":
				ArrayList<PostVO> list = (ArrayList<PostVO>) psv.getListForProfile(req.getParameter("email"));
				for (PostVO pvo : list) {
					log.info(pvo.getFiles());
				}
				JSONObject[] jsonObjArr = new JSONObject[list.size()];
				JSONArray jsonObjList = new JSONArray();
				for (int i = 0; i < list.size(); i++) {
					jsonObjArr[i] = new JSONObject(); // Key : Value (mapping)
					
					jsonObjArr[i].put("postId", list.get(i).getPostId());
					jsonObjArr[i].put("writer", list.get(i).getWriter());
					jsonObjArr[i].put("likeCnt", list.get(i).getLikeCnt());
					jsonObjArr[i].put("content", list.get(i).getContent());
					jsonObjArr[i].put("modAt", list.get(i).getModAt());
					jsonObjArr[i].put("readCnt", list.get(i).getReadCnt());
					jsonObjArr[i].put("files", list.get(i).getFiles());
					
					jsonObjList.add(jsonObjArr[i]);
				}
				String jsonData = jsonObjList.toJSONString();
				
				out = res.getWriter();
				out.print(jsonData);
			break;
		case "detail":
			uvo = usv.getDetail(req.getParameter("email"));

			req.setAttribute("uvo", usv.getDetail(req.getParameter("email")));
			req.getRequestDispatcher("/user/detail.jsp").forward(req, res);
			break;
		case "modify":
			isUp = usv.modify(new UserVO(req.getParameter("email"), Integer.parseInt(req.getParameter("age")),
					req.getParameter("name"), Boolean.parseBoolean(req.getParameter("isAdmin")),
					req.getParameter("nickName")));
			log.info(">> modify {}", isUp > 0 ? "Success" : "Fail");
			req.getRequestDispatcher("/userCtrl/detail?email=" + req.getParameter("email"));
			break;
		case "changePwd":
			req.setAttribute("email", req.getParameter("email"));
			req.getRequestDispatcher("/user/changePwd.jsp").forward(req, res);
			break;
		case "checkPwd":
			try {
				StringBuffer sb = new StringBuffer();
				String line = null;
				BufferedReader br = req.getReader();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				log.info(">>> sb {}", sb.toString());
				JSONParser parser = new JSONParser();
				JSONObject jsonObj = (JSONObject) parser.parse(sb.toString());
				
				String checkPwd = "";
				try {
					checkPwd = hash.bytesToHex(hash.sha256(jsonObj.get("pwd").toString()));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				uvo = usv.logIn(new UserVO(jsonObj.get("email").toString(), checkPwd));
				out = res.getWriter();
				if (uvo == null) {
					out.print(0);
				} else {
					out.print(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "modifyPwd":
			String newPwd = "";
			try {
				newPwd = hash.bytesToHex(hash.sha256(req.getParameter("newPwd")));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			isUp = usv.modifyPwd(new UserVO(req.getParameter("email"), newPwd, true));
			log.info(">>> pwd change > {}", isUp > 0 ? "Success" : "Fail");
			res.sendRedirect("/userCtrl/logout");
			break;
		case "changeAvatar":
			req.setAttribute("email", req.getParameter("email"));
			req.getRequestDispatcher("/user/changeAvatar.jsp").forward(req, res);
			break;
		case "modifyAvatar":
			uvo = new UserVO();
			String oldAvatar = "";
			try {
				String savePath = getServletContext().getRealPath("/_fileUpload/avatar");
				File fileDir = new File(savePath);

				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}

				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir);
				fileItemFactory.setSizeThreshold(1 * 1024 * 1024);

				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);

				List<FileItem> itemList = fileUpload.parseRequest(req);
				for (FileItem item : itemList) {
					switch (item.getFieldName()) {
					case "email":
						uvo.setEmail(item.getString(UTF8));
						break;
					case "avatar":
						oldAvatar = item.getString(UTF8);

						break;
					case "profileImage":
						if (item.getSize() > 0) {
							String fileName = System.currentTimeMillis() + "-"
									+ item.getName().substring(item.getName().lastIndexOf(File.separator) + 1);
							File uploadFilePath = new File(fileDir + File.separator + fileName);
							try {
								item.write(uploadFilePath);
								uvo.setAvatar(fileName);
								Thumbnails.of(uploadFilePath).size(75, 75)
										.toFile(new File(fileDir + File.separator + "th_" + fileName));
							} catch (Exception e) {
								log.info(">>> File Write > Fail");
								e.printStackTrace();
							}
						}
						break;

					default:
						break;
					}
				}
				// 이전 아바타 지우기
				log.info(oldAvatar);
				if (uvo.getAvatar().length() > 0 && oldAvatar != "") {
					File removeFile = new File(fileDir + File.separator + oldAvatar);
					File removeFileThumb = new File(fileDir + File.separator + "th_" + oldAvatar);
					boolean rm = true;
					if (removeFile.exists()) {
						rm = removeFile.delete();
						if (rm) {
							rm = removeFileThumb.delete();
						}
					}
					log.info(">>> profileImg delete {}", rm ? "Success" : "Fail");
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}

			log.info(">>> uvo > {}", uvo);
			isUp = usv.modifyAvatar(uvo);
			
			// 새 아바타를 담은 uvo넣기
			req.getSession().setAttribute("ses", usv.logInWithKakao(uvo.getEmail()));
			
			req.getRequestDispatcher("/userCtrl/profile?email=" + uvo.getEmail()).forward(req, res);
			break;
		case "follow":
			usv.follow(req.getParameter("from"), req.getParameter("to"));
			req.getSession().setAttribute("followingList", usv.getFollowingList(req.getParameter("from")));
			req.getRequestDispatcher("/userCtrl/profile?email=" + req.getParameter("to")).forward(req, res);
			break;
		case "unfollow":
			usv.unFollow(req.getParameter("from"), req.getParameter("to"));
			req.getSession().setAttribute("followingList", usv.getFollowingList(req.getParameter("from")));
			req.getRequestDispatcher("/userCtrl/profile?email=" + req.getParameter("to")).forward(req, res);
			break;
		case "unfollowFromModal":
			isUp = usv.unFollow(req.getParameter("from"), req.getParameter("to"));
			out = res.getWriter();
			out.print(isUp);
			break;
		case "checkEmail": // 수정 중
			isUp = usv.checkEmail(req.getParameter("email"));
			out = res.getWriter();
			out.print(isUp);
			break;
		case "login":
			String pwd = "";
			try {
				pwd = hash.bytesToHex(hash.sha256(req.getParameter("pwd")));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			uvo = usv.logIn(new UserVO(req.getParameter("email"), pwd));
			if (uvo != null) {
				HttpSession ses = req.getSession();
				ses.setAttribute("ses", uvo);
				if (req.getParameter("iska") != null) {
					ses.setAttribute("isSocial", true);
				}
				ses.setMaxInactiveInterval(60 * 20);
			} else {
				res.sendRedirect("/");
				break;
			}
			req.getRequestDispatcher("/postCtrl/list").forward(req, res);
			break;
		case "logout":
			HttpSession currSes = req.getSession();
			currSes.invalidate();
			res.sendRedirect("/index.jsp");
			break;
		case "remove":
			usv.unfollowAll(req.getParameter("email"));
			
			isUp = psv.removeAll(req.getParameter("email"));
			isUp = csv.removeAll(req.getParameter("email"));
			isUp = lsv.removeAll(req.getParameter("email"));
			isUp = usv.remove(req.getParameter("email"));
			
			log.info("1 :  {}", Integer.toString(isUp));
			HttpSession currSES = req.getSession();
			currSES.invalidate();
			res.sendRedirect("/index.jsp");
			break;

		default:
			break;
		}
	}
}
