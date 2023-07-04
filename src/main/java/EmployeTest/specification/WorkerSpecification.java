package EmployeTest.specification;

import EmployeTest.model.Task;
import EmployeTest.model.Worker;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WorkerSpecification {


    public static Specification<Worker> workerById(Long id) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), id);

        };

    }

    public static Specification<Worker> workerByName(String name) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), name);

        };
    }

    public static Specification<Worker> workerByNameLike(String name) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("name"), name);

        };
    }

    public static Specification<Worker> workerBysalaryGreater(int salary) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), salary);

        };
    }


    public static Specification<Worker> workerBySalaryLess(int salary) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("salary"), salary);

        };
    }

    public static Specification<Worker> taskByWorkerId(int salary) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("salary"), salary);

        };
    }



    public static Specification<Worker> workerByJoinDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("joindate"), date);

        };
    }


    public static Specification<Worker> workerByJoinDateGreter(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("joindate"), date);

        };
    }

    public static Specification<Worker> workerByJoinDatLessThanEq(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("joindate"), date);

        };
    }


}
