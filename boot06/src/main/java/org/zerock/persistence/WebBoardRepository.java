package org.zerock.persistence;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.QWebBoard;
import org.zerock.domain.WebBoard;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>,
						QuerydslPredicateExecutor<WebBoard>{

	// findAll() 에 전달할 Predicate 은 QWebBoard 를 이용해서 작성해야 한다.
	// '검색조건 type ' 과 '키워드 keyword' 를 전달하여 Predicate 를 생성하는 코드 작성
	// makePredicate 은 위 매개변수를 사용하여 적당한 쿼리 생성.
	// ※ Java8 부터는 인터페이스에 'default 메소드' 사용 가능.
	public default Predicate makePredicate(String type, String keyword) {
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QWebBoard board = QWebBoard.webBoard;
		
		// type if ~ else
		
		// where bno > 0   조건만 생성		
		builder.and(board.bno.gt(0));
		
		if(type == null){
			return builder;
		}
		
		switch(type){
		case "t":
			builder.and(board.title.like("%" + keyword +"%"));
			break;
		case "c":
			builder.and(board.content.like("%" + keyword +"%"));
			break;
		case "w":
			builder.and(board.writer.like("%" + keyword +"%"));
			break;
		}
		
		return builder;
	}

}















