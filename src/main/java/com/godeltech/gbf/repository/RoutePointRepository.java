package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.RoutePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoutePointRepository extends JpaRepository<RoutePoint, Long>, JpaSpecificationExecutor<RoutePoint> {
}
