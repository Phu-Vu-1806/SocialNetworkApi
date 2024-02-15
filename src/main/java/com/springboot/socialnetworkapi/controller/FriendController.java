package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.service.impl.FriendServiceImpl;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendServiceImpl friendService;

    @GetMapping("/add")
    public ResponseEntity<?> addFriend(@RequestParam(name = "fromUserId") Long fromUserId, @RequestParam(name = "toUserId") Long toUserId){

        friendService.addFriend(fromUserId, toUserId);
        return ResponseEntity.ok().body(new ResponseMessage("Oke"));
    }

    @GetMapping("/accpect")
    public ResponseEntity<?> accpectFriend(@RequestParam(name = "confirmId") Long confirmId, @RequestParam(name = "toUserId") Long toUserId){

        friendService.acceptFriend(confirmId, toUserId);
        return ResponseEntity.ok().body(new ResponseMessage("Oke"));
    }

    @GetMapping("/cancel-request")
    public ResponseEntity<?> cancelRequest(@RequestParam(name = "fromUserId") Long fromUserId, @RequestParam(name = "toUserId") Long toUserId){

        friendService.cancelRequest(fromUserId, toUserId);
        return ResponseEntity.ok().body(new ResponseMessage("Oke"));
    }

    @GetMapping("/unfriend")
    public ResponseEntity<?> unfriend(@RequestParam(name = "fromUserId") Long fromUserId, @RequestParam(name = "toUserId") Long toUserId){

        friendService.unfriend(fromUserId, toUserId);
        return ResponseEntity.ok().body(new ResponseMessage("Oke"));
    }
}
