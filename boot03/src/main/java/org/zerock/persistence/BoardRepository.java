package org.zerock.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>,
								QuerydslPredicateExecutor<Board>{

	public List<Board> findBoardByTitle(String title);
	
	// findBy... 로 시작하는 쿼리 메소드는
	// 지정하는 '속성'의 타입에 따라 파라미터 타입이 결정된다.
	// Board 의 writer '속성' 의 타입이 문자열이기 때문에 파라미터 타입은 'String' 으로 지정. 
	public Collection<Board> findByWriter(String writer);
	
	// 작성자에 대한 like % 키워드 %
	// 작성자명에 특정 문자가 있는 게시글 검색
	public Collection<Board> findByWriterContaining(String writer);
	
	// OR 조건의 처리
	// title 과 content 총2개 속성을 사용했기에 파라미터도 2개 지정
	public Collection<Board> findByTitleContainingOrContentContaining(String title, String content);
	
	// 부등호 조건
	// title 에 특정 keyword 를 가지고 있고, bno 가 num 보다 큰 게시글 검색
	public Collection<Board> findByTitleContainingAndBnoGreaterThan(String keyword, Long num);
	
	// order by
	// bno 가 특정 번호보다 큰 게시물을 bno 값 역순으로 검색
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);
	
	
	// 페이징 처리
	// bno > ? ORDER BY bno DESC limit ?, ?
	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);

	// 정렬 처리
	// 메소드 이름에서 정렬조건을 빼본뒤, PageRequests() 를 이용해보기.
	//public List<Board> findByBnoGreaterThan(Long bno, Pageable paging);
	
	// Page<T> 리턴
	public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);
	
	// @Query 사용
	// 제목에 대한 검색처리
	@Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByTitle(String title);
	
	// @Param 사용 : 여러개의 파라미터를 JPQL에 전달할때 이름을 이용해서 전달 가능.
	// 내용에 대한 검색 처리
	@Query("SELECT b FROM Board b WHERE b.content LIKE %:content% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByContent(@Param("content") String content);
	
	// #{#entityName} 사용 : 
	// 작성자에 대한 검색 처리
	@Query("SELECT b FROM #{#entityName} b WHERE b.writer LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByWriter2(String writer);
	
	// 필요한 컬럼만 추출하기
	// 리턴타입이 엔티티타입이 아니라 Object[] 다.
	@Query("SELECT b.bno, b.title, b.writer, b.regdate "
			+ " FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Object[]> findByTitle2(String title);
	
	// nativeQuery 사용하기
	// 실행시 @Query 의 value 값을 그.대.로 실행하게 됨.
	@Query(value = "SELECT bno, title, writer FROM tbl_boards "
			+ " WHERE title LIKE CONCAT('%', ?1, '%') AND bno > 0 "
			+ " ORDER BY bno DESC", nativeQuery = true)
	public List<Object[]> findByTitle3(String title);
	
	// 페이징 처리
	// @Query + Pageable
	@Query("SELECT b FROM Board b WHERE b.bno > 0 ORDER By b.bno DESC")
	public List<Board> findByPage(Pageable pageable);
	
}














