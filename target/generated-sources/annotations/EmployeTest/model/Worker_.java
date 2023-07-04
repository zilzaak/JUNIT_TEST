package EmployeTest.model;

import java.time.LocalDate;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Worker.class)
public abstract class Worker_ {

	public static volatile SingularAttribute<Worker, LocalDate> joindate;
	public static volatile SingularAttribute<Worker, Date> admitDate2;
	public static volatile SingularAttribute<Worker, String> name;
	public static volatile SingularAttribute<Worker, LocalDate> admitDate;
	public static volatile SingularAttribute<Worker, Integer> id;
	public static volatile SingularAttribute<Worker, Integer> salary;
	public static volatile ListAttribute<Worker, Task> tasks;

	public static final String JOINDATE = "joindate";
	public static final String ADMIT_DATE2 = "admitDate2";
	public static final String NAME = "name";
	public static final String ADMIT_DATE = "admitDate";
	public static final String ID = "id";
	public static final String SALARY = "salary";
	public static final String TASKS = "tasks";

}

