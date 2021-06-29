    package com.oracle.demosjon.demojson;

    import org.springframework.web.bind.annotation.CrossOrigin;
    import org.springframework.web.bind.annotation.RestController;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestParam;


    @RestController
    @CrossOrigin
    public class DemojsonController {

        @GetMapping("/")
        public String home(){
            return "Home Page";
        }

        @GetMapping("/hello")
        public String hello(@RequestParam String name){
            return "Hello "+name;
        }
 }

