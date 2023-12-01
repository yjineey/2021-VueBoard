package kr.co.board.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.board.dao.FileDao;
import kr.co.board.vo.BoardVo;
import kr.co.board.vo.FileVo;

@Service
public class FileService {

	@Inject
	FileDao fileDao;

	public List<BoardVo> FileListAll() throws Exception {
		return fileDao.FileListAll();
	}

	public List<FileVo> fileView(int id) throws Exception {
		return fileDao.fileView(id);
	}

	public void fileUpload(BoardVo vo, FileVo fileVo) throws Exception {
//			public ResponseEntity<Map<String, Object>> fileUpload(MultipartFile[] files, BoardVo boardVo) throws Exception {
		System.out.println(":::::::::::파일업로드::::::::::::::::::");

		List<MultipartFile> files = fileVo.getFiles();
		String filePath = "D:\\develop\\project-Workspace\\Project_05_vueBoard\\springBoard\\src\\main\\webapp\\WEB-INF\\classes\\uploadPath";

		if (files != null && files.size() > 0) {
			File file = new File(filePath);

			// 디렉토리가 없으면 생성
			if (file.exists() == false) {
				file.mkdirs();
			}

			for (MultipartFile multipartFile : files) {
				// 파일이름
				String orgFileName = multipartFile.getOriginalFilename();
				// 암호화
				String fileExt = orgFileName.substring(orgFileName.lastIndexOf("."));
				// 파일명 변경(파일명랜덤+ uuid로 암호화_ 확장자)
				String fileName = UUID.randomUUID().toString().replaceAll("-", "") + fileExt;
				// 파일 크기
				int fileSize = (int) multipartFile.getSize();

				// 설정한 Path에 파일 저장
				file = new File(filePath + "/" + fileName);

				multipartFile.transferTo(file);

				fileVo.setBoardId(vo.getId());
				fileVo.setOrgFileName(orgFileName);
				fileVo.setFileName(fileName);
				fileVo.setFileSize(fileSize);
				fileVo.setFilePath(filePath);

				fileDao.fileUpload(fileVo);

				System.out.println("================== file start ==================");
				System.out.println("파일명: " + orgFileName);
				System.out.println("파일명 변경: " + fileName);
				System.out.println("파일 크기: " + fileSize);
				System.out.println("content type: " + multipartFile.getContentType());
				System.out.println("파일경로: " + file);
				System.out.println("================== file   END ==================");

			}
		}
	}
	/** 32글자의 랜덤한 문자열(숫자포함) 생성 */
//	public static String getRandomString() {
//		return UUID.randomUUID().toString().replaceAll("-", "");
//	}

	
	public int fileCount(int id) throws Exception {
		return fileDao.fileCount(id);
	}

	public void fileDelete(String fileName ) throws Exception {
		fileDao.fileDelete(fileName);
	}

}