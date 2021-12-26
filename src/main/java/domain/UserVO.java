package domain;

public class UserVO {
	private String email;
	private int age;
	private String name;
	private String pwd;
	private boolean isAdmin;
	private String nickName;
	private String avatar;
	private String regAt;
	private String following;
	private String follower;
	
	public UserVO() {}
	
	// register
	public UserVO(String email, int age, String name, String pwd, boolean isAdmin, String nickName) {
		this.email = email;
		this.age = age;
		this.name = name;
		this.pwd = pwd;
		this.isAdmin = isAdmin;
		this.nickName = nickName;
	}

	// update
	public UserVO(String email, int age, String name, Boolean isAdmin, String nickName) {
		this.email = email;
		this.age = age;
		this.name = name;
		this.isAdmin = isAdmin;
		this.nickName = nickName;
	}
	
	// password update
	public UserVO(String email, String pwd, boolean pwdchange) {
		this.email = email;
		this.pwd = pwd;
	}

	//admin page member관리 (list) & login result
	public UserVO(String email, int age, String name, boolean isAdmin, String nickName, String avatar, String regAt) {
		this.email = email;
		this.age = age;
		this.name = name;
		this.isAdmin = isAdmin;
		this.nickName = nickName;
		this.avatar = avatar;
		this.regAt = regAt;
	}
	
	// detail
	public UserVO(String email, int age, String name, boolean isAdmin, String nickName, String avatar, String regAt,
			String following, String follower) {
		this.email = email;
		this.age = age;
		this.name = name;
		this.isAdmin = isAdmin;
		this.nickName = nickName;
		this.avatar = avatar;
		this.regAt = regAt;
		this.following = following;
		this.follower = follower;
	}
	
	// list
	public UserVO(String name, String nickName, String avatar) {
		this.name = name;
		this.nickName = nickName;
		this.avatar = avatar;
	}
	
	// login 할때
	public UserVO(String email, String pwd) {
		this.email = email;
		this.pwd = pwd;
	}
	
	public UserVO(String parameter, String parameter2, String parameter3, String parameter4) {
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getNickname() {
		return nickName;
	}

	public void setNickname(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRegAt() {
		return regAt;
	}

	public void setRegAt(String regAt) {
		this.regAt = regAt;
	}

	public String getFollowing() {
		return following;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	public String getFollower() {
		return follower;
	}

	public void setFollower(String follower) {
		this.follower = follower;
	}

	@Override
	public String toString() {
		return "UserVO [email=" + email + ", age=" + age + ", name=" + name + ", pwd=" + pwd + ", isAdmin=" + isAdmin
				+ ", nickname=" + nickName + ", avatar=" + avatar + ", regAt=" + regAt + ", following=" + following
				+ ", follower=" + follower + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
}
