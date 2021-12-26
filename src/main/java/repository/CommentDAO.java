package repository;

import java.util.List;

import domain.CommentVO;

public interface CommentDAO {
	public int insert(CommentVO cvo);
	public List<CommentVO> selectList(long postId);
	public int update(CommentVO cvo);
	public int delete(long cmtId);
	public int deleteAll(String writer);
}
