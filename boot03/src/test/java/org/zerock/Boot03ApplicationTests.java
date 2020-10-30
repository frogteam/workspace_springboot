package org.zerock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Board;
import org.zerock.domain.QBoard;
import org.zerock.persistence.BoardRepository;

import com.querydsl.core.BooleanBuilder;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Boot03ApplicationTests {

	@Autowired
	private BoardRepository repo;
	
	@Test
	public void testInsert200() {
		for(int i = 1; i <= 200; i++) {
			Board board = new Board();
			board.setTitle("제목.." + i);
			board.setContent("내용..." + i + " 채우기");
			board.setWriter("user0" + (i % 10));
			repo.save(board);
		}
	}
	
	
	@Test
	public void testByTitle() {
		
		// Java8 이전
		List<Board> list = repo.findBoardByTitle("제목..177");
		for(int i = 0, len = list.size(); i < len; i++) {
			System.out.println(list.get(i));
		}
		
		
		// Java8 부턴
		repo.findBoardByTitle("제목..177")
			.forEach(board -> System.out.println(board));
	}
	
	
	@Test
	public void testByWriter() {
		Collection<Board> results = repo.findByWriter("user00");		
		results.forEach(board -> System.out.println(board));	
	}
	
	@Test
	public void testByWriterContaining() {
		Collection<Board> results = repo.findByWriterContaining("05");
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByTitleContainingOrContentContaining() {
		// title 에 '2' 가 있거나, content 에 '5' 가 있는 데이터 찾기
		Collection<Board> results = repo.findByTitleContainingOrContentContaining("2", "5");
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByTitleAndBno() {
		// title 에 '5' 가 있거나, bno 가 50보다 큰 데이터 찾기
		Collection<Board> results = repo.findByTitleContainingAndBnoGreaterThan("5", 50L);
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderBy() {
		// bno 값이 90보다 큰 데이터를 bno 역순으로 검색
		Collection<Board> results = repo.findByBnoGreaterThanOrderByBnoDesc(90L);
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderByPaging() {
		// of(page, size);
		//    페이지번호(0부터 시작), 데이터수
		Pageable paging = PageRequest.of(0, 10);  
		Collection<Board> results = repo.findByBnoGreaterThanOrderByBnoDesc(0L, paging);
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoPagingSort() {
		// bno 값이 0보다 큰 것들중.
		// bno 속성값 순서대로 오름차순 정렬.  첫페이지(0), 10개 데이터
		Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "bno");		
		
		//Collection<Board> results = repo.findByBnoGreaterThan(0L, paging);
		//results.forEach(board -> System.out.println(board));
		
		Page<Board> result = repo.findByBnoGreaterThan(0L, paging);
		System.out.println("PAGE SIZE: " + result.getSize());
		System.out.println("TOTAL PAGES: " + result.getTotalPages());
		System.out.println("TOTAL COUNT: " + result.getTotalElements());
		System.out.println("NEXT: " + result.nextPageable());
		
		List<Board> list = result.getContent();
		
		list.forEach(board -> System.out.println(board));
	}
	
	// 제목(title) 로 검색
	@Test
	public void testByTitle2() {
		// 제목(title) 에 "17" 이 있는 데이터 검색
		repo.findByTitle("17")
		.forEach(board -> System.out.println(board));
	}
	
	
	// 내용(content) 로 검색
	@Test
	public void testByContent() {
		// 내용(content) 에 "17" 이 있는 데이터 검색
		repo.findByContent("17")
		.forEach(board -> System.out.println(board));
	}
	
	// 작성자(writer) 로 검색
	@Test
	public void testByWriter2() {
		// 작성자(writer) 에 "7" 이 있는 데이터 검색
		repo.findByWriter2("7")
		.forEach(board -> System.out.println(board));
	}
	
	// 제목(title) 로 검색
	@Test
	public void testByTitle17() {
		repo.findByTitle2("17") // List<Object[]> 을 리턴했다. 따라서..
		.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	
	// 제목(title) 로 검색 : nativeQuery
	@Test
	public void testByTitle9() {
		repo.findByTitle3("17")
		.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	// @Query + Pageable
	@Test
	public void testByPaging() {
		Pageable pageable = PageRequest.of(0, 10);
		repo.findByPage(pageable)
		.forEach(board -> System.out.println(board));
	}
	
	
	// Predicate 생성 및 테스트, querydsl 사용
	//  - 1. BooleanBuilder 를 생성 (Predicate 생성)
	//  - 2. 동적쿼리에 필요한 조건을 and() 등을 이용하여 추가.
	//  - 3. QBoard 의 like(), get() 등을 이용하여 원하는 SQL 구성
	@Test
	public void testPredicate() {
		String type = "t";
		String keyword = "17";
		
		BooleanBuilder builder = new BooleanBuilder();  // com.querydsl.core.BooleanBuilder
		
		// QDomain 생성기로 생성된 QBoard
		//  Board 속성을 이용해서 다양한 SQL 에 필요한 구문을 처리할수 있는 기능들이 추가되어 있다.
		QBoard board = QBoard.board;  
		
		if(type.equals("t")) {
			builder.and(board.title.like("'%" + keyword + "%'"));
		}
		
		// bno > 0
		builder.and(board.bno.gt(0L));

		System.out.println("builder: " + builder);  // board.title like '%17%' && board.bno > 0
		Pageable pageable = PageRequest.of(0,  10);
		try {			
			repo.findAll(builder);  // 음 UnsupportedOperationException..
		} catch(Exception e) {
			e.printStackTrace();
		}
		//Page<Board> result = repo.findAll(builder,  pageable);  // 음 UnsupportedOperationException..
		

		//System.out.println("PAGE SIZE: " + result.getSize());
		
//		System.out.println("TOTAL PAGES: " + result.getTotalPages());
//		System.out.println("TOTAL COUNT: " + result.getTotalElements());
//		System.out.println("NEXT: " + result.nextPageable());
//		
//		List<Board> list = result.getContent();
//		list.forEach(b -> System.out.println(b));
		
	} // end testPredicate()	
	
}












































