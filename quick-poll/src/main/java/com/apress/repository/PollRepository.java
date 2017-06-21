package com.apress.repository;

import com.apress.domain.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by anthonyjones on 6/19/17.
 */
public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
}
