package EmployeTest.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="worker")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

private String name;
private int salary;
@JsonFormat(shape=JsonFormat.Shape.STRING ,pattern="dd-MM-yyyy")
private LocalDate joindate;

    @JsonFormat(shape=JsonFormat.Shape.STRING ,pattern="dd-MM-yyyy")
    private LocalDate admitDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING ,pattern="dd-MM-yyyy")
    @CreationTimestamp
    private Date admitDate2;

@OneToMany(
        mappedBy = "worker",
        cascade = CascadeType.ALL,fetch=FetchType.LAZY,
        orphanRemoval = true)
@JsonManagedReference
@JsonInclude(JsonInclude.Include.NON_NULL)
private List<Task> tasks;

@Transient
private String nid;

@Transient
private String dob;








}
