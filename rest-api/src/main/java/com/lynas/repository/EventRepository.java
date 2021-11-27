package com.lynas.repository;

import com.lynas.model.Event;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface EventRepository extends ReactiveCrudRepository<Event, String> {
    @Query("SELECT min(value) FROM event WHERE type like :type and cluster_id like :clusterId " +
            "and timestamp between :fromTime and :toTime")
    Mono<Double> findMin(String type, String clusterId, String fromTime, String toTime);

    @Query("SELECT max(value) FROM event WHERE type like :type and cluster_id like :clusterId " +
            "and timestamp between :fromTime and :toTime")
    Mono<Double> findMax(String type, String clusterId, String fromTime, String toTime);

    @Query("SELECT avg(value) FROM event WHERE type like :type and cluster_id like :clusterId " +
            "and timestamp between :fromTime and :toTime")
    Mono<Double> findAverage(String type, String clusterId, String fromTime, String toTime);

    @Query("WITH Numbered AS(SELECT *, COUNT(*) OVER () AS Cnt,ROW_NUMBER() OVER (ORDER BY value) AS RowNum " +
            "FROM event where type like :type and cluster_id like :clusterId " +
            "and timestamp between :fromTime and :toTime ) SELECT value FROM Numbered " +
            "WHERE RowNum IN ((Cnt+1)/2, (Cnt+2)/2);")
    Mono<Double> findMedian(String type, String clusterId, String fromTime, String toTime);

}
