package mat.mat_t.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mat.mat_t.domain.review.InstructorReview;
import mat.mat_t.domain.review.ReviewLike;
import mat.mat_t.domain.user.User;
import mat.mat_t.web.service.InstructorReviewService;
import mat.mat_t.web.service.ReviewLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Controller
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;
    private final InstructorReviewService instructorReviewService;

    @ApiOperation(value = "좋아요 누르기")
    @PostMapping("review/Like/{reviewId}")
    public ResponseEntity pressLikeReview(@PathVariable Long reviewId
            , HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute("loginUser");

        InstructorReview instructorReview=instructorReviewService.findByInsReviewId(reviewId);
        ReviewLike reviewLike = new ReviewLike(instructorReview,loginUser);

        if(reviewLikeService.existsByUserIdAndInstructorReviewId(loginUser.getId(), reviewId)){
            throw new IllegalStateException("이미 좋아요를 누르셨습니다.");
        }

        reviewLikeService.saveLike(reviewLike);
        instructorReviewService.pressLikes(instructorReview);

        return null;
    }

    @ApiOperation(value="좋아요 취소하기")
    @DeleteMapping("review/Like/{reviewId}")
    public ResponseEntity cancelLikeReview(@PathVariable Long reviewId, HttpServletRequest request){

        InstructorReview instructorReview=instructorReviewService.findByInsReviewId(reviewId);
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute("loginUser");

        instructorReviewService.cancelLikes(instructorReview);
        reviewLikeService.deleteLike(reviewId, loginUser.getId());
        return null;
    }
}
