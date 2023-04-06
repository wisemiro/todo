package com.example.repository;

import com.example.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository is particularly a JPA specific extension for Repository.
// It has full API CrudRepository and PagingAndSortingRepository.
// So, basically, Jpa Repository contains the APIs for basic CRUD operations,
// the APIS for pagination, and the APIs for sorting.

public interface TodoRepository extends JpaRepository<Todo, Long> {
}