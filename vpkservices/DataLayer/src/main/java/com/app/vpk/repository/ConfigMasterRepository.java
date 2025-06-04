package com.app.vpk.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.vpk.entity.ConfigMaster;

public interface ConfigMasterRepository extends JpaRepository<ConfigMaster, Long> {
	Optional<ConfigMaster> findByConfigKeyAndIsActiveTrue(String configKey);
}
