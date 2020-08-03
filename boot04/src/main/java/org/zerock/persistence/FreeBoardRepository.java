package org.zerock.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.FreeBoard;

public interface FreeBoardRepository extends 
				CrudRepository<FreeBoard, Long> {

	public List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);
	
	// @Query, Fetch Join을 사용한 조인 처리, 
	// 게시글목록 + 댓글의 개수
	@Query("SELECT b.bno, b.title, count(r) " + 
			" FROM FreeBoard b LEFT OUTER JOIN b.replies r " +
			" WHERE b.bno > 0 GROUP BY b ")
	public List<Object[]> getPage(Pageable page);
	
}
















