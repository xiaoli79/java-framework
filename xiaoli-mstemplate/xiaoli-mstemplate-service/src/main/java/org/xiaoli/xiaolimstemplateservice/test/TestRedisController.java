package org.xiaoli.xiaolimstemplateservice.test;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoli.xiaolicommondomain.domain.R;
import org.xiaoli.xiaolicommonredis.RedisService;

@RestController
@Slf4j
@RequestMapping("/test/redis")
public class TestRedisController {



    @Autowired
    private RedisService redisService;


    @PostMapping("/add")
    public R<Void> add(){
        redisService.setCacheObject("test","abc");
        return R.ok();
    }





}
