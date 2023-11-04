package com.phanlop.khoahoc.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phanlop.khoahoc.Entity.File;

public interface FileRepository extends JpaRepository<File, UUID> {
}