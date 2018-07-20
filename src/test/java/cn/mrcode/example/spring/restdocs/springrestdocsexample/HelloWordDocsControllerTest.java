package cn.mrcode.example.spring.restdocs.springrestdocsexample;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/7/20 9:13
 * @date 2018/7/20 9:13
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWordDocsControllerTest {
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                // 这一段配置是 https://docs.spring.io/spring-restdocs/docs/current/reference/html5/ 官网中的配置
                // 不需要生成文档的话 不用配置该项
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void fun1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fun1")) // 请求
                .andExpect(MockMvcResultMatchers.status().isOk()) // 断言HTTP状态为200，否则异常
                .andDo(MockMvcRestDocumentation  // 增加文档；原理就是收集一些请求响应数据按照asciidoctor语法生成“.adoc”文件；
                               .document("fun1"));   // 这个api就是专为生成asciidoctor的配置api；更详细的配置可以参考他的官网
    }

    @Test
    public void fun2() {
    }

    @Test
    public void fun3() {
    }
}