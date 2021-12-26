package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domain.UserVO;

public interface UserDAO {
	public int insert(UserVO uvo);
	public List<UserVO> selectList();
	public UserVO selectOne(String email);
	public int update(UserVO uvo);
	public int delete(String email);
	public UserVO selectOne(UserVO uvo);
	public HashMap<String, String> selectFollow(String email);
	public int setFollow(String fromFollowerList, String fromFollowingList, String from);
	public String selectFollowerCSV(String email);
	public String selectFollowingCSV(String email);
	public List<UserVO> selectListByEmail(ArrayList<String> csv);
	public int updatePwd(UserVO uvo);
	public int updateAvatar(UserVO uvo);
	public UserVO emailExist(String email);
	public UserVO kakaoLogin(String email);
}
