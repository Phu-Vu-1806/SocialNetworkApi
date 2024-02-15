package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Comment;
import com.springboot.socialnetworkapi.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findAllByProfile(Profile profile, Pageable pageable);
}
