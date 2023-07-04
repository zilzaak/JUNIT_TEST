package EmployeTest.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="edu")
public class Edu {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int id;
int workerid;


  @Column
  @NotEmpty(message = "dsfsdjhsdjfhsdjfhsdj")
  String name;

  @Size(min=4 , max=10 , message = "ohhhhhhhhhhhhhhh")
    String college;

   @JsonFormat(shape=JsonFormat.Shape.STRING ,pattern="dd-MM-yyyy")
   LocalDate passyear;

}
