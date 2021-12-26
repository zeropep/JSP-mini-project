package service;

import java.util.List;

import domain.PostVO;

public interface PostService {
	public int register(PostVO pvo);
	public List<PostVO> getList(int page, String query);
	public List<PostVO> getListForProfile(String writer);
	public List<PostVO> getLikeList(List<String> likedList);
	public PostVO getDetail(long postId);
	public PostVO getDetailAndUp(long postId);
	public int getCnt(String query);
	public int modify(PostVO pvo);
	public int remove(long postId);
	public int removeAll(String email);
}
