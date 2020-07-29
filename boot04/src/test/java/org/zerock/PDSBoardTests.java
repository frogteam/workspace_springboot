package org.zerock;

import java.util.Arrays;
import java.util.Optional;

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
		log.info("update count: " + count);
	}
	
	@Transactional
	@Test
	public void testUpdateFileName2() {
		String newName = "updateFile2.doc";
		
		// 번호가 존재하는지 확인하고 하세요
		Optional<PDSBoard> result = repo.findById(4L);
		
		// TODO
		
		
	}
	
	
	
	
	
}
























