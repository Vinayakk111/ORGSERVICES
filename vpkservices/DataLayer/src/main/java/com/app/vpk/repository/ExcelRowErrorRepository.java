package com.app.vpk.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.vpk.entity.ExcelRowError;

public interface ExcelRowErrorRepository extends JpaRepository<ExcelRowError, Long> {

    // Spring Data resolves this via the batch.id nested path.
    List<ExcelRowError> findByBatchId(Long batchId);
}
