package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.javassist.expr.NewArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import domain.LikeVO;
import domain.PostVO;
import domain.UserVO;
import service.CommentService;
import service.CommentServiceImple;
import service.LikeService;
import service.LikeServiceImple;
import service.PostService;
import service.PostServiceImple;
import service.UserService;
import service.UserServiceImple;

@WebServlet("/postCtrl/*")
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(PostController.class);
	private final PostService psv;
	private RequestDispatcher rdp;
	private int isUp;
	private String UTF8 = "utf-8";

	public PostController() {
		psv = new PostServiceImple();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding(UTF8);
		res.setCharacterEncoding(UTF8);
		res.setContentType("text/html; charset=utf-8");

		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/") + 1);

		PostService psv = new PostServiceImple();
		UserService usv = new UserServiceImple();
		CommentService csv = new CommentServiceImple();
		LikeService lsv = new LikeServiceImple();
		log.info("path : {}", path);
		switch (path) {

		case "insert":
			try {
				String savePath = getServletContext().getRealPath("/_fileUpload/post");
				File fileDir = new File(savePath);

				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir);
				fileItemFactory.setSizeThreshold(1 * 1024 * 1024); // 저장을 위한 임시 메모리 저장용량 Byte단위
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				PostVO pvo = new PostVO();
				List<FileItem> itemList = fileUpload.parseRequest(req);
				log.info("itemList {}", itemList);
				for (FileItem item : itemList) {
					switch (item.getFieldName()) {
					case "content":
						log.info("content: {}", item.getString("utf-8"));
						pvo.setContent(item.getString("utf-8"));
						break;
					case "writer":
						log.info("writer: {}", item.getString("utf-8"));
						pvo.setWriter(item.getString("utf-8"));
						break;
					case "imgFile":
						if (item.getSize() > 0) {
							String fileName = item.getName().substring(item.getName().lastIndexOf(File.separator) + 1);
							fileName = System.currentTimeMillis() + "-" + fileName;
							File uploadFilePath = new File(fileDir + File.separator + fileName);
							log.info("===upload path : {}", uploadFilePath);

							try {
								item.write(uploadFilePath);
								pvo.setFiles(fileName);

							} catch (Exception e) {
								log.info("upload failed");
								e.printStackTrace();
							}

						}
						break;
					default:
						break;
					}
				}
				log.info("===pvo :{}", pvo);
				isUp = psv.register(pvo);

				log.info("== insert {}", isUp > 0 ? "success" : "fail");
			} catch (Exception e) {
				e.printStackTrace();
			}
			req.getRequestDispatcher("/postCtrl/list").forward(req, res);
			break;
		case "list":
			HttpSession session = req.getSession();
			if (session.getAttribute("ses") == null) {
				res.sendRedirect("/userCtrl/login");
			}
			int page = 1;
			String query = "";
			if (req.getParameter("page") != null && !req.getParameter("page").equals("")) {
				page = Integer.parseInt(req.getParameter("page"));
			}
			if(req.getParameter("query") != null && !req.getParameter("query").equals("")) {
				query = req.getParameter("query");
			}
			List<PostVO> postList = psv.getList((page - 1) * 5, query);
			UserVO uvo = (UserVO) session.getAttribute("ses");

			List<LikeVO> likeList = lsv.getList(uvo.getEmail());
			List<UserVO> followingList = usv.getFollowingList(uvo.getEmail());// 세션 이메일 넣을것
			req.setAttribute("likeList", likeList);
			req.setAttribute("cnt", psv.getCnt(query));
			req.setAttribute("postList", postList);
			session.setAttribute("followingList", followingList);
			for (PostVO pvo : postList) {
				req.setAttribute("cmt" + pvo.getPostId(), csv.getList(pvo.getPostId()));
			}
			
			log.info("list: {}", postList);
			log.info("page: {}", page);
			log.info("count : {}", psv.getCnt(query));

			req.getRequestDispatcher("/post/list.jsp").forward(req, res);

			break;
		case "getList":
			int limit1 = 5;
			if (req.getParameter("limit") != null && !req.getParameter("limit").equals("")) {
				limit1 = Integer.parseInt(req.getParameter("limit"));
			}
			List<PostVO> posts = psv.getList(limit1,"");

			Gson gson = new Gson();
			JSONArray data = new JSONArray();
			for (PostVO p : posts) {
				JSONObject post = new JSONObject();
				post.put("postData", p);
				post.put("comments", csv.getList(p.getPostId()));
				post.put("likeList", lsv.getList(p.getWriter()));
				data.add(post);
			}

			PrintWriter out = res.getWriter();
			out.print(gson.toJson(data));
			break;
		case "search":
				
			break;
		case "mylist":
			List<String> likedPostId = lsv.getLikedPostId(req.getParameter("email"));
			log.info("내가 좋아하는 글 목록 > {}", likedPostId);
			log.info("liked post  > {}", psv.getLikeList(likedPostId));
			HttpSession session2 = req.getSession();
			List<PostVO> postList2 = psv.getLikeList(likedPostId);
			UserVO uvo2 = (UserVO) session2.getAttribute("ses");
			
			
			List<LikeVO> likeList2 = lsv.getList(uvo2.getEmail());
			List<UserVO> followingList2 = usv.getFollowingList(uvo2.getEmail());// 세션 이메일 넣을것
			log.info("followingList = {}", followingList2);
			req.setAttribute("likeList", likeList2);
			req.setAttribute("cnt", psv.getCnt(""));
			req.setAttribute("postList", postList2);
			req.setAttribute("followingList", followingList2);
			if (postList2 != null) {
				for (PostVO pvo : postList2) {
					req.setAttribute("cmt" + pvo.getPostId(), csv.getList(pvo.getPostId()));
				}				
			}
			
			
			req.getRequestDispatcher("/post/list.jsp").forward(req, res);
			break;
		case "detail":
			// 게시물의 디테일로 이동
			Long pid = Long.parseLong(req.getParameter("pid"));
			PostVO post = psv.getDetailAndUp(pid);
			String str = post.getContent();
			List<String> tags = new ArrayList<>();
			log.info("content: {}", str);
			Pattern pattern = Pattern.compile("(#[ㄱ-힣A-Za-z0-9-_]+)(?:#[A-Za-z0-9가-힣-_]+)*\\b");
			Matcher matcher = pattern.matcher(str);

			while (matcher.find()) {
				String tag = matcher.group(1);
				tags.add(tag);
			}

			req.setAttribute("pvo", post);
			req.setAttribute("content", matcher.replaceAll(""));
			req.setAttribute("hashtags", tags);
			req.setAttribute("cvoList", csv.getList(pid));
			req.getRequestDispatcher("/post/detail.jsp").forward(req, res);
			break;
		case "modify":
			PostVO pvo = psv.getDetail(Long.parseLong(req.getParameter("pid")));
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("content", pvo.getContent());
			jsonObject.put("pid", pvo.getPostId());
			jsonObject.put("imgFile", pvo.getFiles());
			PrintWriter out1 = res.getWriter();
			System.out.println(jsonObject);
			out1.print(jsonObject);

			break;
		case "update":
			PostVO pvo2 = new PostVO();
			try {
				String savePath = getServletContext().getRealPath("/_postImgUpload");
				File fileDir = new File(savePath);
				String prevFile = "";
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir);
				fileItemFactory.setSizeThreshold(1 * 1024 * 1024); // 저장을 위한 임시 메모리 저장용량 Byte단위
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				List<FileItem> itemList = fileUpload.parseRequest(req);
				for (FileItem item : itemList) {
					switch (item.getFieldName()) {
					case "content":
						pvo2.setContent(item.getString("utf-8"));
						break;
					case "writer":
						pvo2.setWriter(item.getString("utf-8"));
						break;

					case "prevImgFile":
						pvo2.setFiles(item.getString("utf-8"));
						prevFile = pvo2.getFiles();

						break;
					case "pid":
						pvo2.setPostId(Long.parseLong(item.getString("utf-8")));
						break;
					case "imgFile":

						if (item.getSize() > 0) {

							String fileName = item.getName().substring(item.getName().lastIndexOf(File.separator) + 1);
							File removeFile = new File(fileDir + File.separator + prevFile);
							boolean rm = false;
							if (removeFile.exists()) {
								rm = removeFile.delete();
							}

							log.info(">>> profileImg delete {}", rm ? "Success" : "Fail");
							fileName = System.currentTimeMillis() + "-" + fileName;
							File uploadFilePath = new File(fileDir + File.separator + fileName);
							try {
								item.write(uploadFilePath);
								pvo2.setFiles(fileName);

							} catch (Exception e) {
								log.info("upload failed");
								e.printStackTrace();
							}

						}
						break;
					default:
						break;
					}
				}
//				log.info("prevFil = {}", prevFile);
//				if(pvo2.getFiles().length() > 0 && prevFile.length() > 0) {
//					File removeFile = new File(fileDir + File.separator + prevFile);
//					boolean rm = true;
//					if (removeFile.exists()) {
//						rm = removeFile.delete();
//					}
//					log.info(">>> profileImg delete {}", rm ? "Success" : "Fail");
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			isUp = psv.modify(pvo2);
			log.info("==update {}", isUp > 0 ? "success" : "failed");
			res.sendRedirect("/postCtrl/list");
			break;
		case "remove":
			// 게시물 삭제
			// 이후 내 게시물로 이동
			psv.remove(Long.parseLong(req.getParameter("pid")));

			break;
		case "like":
			// 좋아요 표시
			// 비동기
			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader br = req.getReader();

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONParser parser2 = new JSONParser();
			try {
				JSONObject jsonObj = (JSONObject) parser2.parse(sb.toString());
				isUp = lsv.like(new LikeVO((String) jsonObj.get("email"), Long.valueOf((String) jsonObj.get("pid"))));
				if (isUp > 0) {
					log.info("=== {} likes {}", jsonObj.get("email"), jsonObj.get("pid"));
				}
			} catch (ParseException e) {

				e.printStackTrace();
			}
			req.getRequestDispatcher("/postCtrl/list").forward(req, res);
			break;
		case "unlike":
			// 좋아요 없애기
			// 비동기
			StringBuffer sb2 = new StringBuffer();
			String line2 = null;
			BufferedReader br2 = req.getReader();

			while ((line = br2.readLine()) != null) {
				sb2.append(line);
			}
			JSONParser parser3 = new JSONParser();
			try {
				JSONObject jsonObj2 = (JSONObject) parser3.parse(sb2.toString());
				isUp = lsv
						.unLike(new LikeVO((String) jsonObj2.get("email"), Long.valueOf((String) jsonObj2.get("pid"))));
				if (isUp > 0) {
					log.info("=== {} unlikes {}", jsonObj2.get("email"), jsonObj2.get("pid"));
				}
			} catch (ParseException e) {

				e.printStackTrace();
			}
			req.getRequestDispatcher("/postCtrl/list").forward(req, res);
			break;

		default:
			break;
		}
	}

}
