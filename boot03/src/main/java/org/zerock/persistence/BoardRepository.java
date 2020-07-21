package org.zerock.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

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
	
}














