package project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project3.model.Data;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
}
