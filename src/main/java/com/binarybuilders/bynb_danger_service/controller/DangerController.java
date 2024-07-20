//package com.binarybuilders.bynb_danger_service.controller;
//
//import com.binarybuilders.bynb_danger_service.service.UserRequestService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class DangerController {
//
//    private final UserRequestService userRequestService;
//
//    public DangerController(UserRequestService userRequestService) {
//        this.userRequestService = userRequestService;
//    }
//
//    @GetMapping("/danger/{dangerId}/user")
//    public String getUser(@PathVariable String dangerId) {
//        return userRequestService.getUserId(dangerId);
//    }
//}
