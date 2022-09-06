package mat.mat_t.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mat.mat_t.domain.class_.ClassStatus;
import mat.mat_t.domain.class_.ClassStudents;
import mat.mat_t.domain.class_.WaitingStudent;
import mat.mat_t.domain.user.User;
import mat.mat_t.web.service.ClassService;
import mat.mat_t.web.service.ClassStudentsService;
import mat.mat_t.web.service.WaitingStudentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class WaitingStudentController {

    private final WaitingStudentsService waitingStudentsService;
    private final ClassService classService;
    private final ClassStudentsService classStudentsService;

    @ApiOperation("클래스 신청한 학생 DB에 저장")
    @PostMapping("/waitingStudent/add/{classId}")
    public ResponseEntity add(@PathVariable Long classId,
                              @RequestParam String content,
                              HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute("loginUser");

        WaitingStudent waitingStudent = new WaitingStudent();
        waitingStudent.setUserWS(loginUser);
        waitingStudent.setClassesWS(classService.findById(classId));
        waitingStudent.setContent(content);
        waitingStudent.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        waitingStudentsService.add(waitingStudent);

        return ResponseEntity.ok(waitingStudent);
    }

    @ApiOperation("해당 클래스에 신청한 학생들 조회")
    @GetMapping("/waitingStudent/list/{classId}")
    public ResponseEntity listStudents(@PathVariable Long classId) {
        List<WaitingStudent> classStudents = waitingStudentsService.findStudentsByClassId(classId);

        List<WsDto> classStudentsDto = classStudents.stream()
                .map(c -> new WsDto(c))
                .collect(Collectors.toList());

        return ResponseEntity.ok(classStudentsDto);
    }


    @ApiOperation("클래스 신청 수정")
    @PatchMapping("/waitingStudent/edit/{wsId}")
    public ResponseEntity editWs(@PathVariable Long wsId,
                                 @RequestParam String content) {
        WaitingStudent updateStudent = waitingStudentsService.update(wsId, content);
        return ResponseEntity.ok(updateStudent);
    }

    @ApiOperation("클래스 신청 삭제")
    @DeleteMapping("/waitingStudent/delete/{wsId}")
    public void deleteWs(@PathVariable Long wsId) {
        waitingStudentsService.delete(wsId);
    }

    @ApiOperation("강사가 수락 시 ws는 삭제 cs는 추가")
    @PostMapping("/waitingStudent/transfer/{wsId}")
    public ResponseEntity transferFromWsToCs(@PathVariable Long wsId) {
        ClassStudents classStudent = waitingStudentsService.transfer(wsId);
        classStudentsService.saveClassStudents(classStudent);
        CsDto csDto = new CsDto();
        csDto.setCsDto(classStudent);
        return ResponseEntity.ok().body(csDto);
    }

    @Getter
    static class WsDto {
        Long id;
        String name;
        String content;

        String date;
        public WsDto(WaitingStudent waitingStudent) {
            id = waitingStudent.getWaitingId();
            name = waitingStudent.getUserWS().getName();
            content = waitingStudent.getContent();
            date = waitingStudent.getDate();
        }
    }

    @Getter
    static class CsDto {

        Long cs_id;
        ClassStatus status;
        Long class_id;
        Long student_id;

        public void setCsDto(ClassStudents classStudents) {
            this.cs_id = classStudents.getClassStudentId();
            this.status = classStudents.getStatus();
            this.class_id = classStudents.getClassesCS().getClassId();
            this.student_id = classStudents.getUserCS().getId();
        }
    }

}
