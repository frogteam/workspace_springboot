package org.zerock;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.persistence.MemberRepository;
import org.zerock.persistence.ProfileRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log   // Lombok 로그 사용
@Commit  // 테스트 결과 를 DB 에 commit
public class ProfileTests {

	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	ProfileRepository profileRepo;
	
}

