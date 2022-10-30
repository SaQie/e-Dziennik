package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

@RestController
@AllArgsConstructor
public class TestController {

    private final TeacherDao dao;

    @GetMapping("/asd")
    public void asdasd(){
        Teacher test = dao.findByUsername("K1a1m1i1l1222");
        System.out.println("asdasd");
    }


}
