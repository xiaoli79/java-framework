package org.xiaoli.xiaolimstemplateservice.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoli.xiaolicommondomain.domain.R;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @GetMapping("/info")
    public  void info(){
        log.info("接口调用测试");
    }


    @GetMapping("/result")
    public R<Void> result(int id){

        if(id < 0){
            return R.fail();
        }
        return R.ok();
    }

}
