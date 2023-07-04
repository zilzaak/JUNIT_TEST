package EmployeTest.EmployeTest;

import EmployeTest.model.Task;
import EmployeTest.model.Worker;
import EmployeTest.repo.Workerrepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ControllworkerTest {
    @Autowired
    private Workerrepo wrr;

    @Test
    public void omarBorkanAlGala(){
        Worker wk=wrr.findById(3).get();
        Task w=wk.getTasks().get(0);
        System.out.println("worker name is "+w.getName()+" worker age"+w.getName()+" worker"+w.getDate());
        int h=45+556;
        int k=h+2;
        System.out.println("worker name "+w.getName()+" worker age"+w.getName()+" worker"+w.getDate());

    }


}
