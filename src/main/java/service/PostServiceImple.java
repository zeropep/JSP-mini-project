package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.PostVO;
import repository.PostDAO;
import repository.PostDAOImple;

public class PostServiceImple implements PostService {
	private static Logger log = LoggerFactory.getLogger(PostServiceImple.class);
	private PostDAO pdao;
	
	public PostServiceImple() {
		pdao = new PostDAOImple();
	}

	@Override
	public int register(PostVO pvo) {
		return pdao.insert(pvo);
	}

	@Override
	public List<PostVO> getList(int page, String query) {

		return pdao.selectList(page, query);
	}
	
	@Override
	public List<PostVO> getListForProfile(String writer) {
		return pdao.selectList(writer);
	}
	
	@Override
	public List<PostVO> getLikeList(List<String> likedList) {
		
		return pdao.selectList(likedList);
	}

	@Override
	public int getCnt(String query) {

		return pdao.selectCnt(query);
	}

	@Override
	public PostVO getDetail(long postId) {
		return pdao.selectOne(postId);
	}
	
	@Override
	public PostVO getDetailAndUp(long postId) {
		pdao.updateReadcount(postId);
		return pdao.selectOne(postId);
	}

	@Override
	public int modify(PostVO pvo) {
		return pdao.update(pvo);
	}

	@Override
	public int remove(long postId) {
		return pdao.delete(postId);
	}



	@Override
	public int removeAll(String email) {
		return pdao.deleteAll(email);
	}

}
