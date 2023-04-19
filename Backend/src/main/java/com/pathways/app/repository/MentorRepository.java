package com.pathways.app.repository;

import com.pathways.app.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    Optional<Mentor> findByEmail(String email);

    @Query("SELECT m FROM Mentor m JOIN m.user u WHERE u.isApproved = false")
    List<Mentor> findAllUnapprovedMentors();
}
