package com.example.FirstProject.Controller;

import com.example.FirstProject.Repository.RoleRepo;
import com.example.FirstProject.Repository.UserRepository;
import com.example.FirstProject.model.AppRoles;
import com.example.FirstProject.model.Role;
import com.example.FirstProject.model.User;
import com.example.FirstProject.security.jwt.JwtUnits;
import com.example.FirstProject.security.request.LoginRequst;
import com.example.FirstProject.security.request.SignUpRequest;
import com.example.FirstProject.security.response.MessageResponse;
import com.example.FirstProject.security.response.UserInfoResponse;
import com.example.FirstProject.security.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

@Autowired
UserRepository userRepository;
@Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUnits jwtUnits;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepo roleRepo;
    @Tag(name="Authentication API",description = "APIS for managing Authentication")

    @PostMapping("/signin")
    public ResponseEntity<?> authenticatUser(@RequestBody LoginRequst loginRequst) {
        Authentication authentication ;
        try {

            authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequst.getUserName(),loginRequst.getPassword()
                    )
            );
        }catch (AuthenticationException e){
            Map<String,Object > map = new HashMap<>();
            map.put("message","Authentication Failed");
            map.put("status",false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUnits.generateJwtCookie(userDetails );
        List<String> roles=userDetails.getAuthorities().stream().map(
                item->item.getAuthority()
        ).collect(Collectors.toList());
        UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
               roles
        );
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                        jwtCookie.toString())
                .body(userInfoResponse);
    }
    @Tag(name="Authentication API",description = "APIS for managing Authentication")

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @Valid @RequestBody SignUpRequest  signUpRequest) {
if (userRepository.existsByUserName(signUpRequest.getUsername())){
    return  ResponseEntity.badRequest().
            body(new MessageResponse("Error: UserName is already exist!!!!!!"));

}
        if (userRepository.existsByUserEmail(signUpRequest.getEmail())){
            return  ResponseEntity.badRequest().
                    body(new MessageResponse("Error: Email is already exist!!!!!!"));

        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),passwordEncoder.encode(signUpRequest.getPassword()));
       Set<String> strRole=signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRole==null){
            Role role =  roleRepo.findByRoleName(AppRoles.ROLE_USER).orElseThrow(()
            ->new RuntimeException("Error: Role is not found"));
            roles.add(role);
        }else {
            strRole.forEach(role->{
                switch (role){
                    case "admin":
                        Role adminRole=roleRepo.findByRoleName(AppRoles.ROLE_ADMIN).orElseThrow(()
                            ->new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);
                        break;
                        case "seller":
                            Role sellerRole=roleRepo.findByRoleName(AppRoles.ROLE_SELLER).orElseThrow(()
                                    ->new RuntimeException("Error: Role is not found"));
                            roles.add(sellerRole);

                            break;
                            default:
                                Role userRole=roleRepo.findByRoleName(AppRoles.ROLE_USER).orElseThrow(()
                                        ->new RuntimeException("Error: Role is not found"));
                                roles.add(userRole);
                }
            });

        }
        user.setRoles(roles);
        userRepository.save(user);
        return  ResponseEntity.ok(new MessageResponse("User registered successfully!!!!!!"));
    }
    @Tag(name="Authentication API",description = "APIS for managing Authentication")

    @GetMapping("/username")
public  String currentUser(Authentication authentication){
        if (authentication!=null){
            return authentication.getName();
        }else return "";
}

    @Tag(name="Authentication API",description = "APIS for managing Authentication")

    @GetMapping("/userdetails")
    public ResponseEntity<?> currentUserDetails(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles=userDetails.getAuthorities().stream().map(
                item->item.getAuthority()
        ).collect(Collectors.toList());
        UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
                roles
        );
        return ResponseEntity.ok()
                .body(userInfoResponse);
    }
    @Tag(name="Authentication API",description = "APIS for managing Authentication")

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser(   ){
        ResponseCookie jwtCookie = jwtUnits.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                        jwtCookie.toString())
                .body(new MessageResponse("Successfully logged out!!!!!!"));

    }
}
