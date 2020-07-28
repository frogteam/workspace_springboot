package org.zerock;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.PDSBoard;
import org.zerock.domain.PDSFile;
import org.zerock.persistence.PDSBoardRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
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
	
	
}
















