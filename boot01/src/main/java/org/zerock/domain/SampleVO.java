package org.zerock.domain;

import lombok.Data;
import lombok.ToString;

// 선언부의 어노테이션 설정만으로도 컴파일시 
// getter/setter/toString() 을 자동으로 작성
/*
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
*/

@Data
@ToString(exclude = {"val3"})
public class SampleVO {
	private String val1;
	private String val2;
	private String val3;
}

