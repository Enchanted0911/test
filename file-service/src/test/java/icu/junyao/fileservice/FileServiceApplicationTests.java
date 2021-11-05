package icu.junyao.fileservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void location() {
        System.out.println(System.getProperty("user.dir"));
    }
}
