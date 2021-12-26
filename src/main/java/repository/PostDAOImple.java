package repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.PostVO;
import orm.DataBaseBuilder;

public class PostDAOImple implements PostDAO {
	private static Logger log = LoggerFactory.getLogger(PostDAOImple.class);
	private final String ns = "PostMapper.";
	
	public PostDAOImple() {
		new DataBaseBuilder();
	}
	
	@Override
	public int insert(PostVO pvo) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.insert(ns+"reg", pvo);
			if (isUp > 0 ) {
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
	public List<PostVO> selectList(int page, String query) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			map.put("query", "%" + query + "%");
			List<PostVO> list = sql.selectList(ns + "list", map);
			sql.close();
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<PostVO> selectList(String writer) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			List<PostVO> list = sql.selectList(ns+"profileList", writer);
			sql.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<PostVO> selectList(List<String> likedList) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			List<PostVO> list = sql.selectList(ns + "liked", likedList);
			sql.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public PostVO selectOne(long postId) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			PostVO pvo = sql.selectOne(ns+"one", postId);
			sql.close();
			return pvo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	
	@Override
	public int selectCnt(String query) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int cnt = sql.selectOne(ns + "cnt", "%" + query + "%");
			sql.close();
			return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int update(PostVO pvo) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.update(ns+"mod", pvo);
			if (isUp > 0 ) {
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
	public int delete(long postId) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.delete(ns+"del", postId);
			if (isUp > 0 ) {
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
	public int updateReadcount(long postId) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.update(ns+"addCnt", postId);
			if (isUp > 0 ) {
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
	public int deleteAll(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.delete(ns+"delAll", email);
			if (isUp > 0 ) {
				sql.commit();
			}
			sql.close();
			return isUp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
