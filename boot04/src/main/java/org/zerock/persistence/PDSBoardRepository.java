package org.zerock.persistence;

import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.PDSBoard;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, String> {

}


