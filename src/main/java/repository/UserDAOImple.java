package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.UserVO;
import orm.DataBaseBuilder;

public class UserDAOImple implements UserDAO {
	private static Logger log = LoggerFactory.getLogger(UserDAOImple.class);
	private final String ns = "UserMapper.";

	public UserDAOImple() {
		new DataBaseBuilder();
	}

	@Override
	public int insert(UserVO uvo) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.insert(ns + "reg", uvo);
			if (isUp > 0) {
				sql.commit();
			}
			sql.close();
			return isUp;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 0;
	}

	@Override
	public List<UserVO> selectList() {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			List<UserVO> list = sql.selectList(ns + "list");
			sql.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserVO selectOne(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			UserVO uvo = sql.selectOne(ns + "one", email);
			sql.close();
			return uvo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(UserVO uvo) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.update(ns + "mod", uvo);
			if (isUp > 0) {
				sql.commit();
			}
			sql.close();
			return isUp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.delete(ns + "del", email);
			if (isUp > 0) {
				sql.commit();
			}
			sql.close();
			return isUp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public UserVO selectOne(UserVO uvo) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			UserVO loginUvo = sql.selectOne(ns + "login", uvo);
			sql.close();
			return loginUvo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<String, String> selectFollow(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			HashMap<String, String> map = sql.selectOne(ns + "getFollow", email);
			sql.close();
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String selectFollowerCSV(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			String follower = sql.selectOne(ns + "getFollowerCSV", email);
			sql.close();
			return follower;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String selectFollowingCSV(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			String following = sql.selectOne(ns + "getFollowingCSV", email);
			sql.close();
			return following;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int setFollow(String followerList, String followingList, String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = 0;
			Map<String, String> map = new HashMap<>();
			map.put("follower", followerList);
			map.put("following", followingList);
			map.put("email", email);
			isUp = sql.update(ns + "setFollow", map);
			if (isUp > 0) {
				sql.commit();
			}
			sql.close();
			return isUp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<UserVO> selectListByEmail(ArrayList<String> csv) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
//			System.out.println(csv);
			List<UserVO> list = sql.selectList(ns + "selectListByEmail", csv);
			sql.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updatePwd(UserVO uvo) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.update(ns + "modPwd", uvo);
			if (isUp > 0) {
				sql.commit();
			}
			sql.close();
			return isUp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateAvatar(UserVO uvo) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.update(ns + "modAvatar", uvo);
			if (isUp > 0) {
				sql.commit();
			}
			sql.close();
			return isUp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public UserVO emailExist(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			UserVO uvo = sql.selectOne(ns + "check", email);
			sql.close();
			return uvo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserVO kakaoLogin(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			UserVO loginUvo = sql.selectOne(ns + "kakaoLogin", email);
			sql.close();
			return loginUvo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
