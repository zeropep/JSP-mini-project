package domain;

public class CommentVO {

	private long cmtId;
	private String writer;
	private String regAt;
	private String modAt;
	private long postId;
	private String content;
	private String avatar;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public CommentVO() {
	}

	// register
	public CommentVO(String writer, long postId, String content) {
		this.writer = writer;
		this.postId = postId;
		this.content = content;
	}

	// list
	public CommentVO(String writer, String modAt, String content) {
		this.writer = writer;
		this.modAt = modAt;
		this.content = content;
	}

	// update
	public CommentVO(long postId, String content) {
		this.postId = postId;
		this.content = content;
	}

	public long getCmtId() {
		return cmtId;
	}

	public void setCmtId(long cmtId) {
		this.cmtId = cmtId;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getRegAt() {
		return regAt;
	}

	public void setRegAt(String regAt) {
		this.regAt = regAt;
	}

	public String getModAt() {
		return modAt;
	}

	public void setModAt(String modAt) {
		this.modAt = modAt;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentVO [cmtId=" + cmtId + ", email=" + writer + ", regAt=" + regAt + ", modAt=" + modAt + ", postId="
				+ postId + ", content=" + content + "]";
	}

}
