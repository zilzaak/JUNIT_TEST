package EmployeTest.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Edu.class)
public abstract class Edu_ {

	public static volatile SingularAttribute<Edu, String> college;
	public static volatile SingularAttribute<Edu, Integer> workerid;
	public static volatile SingularAttribute<Edu, LocalDate> passyear;
	public static volatile SingularAttribute<Edu, String> name;
	public static volatile SingularAttribute<Edu, Integer> id;

	public static final String COLLEGE = "college";
	public static final String WORKERID = "workerid";
	public static final String PASSYEAR = "passyear";
	public static final String NAME = "name";
	public static final String ID = "id";

}

