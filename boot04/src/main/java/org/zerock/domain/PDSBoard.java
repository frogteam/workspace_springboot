package org.zerock.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "files")  // files 는 toString() 에서 제외.
@Entity
@Table(name="tbl_pds")
@EqualsAndHashCode(of="pid")
public class PDSBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pid;
	private String pname;
	private String pwriter;
	
	
	@OneToMany
	//@OneToMany(cascade= CascadeType.ALL) // 영속성 전이 설정
	@JoinColumn(name="pdsno")  // tbl_pdsfiles 에 컬럼 (외래키) 추가된다.
	private List<PDSFile> files;
}















