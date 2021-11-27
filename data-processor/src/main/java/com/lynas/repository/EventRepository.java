package com.lynas.repository;

import com.lynas.model.EventDB;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EventRepository extends ReactiveCrudRepository<EventDB, String> {}
