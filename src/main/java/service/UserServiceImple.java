package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import domain.UserVO;
import repository.UserDAO;
import repository.UserDAOImple;

public class UserServiceImple implements UserService {
	private UserDAO udao;

	public UserServiceImple() {
		udao = new UserDAOImple();
	}

	@Override
	public int register(UserVO uvo) {
		return udao.insert(uvo);
	}

	@Override
	public List<UserVO> getList() {
		return udao.selectList();
	}

	@Override
	public UserVO getDetail(String email) {
		return udao.selectOne(email);
	}

	@Override
	public int modify(UserVO uvo) {
		return udao.update(uvo);
	}

	@Override
	public int remove(String email) {
		return udao.delete(email);
	}

	@Override
	public UserVO logIn(UserVO uvo) {
		return udao.selectOne(uvo);
	}

	@Override
	public UserVO logInWithKakao(String email) {
		return udao.kakaoLogin(email);
	}

	@Override
	public void follow(String from, String to) {
		List<String> fromFollowingList = new ArrayList<>();
		List<String> toFollowerList = new ArrayList<>();
		Map<String, String> fromMap = udao.selectFollow(from);
		Map<String, String> toMap = udao.selectFollow(to);
		System.out.println(fromMap.toString());
		System.out.println(toMap.toString());
		String fromFollowing = (String) fromMap.get("following");
		if (fromFollowing == null || fromFollowing == "") {
			fromFollowing = "";
		} else {			
			fromFollowingList.addAll(Arrays.asList(fromFollowing.split(",")));
		}
		String toFollower = (String) toMap.get("follower");
		if (toFollower == null || toFollower == "") {
			toFollower = "";
		} else {
			toFollowerList.addAll(Arrays.asList(toFollower.split(",")));						
		}
		
		System.out.println("팔로우하는사람의 팔로우목록 : " + fromFollowing);
		System.out.println("팔로우받는사람의 팔로워목록 : " + toFollower);
		
		System.out.print("팔로잉 리스트 : [");
		for (String string : fromFollowingList) {
			System.out.print(string);
		}
		System.out.println("]");
		
		System.out.print("팔로워 리스트 : [");
		for (String string : toFollowerList) {
			System.out.print(string);
		}
		System.out.println("]");
		
		if (!fromFollowingList.contains(to)) {
			if (fromFollowingList.size() > 0) {
				fromFollowing += to + ",";
			} else {
				fromFollowing += to;
			}
//			fromFollowing += to + ",";
		}
		if (!toFollowerList.contains(from)) {
			if (toFollowerList.size() > 0) {
				toFollower += from + ",";
			} else {
				fromFollowing += from;
			}
//			toFollower += from + ",";
		}

		System.out.println("팔로우하는사람의 팔로우목록 : " + fromFollowing);
		System.out.println("팔로우받는사람의 팔로워목록 : " + toFollower);
		

		udao.setFollow((String) fromMap.get("follower"), fromFollowing, from);
		udao.setFollow(toFollower, (String) toMap.get("following"), to);
	}

	@Override
	public int unFollow(String from, String to) {
		List<String> fromFollowingList = new ArrayList<>();
		List<String> toFollowerList = new ArrayList<>();
		Map<String, String> fromMap = udao.selectFollow(from);
		Map<String, String> toMap = udao.selectFollow(to);
		
		String fromFollowing = (String) fromMap.get("following");
		if (fromFollowing == null || fromFollowing == "") {
			fromFollowing = "";
		} else {			
			fromFollowingList.addAll(Arrays.asList(fromFollowing.split(",")));			
		}
		String toFollower = (String) toMap.get("follower");
		if (toFollower == null || toFollower == "") {
			toFollower = "";
		} else {
			toFollowerList.addAll(Arrays.asList(toFollower.split(",")));						
		}
		
		System.out.println("팔로우하는사람의 팔로우목록 : " + fromFollowing);
		System.out.println("팔로우받는사람의 팔로워목록 : " + toFollower);
		
		System.out.print("팔로잉 리스트 : [");
		for (String string : fromFollowingList) {
			System.out.print(string);
		}
		System.out.println("]");
		
		System.out.print("팔로워 리스트 : [");
		for (String string : toFollowerList) {
			System.out.print(string);
		}
		System.out.println("]");
		
		System.out.println("목표의 인덱스값 : " + fromFollowingList.indexOf(to));
		System.out.println("목표의 인덱스값 : " + toFollowerList.indexOf(from));

		fromFollowingList.remove(fromFollowingList.indexOf(to));
		toFollowerList.remove(toFollowerList.indexOf(from));
		fromFollowing = "";
		toFollower = "";
		for (String string : fromFollowingList) {
			fromFollowing += string + ",";

		}
		for (String string : toFollowerList) {
			toFollower += string + ",";
		}
		
		System.out.println("팔로우하는사람의 팔로우목록 : " + fromFollowing);
		System.out.println("팔로우받는사람의 팔로워목록 : " + toFollower);

		udao.setFollow((String) fromMap.get("follower"), fromFollowing, from);
		udao.setFollow(toFollower, (String) toMap.get("following"), to);
		return 0;
	}

