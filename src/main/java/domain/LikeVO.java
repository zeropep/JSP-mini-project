package domain;

public class LikeVO {
	private String email;
	private long postId;
	private String avatar;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public LikeVO() {
	}

	public LikeVO(String email, long postId) {
		this.email = email;
		this.postId = postId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "LikeVO [email=" + email + ", postId=" + postId + "]";
	}

}
