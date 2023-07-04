package EmployeTest.repo;

import EmployeTest.model.Task;
import EmployeTest.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface Taskrepo extends JpaRepository<Task,Integer> , JpaSpecificationExecutor<Task> {




}
