package EmployeTest.repo;

import EmployeTest.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Workerrepo extends JpaRepository<Worker,Integer> , JpaSpecificationExecutor<Worker> {

boolean existsByNameIgnoreCaseInAndId(List<String> lst,int id);

    @Query("SELECT DISTINCT name FROM Worker WHERE name NOT IN (:names)")
    List<String> findNonReferencedNames(@Param("names") List<String> names);

    @Query(" select count(x) from Worker x where x.name=?1 and " +
            " (  x.joindate between ?2 and ?3 " +
            " or x.admitDate between ?2 and ?3 ) ")
    Long checkNameExistOrNot(String name , LocalDate joinDate , LocalDate admitDate);

    @Query(" select avg(x.salary) from Worker x where x.name=?1 and " +
            " (  x.joindate between ?2 and ?3 " +
            " or x.admitDate between ?2 and ?3 ) ")
    Long checkNameExistOrNot2(String name , LocalDate joinDate , LocalDate admitDate);

    boolean existsByNameAndJoindateInOrAdmitDateIn(String name , List<LocalDate> lst, List<LocalDate> lst2);



    Worker findByName(String name);
    @Query(" select count(x.salary) from Worker x where UPPER(x.name)= UPPER(?1) ")
    Long getnameignorecase(String name);
    @Query(" select x.id as id , x.salary as salary , x.name as name  from Worker x where " +
            " ( CAST( :day1 AS date )  is null or  x.admitDate2  >= CAST( :day1 AS date )    )  and " +
            " ( CAST( :day2 AS date )  is null or  x.admitDate2  <= CAST( :day2 AS date )  )  " )
    List<Map<String, Object>> getdateBetween(@Param("day1") Date day1, @Param("day2") Date day2);

    @Query(" select  x.name    from Worker x where upper(x.name) like %:nam% ")
    List<String> getnamelike(@Param("nam") String nam);

    @Query("SELECT x FROM Worker x " +
            " where  ( ?1 IS NOT NULL and  x.name=?1 ) or " +
            " (?2 IS NOT NULL and x.salary=?2 ) ")
    List<Worker> getcasewhen(String nam,  Integer sal);
}
