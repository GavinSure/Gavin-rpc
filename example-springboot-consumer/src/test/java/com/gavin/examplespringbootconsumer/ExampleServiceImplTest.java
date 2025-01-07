package com.gavin.examplespringbootconsumer;



import com.gavin.examplespringbootconsumer.service.impl.ExampleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class ExampleServiceImplTest {

    @Resource
    private ExampleServiceImpl exampleService;

    @Test
    void test1() {
        exampleService.test();
    }
}
