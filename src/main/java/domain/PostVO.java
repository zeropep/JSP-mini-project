package domain;

public class PostVO {

	private long postId;
	private String writer;
	private int likeCnt;
	private String regAt;
	private String modAt;
	private int readCnt;
	private String files;
	private String content;
	private String cmtWriter;
	private String cmtContent;
	private String avatar;
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public PostVO() {
	}

	// register
	public PostVO(String writer, String files, String content) {
		this.writer = writer;
		this.files = files;
		this.content = content;
	}

	
	//list
	public PostVO( String writer, int likeCnt, String regAt, String modAt, int readCnt) {
		
		this.writer = writer;
		this.likeCnt = likeCnt;
		this.regAt = regAt;
		this.modAt = modAt;
		this.readCnt = readCnt;
	}
	
	//detail
	public PostVO( String writer, int likeCnt, String regAt, String modAt, int readCnt, String files, String content) {
		this.writer = writer;
		this.likeCnt = likeCnt;
		this.regAt = regAt;
		this.modAt = modAt;
		this.readCnt = readCnt;
		this.files = files;
		this.content = content;
	}
	
	//modify
	public PostVO(long postId, String files, String content) {
		this.postId = postId;
		this.files = files;
		this.content = content;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getWriter() {
		return writer;
	}

	public String getCmtWriter() {
		return cmtWriter;
	}

	public void setCmtWriter(String cmtWriter) {
		this.cmtWriter = cmtWriter;
	}

	public String getCmtContent() {
		return cmtContent;
	}

	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
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

	public int getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "PostVO [postId=" + postId + ", writer=" + writer + ", likeCnt=" + likeCnt + ", regAt=" + regAt
				+ ", modAt=" + modAt + ", readCnt=" + readCnt + ", files=" + files + ", content=" + content
				+ ", cmtWriter=" + cmtWriter + ", cmtContent=" + cmtContent + "]";
	}

	
	
	
}
