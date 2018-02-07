package info.jjhop.gdcollector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.jjhop.gdcollector.model.Data;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
}
