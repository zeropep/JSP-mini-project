package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.LikeVO;
import repository.LikeDAO;
import repository.LikeDAOimple;

public class LikeServiceImple implements LikeService {
	private static final Logger log = LoggerFactory.getLogger(LikeServiceImple.class);
	private LikeDAO ldao = new LikeDAOimple();
	
	@Override
	public int like(LikeVO lvo) {
		return ldao.insert(lvo);
	}

	@Override
	public List<LikeVO> getList(String email) {
		return ldao.selectList(email);
	}
	
	@Override
	public List<String> getLikedPostId(String email) {
		return ldao.selectLikedPostId(email);
	}

	@Override
	public int unLike(LikeVO lvo) {
		return ldao.delete(lvo);
	}

	@Override
	public int removeAll(String email) {
		return ldao.deleteAll(email);
	}
}
