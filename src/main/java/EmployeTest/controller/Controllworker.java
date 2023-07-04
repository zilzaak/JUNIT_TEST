package EmployeTest.controller;

import EmployeTest.model.Edu;
import EmployeTest.model.Task;
import EmployeTest.model.Worker;
import EmployeTest.repo.Taskrepo;
import EmployeTest.repo.Workerrepo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/worker")
public class Controllworker {

   @Autowired
   private   Workerrepo wrr;
    @Autowired
    private Taskrepo trr;

    @PersistenceContext
    private EntityManager em;

    @PostMapping("/add")
public ResponseEntity<?> addworker(@RequestBody Worker worker){
wrr.save(worker);

    return new  ResponseEntity<Worker>(worker, HttpStatus.OK);
}

    @GetMapping("/byWorkerId/{id}")
    public ResponseEntity<?> byWorkerId(@PathVariable Integer id){
        Worker wk=wrr.findById(id).get();
        Task w=wk.getTasks().get(0);
        System.out.println("worker name "+w.getName()+" worker age"+w.getName()+" worker"+w.getDate());
        int h=45+556;
        int k=h+2;
        System.out.println("worker name "+w.getName()+" worker age"+w.getName()+" worker"+w.getDate());
        return new  ResponseEntity<Worker>(wk, HttpStatus.OK);
    }

    @GetMapping("/deleteChild/{id}")
    public ResponseEntity<?> deleteChild(@PathVariable Integer id){
        Worker wk=wrr.findById(id).get();
        List<Task> lst=wk.getTasks();
        lst.clear();
        System.out.println("before removing the list size is "+lst.size());
        Task f=new Task();
        f.setName("xxxxxxxx");f.setDate(LocalDate.now());
        f.setTechnology("xxxxxxxxxxxxxxxxx");
        f.setWorker(wk);
        Task f2=new Task();
        f2.setWorker(wk);
        f2.setName("xxxxxxxx");f2.setDate(LocalDate.now());
        f2.setTechnology("xxxxxxxxxxxxxxxxx");
        lst.add(f);
        lst.add(f2);
        System.out.println("after removing the list size is "+lst.size());
        wk.setTasks(lst);
        wrr.save(wk);
        return new  ResponseEntity<Worker>(wk, HttpStatus.OK);
    }


    @GetMapping("/byTaskId/{id}")
    public ResponseEntity<?> byTaskId(@PathVariable Integer id){
        Task wk=trr.findById(id).get();

        Worker w=wk.getWorker();
        System.out.println("worker name "+w.getName()+" worker age"+w.getDob()+" worker"+w.getNid());
        int h=45+556;
        int k=h+2;
        System.out.println("worker name "+w.getName()+" worker age"+w.getDob()+" worker"+w.getNid());
        return new  ResponseEntity<Task>(wk, HttpStatus.OK);
    }

    @GetMapping("/datebetween")
    public ResponseEntity<?> datebetween(@RequestParam String d1 ,@RequestParam String d2 ) throws ParseException {

        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
        Date day1=null;

        Date day2=null ;

        if(d1.length()>0){
            day1=sdf.parse(d1);
        }
        if(d2.length()>0){
            day2=sdf.parse(d2);
        }

     List<Map<String,Object>> lst=wrr.getdateBetween(day1,day2) ;
        return new  ResponseEntity<>(lst, HttpStatus.OK);
    }


    @GetMapping("/getbyname/{name}")
    public ResponseEntity<?> getbyname(@PathVariable String name ){

        Worker wk = wrr.findByName(name);
        return new  ResponseEntity<>(wk, HttpStatus.OK);
    }


    @GetMapping("/nameilike/{name}")
    public ResponseEntity<?> nameilike(@PathVariable String name ){

        Long  wk = wrr.getnameignorecase(name);
        return new  ResponseEntity<>(wk, HttpStatus.OK);
    }



