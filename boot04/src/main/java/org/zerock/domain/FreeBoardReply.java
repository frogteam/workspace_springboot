package org.zerock.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "board")
@Entity
@Table(name="tbl_free_replies", 
	// 테이블 생성시 인덱스 설계
	indexes = {@Index(unique = false, columnList = "board_bno")})
@EqualsAndHashCode(of="rno")
public class FreeBoardReply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	private String reply;
	private String replyer;
	
	@CreationTimestamp // INSERT 시 시간 자동 저장
	private Timestamp replydate;
	@CreationTimestamp // INSERT 시 시간 자동 저장
	private Timestamp updatedate;
	
	@ManyToOne
	private FreeBoard board;

}

























