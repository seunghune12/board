package com.example.demo.Repository;

import com.example.demo.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Modifying
    @Query(value = "update BoardEntity b set b.BoardLike=b.BoardLike+1 where b.id=:id")
    void updateHist(@Param("id")Long id);

    @Modifying
    @Query(value = "update BoardEntity b set b.BoardDislike=b.BoardDislike+1 where b.id=:id")
    void updateLike(@Param("id")Long id);

    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateDisike(@Param("id")Long id);
    //등록 가능한 seq 값을 찾는 명령(JPQL)
    //- 동일 그룹, 하단 게시글 중 차수가 같거나 작은 글 중 가장 작은 seq를 구한다
    //- 없을 경우 null이므로 반드시 Long 형태로 처리(혹은 nvl 처리가 필요)
    @Query("select min(b.seq) from BoardEntity b where b.grp = :#{#origin.grp} and b.seq > :#{#origin.seq} and b.dep <= :#{#origin.dep}")
    Long getAvailableSeq(BoardEntity origin);

    //naming query이므로 JPQL 작성 불필요
    long countByGrp(Long grp);

    @Modifying
    @Transactional
    @Query("update BoardEntity b set b.seq = b.seq + 1 where b.grp = :#{#board.grp} and b.seq >= :#{#board.seq}")
    int increaseSequence(BoardEntity board);

    @Modifying
    @Transactional
    @Query("update BoardEntity b set b.seq = b.seq - 1 where b.grp = :#{#board.grp} and b.seq >= :#{#board.seq}")
    int decreaseSequence(BoardEntity board);

    @Modifying
    @Query(value = "UPDATE BoardEntity b set b.grp = :id, b.dep= 0, b.seq = 0 where b.id=:id")
    @Transactional
    void modifyDep(@Param(value="id") Long id);

}
