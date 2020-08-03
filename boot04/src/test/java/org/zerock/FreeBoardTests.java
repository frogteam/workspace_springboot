package org.zerock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.FreeBoard;
import org.zerock.domain.FreeBoardReply;
import org.zerock.persistence.FreeBoardReplyRepository;
import org.zerock.persistence.FreeBoardRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class FreeBoardTests {

	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	FreeBoardReplyRepository replyRepo;
	
	@Test
	public void insertDummy() {
		IntStream.range(1,  200).forEach(i -> {
			FreeBoard board = new FreeBoard();
			board.setTitle("Free Board ... " + i);
			board.setContent("Free Content ... " + i);
			board.setWriter("user" + i % 10);
			
			boardRepo.save(board);
		});
	}
	
	@Transactional
	@Test
	public void insertReply2Way() {
		
		Optional<FreeBoard> result = boardRepo.findById(199L);
		
		result.ifPresent(board ->{
			List<FreeBoardReply> replies = board.getReplies();
			
			FreeBoardReply reply = new FreeBoardReply();
			reply.setReply("REPLY.............");
			reply.setReplyer("replyer00");
			reply.setBoard(board);
			
			replies.add(reply);
			
			board.setReplies(replies);
			
			boardRepo.save(board);			
		});		
	}
	
	// 단방향 방식 댓글 추가
	@Test
	public void insertReply1Way() {
		FreeBoard board = new FreeBoard();
		board.setBno(199L);
		
		FreeBoardReply reply = new FreeBoardReply();
		reply.setReply("REPLY................");
		reply.setReplyer("replyer00");
		reply.setBoard(board);
		
		replyRepo.save(reply);
	}
	
	// 페이징 - 쿼리메소드 사용
	@Test
	public void testList1() {
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		
		boardRepo.findByBnoGreaterThan(0L, page).forEach(board -> {
			log.info(board.getBno() + ": " + board.getTitle());
		});
	}
	
	// 댓글의 개수 같이 출력해보기
	@Transactional   // 지연로딩 + 댓글 개수 가져오기
	@Test
	public void testList2() {
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		
		boardRepo.findByBnoGreaterThan(0L, page).forEach(board -> {
			log.info(board.getBno() + ": " + board.getTitle() + ":" 
					+ board.getReplies().size());  // 댓글의 개수 출력
		});
	}
	
	// @Query + Fetch Join 사용
	@Test
	public void testList3() {
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		
		boardRepo.getPage(page).forEach(arr -> 
				log.info(Arrays.toString(arr)));
	}
	
	
	
	

}