    @GetMapping("/namedate/{name}/{date1}/{date2}")
    public ResponseEntity<?> getbyname(@PathVariable String name ,
                                       @PathVariable String date1 ,
                                       @PathVariable String date2){

        LocalDate joinDate = LocalDate.parse(date1,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate admitDate = LocalDate.parse(date2,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<LocalDate> lst=new ArrayList<>();
        lst.add(joinDate);
        lst.add(admitDate);
        Boolean wk = wrr.existsByNameAndJoindateInOrAdmitDateIn(name,lst,lst);
       // Long wk = wrr.checkNameExistOrNot2(name,joinDate,admitDate);
        return new  ResponseEntity<>(wk, HttpStatus.OK);
    }



    @GetMapping("/find")
    public ResponseEntity<?> AndindUsingJpaSpecfiations(@RequestParam Map<String,String> cp){
        DateTimeFormatter df=DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<Worker> allworker=wrr.findAll(
                (Specification<Worker>)     (root , cq , cb )->{
                    Join<Worker,Task> jointable= root.join("tasks", JoinType.LEFT);
                    Predicate p=cb.conjunction();
if(cp.containsKey("name")){
    if(cp.get("name")!=null || cp.get("name")!=""){
        p=cb.and(p,cb.equal(root.get("name"), cp.get("name")));
    }
}

              if(cp.containsKey("salary")){
                  if(cp.get("salary")!=null || cp.get("salary")!="" ){
                      p=cb.and(p,cb.gt(root.get("salary"),Integer.valueOf(cp.get("salary").toString())));
                  }
              }
              if(cp.containsKey("wid") && cp.get("wid")!=null){
                  p=cb.and(p,cb.equal(jointable.get("id"),Integer.valueOf(cp.get("wid").toString())));
                    }

                  if(cp.containsKey("date1") && cp.containsKey("date2")){
                      if(cp.get("date1")!=null && cp.get("date2")!=null){
                          try{
                              p=cb.and(p,cb.between(root.get("joindate"), LocalDate.parse(cp.get("date1"),df),
                                      LocalDate.parse(cp.get("date2"),df)  ));
                          }catch(Exception e){
                          }}}


                    if(cp.containsKey("taskname")){
                        if(cp.get("taskname")!=null){
                            try{
                                p=cb.and(p,cb.equal(jointable.get("name"), cp.get("taskname")));

                            }catch(Exception e){
                            }}}



                    return p;
                }

        );

        return new  ResponseEntity<List<Worker>>(allworker, HttpStatus.OK);


    }



    @GetMapping("/find2")
    public ResponseEntity<?> AndingCriteriaRestrictions(@RequestParam Map<String,String> cp){
        DateTimeFormatter df=DateTimeFormatter.ofPattern("dd-MM-yyyy");Session s = em.unwrap(Session.class);
Criteria cr=s.createCriteria(Worker.class);
if(cp.isEmpty()){
    return new  ResponseEntity<List<Worker>>(Arrays.asList(new Worker()), HttpStatus.OK);
}
DateTimeFormatter f=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    if(cp.containsKey("name")){
                        if(cp.get("name")!=null || cp.get("name")!=""){
                            Criterion c1= Restrictions.like("name","%"+cp.get("name")+"%");
                          cr.add(c1);
                        }
                    }

                    if(cp.containsKey("salary")){
                        if(cp.get("salary")!=null || cp.get("salary")!="" ){
                            Criterion c1= Restrictions.gt("salary",Integer.valueOf(cp.get("salary").toString()));
                            cr.add(c1);

                        }
                    }

                    if(cp.containsKey("wid") && cp.get("wid")!=null){
                        Criterion c1= Restrictions.eq("id",Integer.valueOf(cp.get("wid").toString()));
                        cr.add(c1);
                    }

                    if(cp.containsKey("date1") && cp.containsKey("date2")){
                        if(cp.get("date1")!=null && cp.get("date2")!=null){

                            try{
                                Criterion c1= Restrictions.between("joindate",
                                        LocalDate.parse(cp.get("date1")),
                                        LocalDate.parse(cp.get("date2")));
                                cr.add(c1);

                            }
                            catch(Exception e){

                            }}
                    }
                    List<Worker> allworker = cr.list();
                    return new  ResponseEntity<List<Worker>>(allworker, HttpStatus.OK);
    }


    @GetMapping("/existbyname")
    public ResponseEntity<?> existbyname(@RequestParam Map<String,String> cp){

   String sql = " select a.name , a.id from Worker a ";

   TypedQuery<Tuple> query = em.createQuery(sql,Tuple.class);
   List<Tuple> LIST= query.getResultList();

        return new  ResponseEntity<>(LIST, HttpStatus.OK);
    }


    @GetMapping("/oring")
    public ResponseEntity<?> OringUsingCriteriaRestrictions(@RequestParam Map<String,String> cp){
        DateTimeFormatter df=DateTimeFormatter.ofPattern("dd-MM-yyyy");Session s = em.unwrap(Session.class);
        Criteria cr=s.createCriteria(Worker.class);
        if(cp.isEmpty()){
            return new  ResponseEntity<List<Worker>>(Arrays.asList(new Worker()), HttpStatus.OK);
        }
        Criterion c1=null; Criterion c2=null; Criterion c3=null;Criterion c4=null;
        List<Criterion> lst=new ArrayList<>();
        DateTimeFormatter f=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(cp.containsKey("name")){
            if(cp.get("name")!=null || cp.get("name")!=""){

                 c1= Restrictions.like("name","%"+cp.get("name")+"%");
                 if(c1!=null){
                     lst.add(c1);
                 }
            }
        }

        if(cp.containsKey("salary")){
            if(cp.get("salary")!=null || cp.get("salary")!="" ){
              c2= Restrictions.gt("salary",Integer.valueOf(cp.get("salary").toString()));
                if(c2!=null){
                    lst.add(c2);
                }

            }
        }

        if(cp.containsKey("wid") && cp.get("wid")!=null){
          c3= Restrictions.eq("id",Integer.valueOf(cp.get("wid").toString()));
            if(c3!=null){
                lst.add(c3);
            }
        }

        if(cp.containsKey("date1") && cp.containsKey("date2")){
            if(cp.get("date1")!=null && cp.get("date2")!=null){

                try{
                     c4= Restrictions.between("joindate",
                            LocalDate.parse(cp.get("date1")),
                            LocalDate.parse(cp.get("date2")));
                    if(c4!=null){
                        lst.add(c4);
                    }
                }
                catch(Exception e){

                }}
        }
        for(Criterion c : lst){
            cr.add(Restrictions.or(c));
        }


        List<Worker> allworker = cr.list();
        return new  ResponseEntity<List<Worker>>(allworker, HttpStatus.OK);
    }

    @GetMapping("/oring2")
    public ResponseEntity<?> OringUsingJpaSpecfiations(@RequestParam Map<String,String> cp){
        DateTimeFormatter df=DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<Worker> allworker=wrr.findAll(
                (Specification<Worker>)     (root , cq , cb )->{

                    Predicate p=cb.disjunction();
                    if(cp.containsKey("name")){
                        if(cp.get("name")!=null || cp.get("name")!=""){
                            p=cb.or(p,cb.like(root.get("name"), "%"+cp.get("name")+"%"));
                        }
                    }

                    if(cp.containsKey("salary")){
                        if(cp.get("salary")!=null || cp.get("salary")!="" ){
                            p=cb.or(p,cb.gt(root.get("salary"),Integer.valueOf(cp.get("salary").toString())));
                        }
                    }
                    if(cp.containsKey("wid") && cp.get("wid")!=null){
                        p=cb.or(p,cb.equal(root.get("id"),Integer.valueOf(cp.get("wid").toString())));
                    }

                    if(cp.containsKey("date1") && cp.containsKey("date2")){
                        if(cp.get("date1")!=null && cp.get("date2")!=null){
                            try{
                                p=cb.or(p,cb.between(root.get("joindate"), LocalDate.parse(cp.get("date1"),df),
                                        LocalDate.parse(cp.get("date2"),df)  ));
                            }catch(Exception e){
                            }}}
                    return p;
                });

        return new  ResponseEntity<List<Worker>>(allworker, HttpStatus.OK);

    }

@GetMapping("/specific1")
    public ResponseEntity<?>  specificcolumnFetch(){
        int f=20000; int id=10;int id2=21 ;
        Session session=em.unwrap(Session.class);
        Query query=session.createQuery("select s.id , s.salary from Worker s where s.salary>"+f+"and s.id>"+id+"and s.id<"+id2);
List<Object[]> rows=query.list();
      return new  ResponseEntity<List<Object[]>>(rows, HttpStatus.OK);

}

    @PostMapping("/addedu")
    public ResponseEntity<?>  specificcolumnFetch  (@Valid  @RequestBody Edu ed ){
        Session session=em.unwrap(Session.class);
        session.save(ed);
        return new  ResponseEntity<Edu>(ed, HttpStatus.OK);

    }


    @GetMapping("/winner")
    public ResponseEntity<?>  workereduinner( ){

        String k="chadni akter";
        Session session=em.unwrap(Session.class);
        Query query=session.createQuery("select s.id , s.salary , e.name , e.college from Worker s , Edu e where " +
                        "e.workerid=s.id and e.name="
                +"'"+k+"'");
        List<Object[]> rows=query.list();
        return new  ResponseEntity<List<Object[]>>(rows, HttpStatus.OK);
    }

    @GetMapping("/leftjoin/{pi}/{ps}")
    public ResponseEntity<?>  leftjoin( @PathVariable int ps , @PathVariable int pi){
   String q="SELECT e.name,e.college, e.passyear from Edu e  left join Worker w ON e.workerid = w.id";

        Session session=em.unwrap(Session.class);
        Query query=session.createQuery(q).setMaxResults(ps).setFirstResult(pi);
        List<Object[]> rows=query.list();
        return new  ResponseEntity<List<Object[]>>(rows, HttpStatus.OK);
    }


    @GetMapping("/rightjoin/{pi}/{ps}")
    public ResponseEntity<?>  rightjoin( @PathVariable int ps , @PathVariable int pi){
        String q="select e.name,e.id, e.salary , e.joindate from Worker e  left join Edu w ON e.id = w.workerid and w.name='chadni akter'";

        Session session=em.unwrap(Session.class);
        Query query=session.createQuery(q);
        List<Object[]> rows=query.list();
        Map<String,Object> response=new HashMap<>();
        response.put("total reord",rows.stream().count());
        response.put("list",rows);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/join")
    public ResponseEntity<?>  rightjoindfd(){
        String q="select distinct a.name from Worker a";

        Session session=em.unwrap(Session.class);
        Query query=session.createQuery(q);
        List<Object[]> rows=query.list();
        Map<String,Object> response=new HashMap<>();
        response.put("total reord",rows.stream().count());
        response.put("list",rows);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/OR")
    public ResponseEntity<?> Orrrs(@RequestParam Map<String,String> cp){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Worker> criteriaQuery = criteriaBuilder.createQuery(Worker.class);
        Root<Worker> itemRoot = criteriaQuery.from(Worker.class);
        Predicate chadni= criteriaBuilder.equal(itemRoot.get("name"), "chadni akter");
        Predicate alam = criteriaBuilder.equal(itemRoot.get("name"), "noor alam");
        Predicate chadniOrAlam= criteriaBuilder.or(chadni,alam);

        Predicate salary
                = criteriaBuilder.gt(itemRoot.get("salary"), 50000);
       /* Predicate predicateForGradeB
                = criteriaBuilder.equal(itemRoot.get("grade"), "B");
        Predicate predicateForGrade
                = criteriaBuilder.or(predicateForGradeA, predicateForGradeB);*/

       Predicate finalpredicate= criteriaBuilder.and(chadniOrAlam, salary);
        criteriaQuery.where(finalpredicate);
        Integer f = em.createQuery(criteriaQuery).getResultList().size();
        return new  ResponseEntity<Integer>(f, HttpStatus.OK);
    }



    @GetMapping("/OR2")
    public ResponseEntity<?> Orrrs2(@RequestParam Map<String,String> cp){
        List<Worker> allworker=wrr.findAll(
                (Specification<Worker>)     (root , cq , cb )->{
                    Predicate p=cb.disjunction();
                    Predicate p2=cb.conjunction();
                    Predicate p3=cb.conjunction();
                    p=cb.or(p,cb.equal(root.get("name"),"chadni akter"));
                    p=cb.or(p,cb.equal(root.get("name"),"noor alam"));
                    p2=cb.and(p2,cb.lt(root.get("salary"),60000));
                     p3=cb.and(p,p2);
                    return p3;
                });

        return new  ResponseEntity<List<Worker>>(allworker, HttpStatus.OK);
    }


    @GetMapping("/select")
    public ResponseEntity<?> select(@RequestParam Map<String,String> cp){
 CriteriaBuilder cb=em.getCriteriaBuilder();
CriteriaQuery<Tuple> cq= cb.createQuery(Tuple.class);
Root<Worker> root = cq.from(Worker.class);
Path<Object> name=root.get("name");
        Path<Object> id1=root.get("id");
Path<Object> salary=root.get("salary");
        Root<Task> root2 = cq.from(Task.class);
        Path<Object> n=root2.get("name");
        Path<Object> d=root2.get("date");
        Path<Object> id2=root2.get("id");

List<DtoW> lst=new ArrayList<>();
        cq.multiselect(name,salary,n,d,id1,id2).where(cb.equal(root.get("id"),root2.get("worker").get("id")));

List<Tuple> results = em.createQuery(cq).getResultList();

        List<Tuple> results2 = em.createQuery(cq).getResultList();

        Tuple tx = results2.get(2);

for(Tuple t : results){
    DtoW x=new DtoW();
    x.setName((String) t.get(0));
    x.setId1((Integer) t.get(4));
    x.setId2((Integer) t.get(5));

lst.add(x);

}

if(results2.contains(tx)){
    return new  ResponseEntity<>(tx, HttpStatus.OK);
}


        return new  ResponseEntity<>(lst, HttpStatus.OK);
    }
    @GetMapping("/selectall")
    public ResponseEntity<?> selectall(@RequestParam Map<String,String> cp){
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Worker> cq= cb.createQuery(Worker.class);
        Root<Worker> root = cq.from(Worker.class);
        cq.select(root);
        List<Worker> results = em.createQuery(cq).getResultList();

        return new  ResponseEntity<List<Worker>>(results, HttpStatus.OK);
    }


    @GetMapping("/existbyin")
    public ResponseEntity<?> existbyin(){
List<String> lst=Arrays.asList("chad akter","peeotnixxx");
if(wrr.existsByNameIgnoreCaseInAndId(lst,4)){
    return new  ResponseEntity<>("the list exist", HttpStatus.OK);
}

        return new  ResponseEntity<>("the list not exist", HttpStatus.OK);

    }

    @GetMapping("/names")
    public ResponseEntity<?> finddistinctnamenotin(){
        List<String> name=Arrays.asList("chadni akter","kupassa");
        List<String> lst=wrr.findNonReferencedNames(name);
        return new  ResponseEntity<>(lst, HttpStatus.OK);

    }

List<LocalDate> lst=new ArrayList<>();

    @GetMapping("/test")
    public ResponseEntity<?> finddistidfghdfnctnamenotin(){

        lst=addvalues();
if(lst.size()>0){
    System.out.println(lst.size());
}
        lst.remove(LocalDate.now());

        return new  ResponseEntity<>(lst, HttpStatus.OK);

    }

    private List<LocalDate> addvalues() {

if(lst.size()<1){
    lst.add(LocalDate.now());
    lst.add(LocalDate.parse("2023-02-12",DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    lst.add(LocalDate.parse("2025-02-12",DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    lst.add(LocalDate.parse("2024-02-12",DateTimeFormatter.ofPattern("yyyy-MM-dd")));
}

return lst;

    }
    @GetMapping("/spec/{namd}")
    public ResponseEntity<?> spec(@PathVariable String namd){
    List<String> getNames = wrr.getnamelike(namd.toUpperCase());
        return new  ResponseEntity<>(getNames, HttpStatus.OK);

    }

    @GetMapping("/casewhen/{nam}")
    public ResponseEntity<?> dfsgdf(@PathVariable String nam  ){
        Integer Salary=null;
        List<Worker> getNames = wrr.getcasewhen(nam,Salary);
        return new  ResponseEntity<>(getNames, HttpStatus.OK);

    }

}
