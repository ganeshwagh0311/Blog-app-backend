package com.ganesh.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ganesh.blog.entities.contact; // ✅ Correct import

public interface ContactRepo extends JpaRepository<contact, Long> {
    // No need to override save()
}
