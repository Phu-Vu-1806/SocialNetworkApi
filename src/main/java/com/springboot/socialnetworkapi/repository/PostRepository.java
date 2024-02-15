package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Group;
import com.springboot.socialnetworkapi.entity.Post;
import com.springboot.socialnetworkapi.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    Page<Post> findAllByGroup(Group group, Pageable pageable);

    @Query(value = "SELECT * FROM Posts p WHERE p.profile_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Post getNewPost(Long profileId);
}
