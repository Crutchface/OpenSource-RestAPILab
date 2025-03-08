package org.oosd.restapidemo.repository;

import org.oosd.restapidemo.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepo extends JpaRepository<EmergencyContact, Long> {
}
