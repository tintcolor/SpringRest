package com.apress.repository;

import com.apress.domain.Poll;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by anthonyjones on 6/19/17.
 */
public interface PollRepository extends CrudRepository<Poll, Long> {
}
