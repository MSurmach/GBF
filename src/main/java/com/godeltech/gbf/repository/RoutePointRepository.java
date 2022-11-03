package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.RoutePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoutePointRepository extends JpaRepository<RoutePoint, Long>, JpaSpecificationExecutor<RoutePoint> {

    @Query(value = "select offer_id from route_point " +
            "where city_id IN :cities " +
            "GROUP BY offer_id " +
            "HAVING COUNT(offer_id)>=2", nativeQuery = true)
    List<Long> findOffersId(List<Integer> cities);
}
