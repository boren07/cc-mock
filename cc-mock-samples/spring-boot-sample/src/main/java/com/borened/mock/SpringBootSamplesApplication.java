package com.borened.mock;

import com.borened.mock.model.Foo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class SpringBootSamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSamplesApplication.class, args);
    }

    /**
     * 测试返回空结果
     */
    @GetMapping("/test1")
    public MyResult test1(){
        return new MyResult<>();
    }

    /**
     * 测试返回泛型参数结果
     */
    @GetMapping("/test2")
    public MyResult<Foo> test2(){
        //这里可以是自定义逻辑,可以从数据库或其他地方读取
        Foo data = new Foo();
        return new MyResult<>(data);
    }

    /**
     * 测试返回嵌套泛型参数结果
     */
    @GetMapping("/test3")
    public MyResult<Foo<Foo.Attr<String>,String,List<Foo.Attr<String>>>> test3(){
        return new MyResult<>();
    }

    @GetMapping("/test4")
    public MyResult<List<Foo.Attr>> test4(){
        return new MyResult<>();
    }

    @GetMapping("/test5")
    public MyResult<List<Foo.Attr>> test5(){
        return new MyResult<>();
    }
}
