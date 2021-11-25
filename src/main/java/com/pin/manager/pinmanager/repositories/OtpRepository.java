package com.pin.manager.pinmanager.repositories;

import com.pin.manager.pinmanager.entities.OtpDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OtpDetail, String> {
}
