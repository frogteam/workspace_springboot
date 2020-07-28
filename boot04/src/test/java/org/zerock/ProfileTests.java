package org.zerock;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Member;
import org.zerock.domain.Profile;
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
	
	// 100 명의 회원 데이터 생성
	@Test
	public void testInsertMembers() {
		IntStream.range(1, 101).forEach(i -> {
			Member member = new Member();
			member.setUid("user" + i);
			member.setUpw("pw" + i);
			member.setUname("사용자" + i);
			
			memberRepo.save(member);
		});
	}
	
	// 특정 회원(user1)의 프로필 데이터
	@Test
	public void testInsertProfile() {
		// 우리가 필요로 하는건 "user1" 이라는 uid 값이다.
		Member member = new Member();
		member.setUid("user1");
		
		for(int i = 1; i < 5; i++) {
			Profile profile1 = new Profile();
			profile1.setFname("face" + i + ".jpg");
			
			if(i == 1) {
				// 여러사진중에서 첫번째 사진이 현재 프로필
				profile1.setCurrent(true);  
			}
			
			profile1.setMember(member);  // 잉?
			profileRepo.save(profile1);
		}	
	}
	
	// 특정 사용자(member) profile 사진 개수 
	@Test
	public void testFetchJoin1() {
		List<Object[]> result = memberRepo.getMemberWithProfileCount("user1");
		
		result.forEach(arr -> System.out.println(Arrays.toString(arr)));	
	}
	
	// 회원정보와 현재 사용중 프로필 정보 얻기
	@Test
	public void testFetchJoin2() {
		List<Object[]> result = memberRepo.getMemberwithProfile("user1");
		result.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	
	
}













