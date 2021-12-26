package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import service.CommentService;
import service.CommentServiceImple;

@WebServlet("/cmtCtrl/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(CommentController.class);
	private final CommentService csv;
	private int isUp;
	private final String UTF8 = "utf-8";
	
	public CommentController() {
		csv = new CommentServiceImple();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding(UTF8);
		res.setCharacterEncoding(UTF8);
		
		String uri = req.getRequestURI();
		String path = uri.substring("/cmtCtrl/".length());
		
		String pathVar = "";
		if (path.contains("/")) {
			pathVar = path.substring(path.lastIndexOf("/") + 1);
			path = path.substring(0, path.lastIndexOf("/"));
		}
		log.info("path : {} , pathVar : {}", path, pathVar);
		// 기존 인스타에서는 list 게시물을 볼때 바로 댓글을 보고 작성할 수 있음
		switch (path) {
		case "post":
			// 비동기방식
			try {
				StringBuffer sb = new StringBuffer();
				String line = null;
				BufferedReader br = req.getReader();

				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				log.info(">>> string buffer = {}", sb.toString());
				JSONParser parser = new JSONParser();
				JSONObject jsonObj = (JSONObject) parser.parse(sb.toString());

				isUp = csv.register(new CommentVO((String)jsonObj.get("writer"),Long.valueOf(String.valueOf(jsonObj.get("pid"))), (String)jsonObj.get("content")));
				log.info(">>> comment register : {}", isUp > 0 ? "success" : "fail");
				PrintWriter out = res.getWriter();
				out.print(isUp);
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case "list":
			
			break;
		case "edit":
			
			break;
		case "remove":
			
			break;

		default:
			break;
		}
	}
}
