package org.zerock.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="tbl_freeboards")
@EqualsAndHashCode(of="bno")
public class FreeBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	private String title;
	private String writer;
	private String content;
	
	@CreationTimestamp // INSERT 시 시간 자동 저장
	private Timestamp regdate;
	@CreationTimestamp // INSERT 시 시간 자동 저장
	private Timestamp updatedate;	
	
	// mappedBy 종속적인 클래스의 인스턴스 변수 지정
	@OneToMany(mappedBy = "board")  
	private List<FreeBoardReply> replies;
}















