package repository;

import java.util.List;

import domain.LikeVO;

public interface LikeDAO {
	public int insert(LikeVO lvo);
	public List<LikeVO> selectList(String email);
	public List<String> selectLikedPostId(String email);
	public int delete(LikeVO lvo);
	public int deleteAll(String email);
}
