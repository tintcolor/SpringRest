package com.apress.repository;

import com.apress.domain.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by anthonyjones on 6/19/17.
 */
public interface VoteRepository extends CrudRepository<Vote, Long> {

    @Query(value = "select v.* from Option o, Vote v where o.POLL_ID = ?1 and v.OPTION_ID = o.OPTION_ID", nativeQuery = true)
    Iterable<Vote> findByPoll(Long pollId);


}
