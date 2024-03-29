package mat.mat_t.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mat.mat_t.domain.user.User;
import mat.mat_t.form.CommentCreateForm;
import mat.mat_t.form.CommentForm;
import mat.mat_t.form.CommentReDto;
import mat.mat_t.web.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /** 댓글 생성 **/
    @ApiOperation(value = "댓글 생성")
    @PostMapping(value = "/community/{communityId}/comment/create")
    public ResponseEntity createComment(@PathVariable Long communityId, @RequestParam String content,
            HttpServletRequest request) {
        CommentReDto dto = new CommentReDto();
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        commentService.createComments(loginUser.getLoginId(), communityId, dto, content);
        CommentCreateForm form = new CommentCreateForm(dto.getContent(), dto.getUser().getNickname(),
                dto.getCommunity().getId());
        return ResponseEntity.ok().body(form);
    }

    /** 댓글 전체 조회 **/
    @ApiOperation(value = "댓글 전체 조회")
    @GetMapping("/community/{communityId}/comments")
    public ResponseEntity<List<CommentForm>> readComment(@PathVariable Long communityId) {
        List<CommentForm> commentForm = new ArrayList<>();
        commentForm = commentService.Listcomments(communityId);
        return ResponseEntity.ok().body(commentForm);
    }

    /** 댓글아이디로 조회 **/
    @ApiOperation(value = "댓글아이디로 조회")
    @GetMapping("/community/comment/{commentId}")
    public ResponseEntity<CommentForm> readCommentById(@PathVariable Long commentId) {
        CommentForm commentForm = new CommentForm();
        commentForm = commentService.readComment(commentId);
        return ResponseEntity.ok().body(commentForm);
    }

    /** 댓글 수정 **/
    @ApiOperation(value = "댓글 수정")
    @PutMapping({ "/community/comment/{commentId}" })
    public ResponseEntity updateComment(@PathVariable Long commentId, @RequestParam String content) {
        commentService.updateComments(commentId, content);
        CommentForm commentForm = new CommentForm();
        commentForm = commentService.readComment(commentId);
        return ResponseEntity.ok().body(commentForm);
    }

    /** 댓글 삭제 **/
    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/community/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId) {
        commentService.deleteComments(commentId);
        return ResponseEntity.ok().body(null);
    }
}
