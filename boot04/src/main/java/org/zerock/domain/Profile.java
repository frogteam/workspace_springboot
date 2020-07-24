package org.zerock.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "member")  // member 제외, 꼭!
@Entity
@Table(name="tbl_profile")
@EqualsAndHashCode(of="fname")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fno;
	private String fname;
	private boolean current;
	
	@ManyToOne  // 프로필 -> 회원 은  N:1 관계 
	private Member member;  // 프로필 을 통해 회원정보 참조
}







