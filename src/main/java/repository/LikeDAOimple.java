package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.LikeVO;
import orm.DataBaseBuilder;

public class LikeDAOimple implements LikeDAO {
	private static final Logger log = LoggerFactory.getLogger(LikeDAOimple.class);
	private final String ns = "LikeMapper.";

	public LikeDAOimple() {
		new DataBaseBuilder();
	}

	@Override
	public int insert(LikeVO lvo) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.insert(ns + "like", lvo);
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
	public List<LikeVO> selectList(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			List<LikeVO> list = sql.selectList(ns + "list", email);
			sql.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> selectLikedPostId(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			List<String> likedPostId = sql.selectList(ns+"likedPostId", email);
			sql.close();
			return likedPostId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int delete(LikeVO lvo) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.delete(ns + "unlike", lvo);
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
	public int deleteAll(String email) {
		try {
			SqlSession sql = DataBaseBuilder.getFactory().openSession();
			int isUp = sql.delete(ns + "delAll", email);
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
}
