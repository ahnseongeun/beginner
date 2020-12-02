package com.inflearnbeginner.beginner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("data","test User 입니다.");
        return "hello";
    }

    @GetMapping("/hello-mvc")
    public String helloMvc(
            @RequestParam(value = "name",required = false) String name,
            Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    //거의 사용안하는 방식
    @GetMapping("/hello-body")
    @ResponseBody
    public String helloBody(
            @RequestParam(value = "name",required = false) String name){
        return "hello"+name;
    }

    //실제 사용하는 방식 및 사용하는 이유
    //Java 객체가 -> Json으로 형태가 나간다.
    @GetMapping("/hello-api")
    @ResponseBody //viewResolver를 사용하는 것이 아니라 HttpMessageConverter가 동작한다.
    public Hello helloApi(
            @RequestParam(value = "name") String name){
        Hello hello=new Hello();
        hello.setName(name);
        return hello;
    }



    static class Hello{
        private String name;
        private String test; //get/set 자동 생성 alt+insert

        public String getName(){
            return name;
        }

        public void setName(String name){
            this.name=name;
        }
    }
}
