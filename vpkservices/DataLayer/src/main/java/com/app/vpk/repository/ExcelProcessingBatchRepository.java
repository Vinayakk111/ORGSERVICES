package com.app.vpk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.vpk.entity.ExcelProcessingBatch;

public interface ExcelProcessingBatchRepository extends JpaRepository<ExcelProcessingBatch, Long> {
}
