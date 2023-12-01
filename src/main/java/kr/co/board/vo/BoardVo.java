package kr.co.board.vo;

public class BoardVo {

	private int id;
	private String title;
	private String contents;
	private String writer;
	private String reg_date;
	private String mod_date;
	private String use_yn;

	private int pageStart;
	private int perPageNum;
	private int total;

	private int fileCount;
	private String fileDelete;
//	List<MultipartFile> files;
//
//	public List<MultipartFile> getFiles() {
//		return files;
//	}
//
//	public void setFiles(List<MultipartFile> files) {
//		this.files = files;
//	}

	
	
	
	public int getTotal() {
		return total;
	}

	public String getFileDelete() {
		return fileDelete;
	}

	public void setFileDelete(String fileDelete) {
		this.fileDelete = fileDelete;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getMod_date() {
		return mod_date;
	}

	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

}
