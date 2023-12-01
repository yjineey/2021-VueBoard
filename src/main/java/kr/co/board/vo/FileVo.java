package kr.co.board.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileVo {

	private int fileId; // 파일 index
	private int boardId; // 게시글
	List<MultipartFile> files; // 업로드 파일
	private String orgFileName; // 파일 실제이름
	private String fileName; // 파일명 변경
	private int fileSize; // 파일크기
	private String filePath; // 파일경로
	private int fileCount; // 파일갯수
	private String fileNameKey; // 암호화 파일명
	private String use_yn;
	
	

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public String getFileNameKey() {
		return fileNameKey;
	}

	public void setFileNameKey(String fileNameKey) {
		this.fileNameKey = fileNameKey;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public String getOrgFileName() {
		return orgFileName;
	}

	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

}
