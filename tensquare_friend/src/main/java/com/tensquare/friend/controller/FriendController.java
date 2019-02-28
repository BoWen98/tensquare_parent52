package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,
                            @PathVariable String type) {
        Claims claims = (Claims) httpServletRequest.getAttribute("claims_user");

        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        //获取添加ID
        String userid = claims.getId();
        //判断是否添加好友
        if (type != null) {
            if (type.equals("1")) {
                //添加好友
                int flag = friendService.addFriend(userid, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "重复添加");
                }
                if (flag == 1) {
                    userClient.updatefanscountandfollowcount(userid, friendid, 1);
                    return new Result(false, StatusCode.ERROR, "添加成功");
                }
            } else if (type.equals("2")) {
                int flag = friendService.addNoFriend(userid, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "重复添加");
                }
                if (flag == 1) {
                    return new Result(false, StatusCode.ERROR, "添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
    }
}
