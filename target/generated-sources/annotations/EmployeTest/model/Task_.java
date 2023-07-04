package EmployeTest.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ {

	public static volatile SingularAttribute<Task, LocalDate> date;
	public static volatile SingularAttribute<Task, String> name;
	public static volatile SingularAttribute<Task, Integer> id;
	public static volatile SingularAttribute<Task, String> technology;
	public static volatile SingularAttribute<Task, Worker> worker;

	public static final String DATE = "date";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String TECHNOLOGY = "technology";
	public static final String WORKER = "worker";

}

