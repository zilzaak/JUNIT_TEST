package EmployeTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @JsonFormat(shape=JsonFormat.Shape.STRING ,pattern="dd-MM-yyyy")
    private LocalDate date;
    private String technology;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="worker_id")
    @JsonBackReference
    private Worker worker;

}
