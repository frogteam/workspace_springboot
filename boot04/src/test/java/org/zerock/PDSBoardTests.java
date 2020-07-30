package org.zerock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.PDSBoard;
import org.zerock.domain.PDSFile;
import org.zerock.persistence.PDSBoardRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit // '테스트' 에서 @Transactional 이 롤백되지 않게 한다.
public class PDSBoardTests {
	
	@Autowired
	PDSBoardRepository repo;

	// 등록작업
	// 1개의 자료와 2개의 첨부파일 저장 
	@Test
	public void testInsertPDS() {
		PDSBoard pds = new PDSBoard();
		pds.setPname("Document");
		
		PDSFile file1 = new PDSFile();
		file1.setPdsfile("file1.doc");
		
		PDSFile file2 = new PDSFile();
		file2.setPdsfile("file2.doc");
		
		pds.setFiles(Arrays.asList(file1, file2));
		
		log.info("try to save pds");
		
		// Repository 는 PDSBoardRespository 한개뿐이므로
		// save() 는 한번만 호출한다.
		repo.save(pds); 
	}
	
	// javax.transaction.Transactional : 
	// 스프링에서 트랜잭션 처리를 지원하는 어노테이션
	@Transactional 
	@Test
	// 첨부파일중 특정 fno 의 파일 이름을 바꾼다
	public void testUpdateFileName1() {
		Long fno = 3L;  // ※실제 tbl_pdsfiles 테이블의 fno 값을 입력해주세요
		String newName = "updatedFile1.doc";
		
		// UPDATE 쿼리 결과 -> int 리턴
		int count = repo.updatePDSFile(fno, newName);
		
		// @Log 설정된 이후 사용 가능.
//		log.info("update count: " + count);
	}
	
	// 객체를 통한 파일 수정
	@Transactional
	@Test
	public void testUpdateFileName2() {
		String newName = "updatedFile2.doc"; // update 할 이름		
		// ※ pid, fno 번호가 존재하는지 확인하고 하세요
		long pid = 4L;
		long fno = 4L;
		
		// pid 값의 PDSBoard 객체 얻기
		Optional<PDSBoard> result = repo.findById(pid);
		
		result.ifPresent(pds -> { // 위 pid 값의 데이터가 존재한다면
			log.info("데이터가 존재하므로 update 시도");
			
			List<PDSFile> list = pds.getFiles();
			
			// fno 값의 PDSFile 객체 생성
			PDSFile target = new PDSFile();
			target.setFno(fno);
			target.setPdsfile(newName);
			
			// 그 fno값의 PDSFile 객체를 찾아내서  
			int idx = list.indexOf(target);
			if(idx > -1) {
				list.remove(idx);  // 일단 삭제한 후에
				list.add(target);  // 새로운 데이터 추가!
				pds.setFiles(list);  // 새롭게 갱신된 첨부파일의 목록을 세팅
			}
		
			repo.save(pds);   // UPDATE 수행
		});
	}
	
	// 첨부파일 삭제
	@Transactional
	@Test
	public void deletePDSFile() {
		// 첨부 파일 번호
		Long fno = 4L;  // ※ 존재 하는 fno 를 입력하세요
		
		int count = repo.deletePDSFile(fno);
		
		log.info("DELETE PDSFILE: " + count);	
	}
	
	// Join 을 위한 테스트 데이터 생성
	@Test
	public void insertDummies() {
		// 100개의 게시글 자료 +  각 게시글마다 2개의 첨부파일 데이터 추가
		List<PDSBoard> list = new ArrayList<>();
		IntStream.range(1, 100).forEach(i -> {
			PDSBoard pds = new PDSBoard();
			pds.setPname("자료 " + i);
			
			PDSFile file1 = new PDSFile();
			file1.setPdsfile("file1.doc");
			
			PDSFile file2 = new PDSFile();
			file2.setPdsfile("file2.doc");
			
			pds.setFiles(Arrays.asList(file1, file2));
			
			log.info("try to save pds");
			
			list.add(pds);
		});
		// saveAll() : List 에 보관했다가 한번에 저장 (INSERT) 가능.
		repo.saveAll(list);
	}
	
	
	@Test
	public void viewSummary() {
		repo.getSummary().forEach(arr ->{
			log.info(Arrays.toString(arr));
		});
	}
	
	
	
}
























