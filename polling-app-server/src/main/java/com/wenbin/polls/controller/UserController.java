package com.wenbin.polls.controller;
import com.wenbin.polls.exception.ResourceNotFoundException;
import com.wenbin.polls.model.User;
import com.wenbin.polls.payload.*;
import com.wenbin.polls.repository.PollRepository;
import com.wenbin.polls.repository.UserRepository;
import com.wenbin.polls.repository.VoteRepository;
import com.wenbin.polls.security.UserPrincipal;
import com.wenbin.polls.service.PollService;
import com.wenbin.polls.security.CurrentUser;
import com.wenbin.polls.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
/**
 * @author Wenbin Luo @ Aalto University
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/polls")
    public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(username, currentUser, page, size);
    }


    @GetMapping("/users/{username}/votes")
    public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(username, currentUser, page, size);
    }

}