	@Override
	public List<UserVO> getFollowerList(String email) {
		ArrayList<String> arr = new ArrayList<>(Arrays.asList(udao.selectFollowerCSV(email).split(",")));
	
		return udao.selectListByEmail(arr);
	}

	@Override
	public List<UserVO> getFollowingList(String email) {
		ArrayList<String> arr = new ArrayList<>(Arrays.asList(udao.selectFollowingCSV(email).split(",")));
		System.out.println(arr);
		return udao.selectListByEmail(arr);
	}

	@Override
	public int modifyPwd(UserVO uvo) {
		return udao.updatePwd(uvo);
	}

	@Override
	public int modifyAvatar(UserVO uvo) {
		return udao.updateAvatar(uvo);
	}

	@Override
	public int checkEmail(String email) {
		UserVO uvo = udao.emailExist(email);
		return uvo != null ? 1 : 0;
	}

	@Override
	public void unfollowAll(String email) {
		ArrayList<String> ingArr = new ArrayList<>(Arrays.asList(udao.selectFollowingCSV(email).split(",")));
		ArrayList<String> werArr = new ArrayList<>(Arrays.asList(udao.selectFollowerCSV(email).split(",")));
		
		if (ingArr.size() > 0 ) {
			for (String to : ingArr) {
				List<String> fromFollowingList = new ArrayList<>();
				List<String> toFollowerList = new ArrayList<>();
				Map<String, String> fromMap = udao.selectFollow(email);
				Map<String, String> toMap = udao.selectFollow(to);
				String fromFollowing;
				String toFollower;
				
				if (fromMap != null) {
					fromFollowing = (String) fromMap.get("following");
					if (fromFollowing == null || fromFollowing == "") {
						fromFollowing = "";
					} else {			
						fromFollowingList.addAll(Arrays.asList(fromFollowing.split(",")));			
					}
					
					fromFollowingList.remove(fromFollowingList.indexOf(to));
					fromFollowing = "";
					
					for (String string : fromFollowingList) {
						fromFollowing += string + ",";
						
						udao.setFollow((String) fromMap.get("follower"), fromFollowing, email);
					}
				}
				
				if (toMap != null) {
					toFollower = (String) toMap.get("follower");
					if (toFollower == null || toFollower == "") {
						toFollower = "";
					} else {
						toFollowerList.addAll(Arrays.asList(toFollower.split(",")));						
					}
					
					toFollowerList.remove(toFollowerList.indexOf(email));
					toFollower = "";
					
					for (String string : toFollowerList) {
						toFollower += string + ",";
					}
					udao.setFollow(toFollower, (String) toMap.get("following"), to);
				}
			}
		}
		
		if (werArr.size() > 0) {
			for (String from : werArr) {
				List<String> fromFollowingList = new ArrayList<>();
				List<String> toFollowerList = new ArrayList<>();
				Map<String, String> fromMap = udao.selectFollow(from);
				Map<String, String> toMap = udao.selectFollow(email);
				String fromFollowing;
				String toFollower;
				
				if (fromMap != null) {
					fromFollowing = (String) fromMap.get("following");
					if (fromFollowing == null || fromFollowing == "") {
						fromFollowing = "";
					} else {			
						fromFollowingList.addAll(Arrays.asList(fromFollowing.split(",")));			
					}
					
					fromFollowingList.remove(fromFollowingList.indexOf(email));
					fromFollowing = "";
					
					for (String string : fromFollowingList) {
						fromFollowing += string + ",";
						
					}
					
					udao.setFollow((String) fromMap.get("follower"), fromFollowing, from);
				}
				
				if (toMap != null) {
					toFollower = (String) toMap.get("follower");
					if (toFollower == null || toFollower == "") {
						toFollower = "";
					} else {
						toFollowerList.addAll(Arrays.asList(toFollower.split(",")));						
					}
					
					toFollowerList.remove(toFollowerList.indexOf(from));
					toFollower = "";
					
					for (String string : toFollowerList) {
						toFollower += string + ",";
					}
					
					udao.setFollow(toFollower, (String) toMap.get("following"), email);
				}

			}
		}
	}
}
