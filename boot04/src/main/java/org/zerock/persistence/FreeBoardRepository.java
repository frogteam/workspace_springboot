package org.zerock.persistence;

import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.FreeBoard;

public interface FreeBoardRepository extends 
				CrudRepository<FreeBoard, Long> {

}
