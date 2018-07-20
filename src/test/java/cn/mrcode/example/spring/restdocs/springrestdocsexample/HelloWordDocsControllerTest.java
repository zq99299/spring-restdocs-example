package cn.mrcode.example.spring.restdocs.springrestdocsexample;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

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
                .apply(documentationConfiguration(this.restDocumentation)
                               // 预处理器，这里设置的是对请求参数和响应参数进行美化输出
                               // https://docs.spring.io/spring-restdocs/docs/2.0.2.RELEASE/reference/html5/#customizing-requests-and-responses-preprocessors-pretty-print
                               // 预处理器的使用 在上面地址文档往上一点点
                               .operationPreprocessors()  // 预处理器
                               .withRequestDefaults(prettyPrint())
                               .withResponseDefaults(prettyPrint())  // 格式化请求或响应参数，比如json串
                )
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

    /**
     * 增加请求参数 和 响应自定义pojo对象
     * @throws Exception
     */
    @Test
    public void fun3() throws Exception {
//        Book book = new Book();
//        book.setName("《spring resdocs进阶篇》");
//        book.setPrice(13.2);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/fun3")
                        // post 参数请求文档地址
                        // https://docs.spring.io/spring-restdocs/docs/2.0.2.RELEASE/reference/html5/#documenting-your-api-request-parameters
                        // 表单提交是不能提交一个对象的，只能提交kv的形式
                        .param("name", "《spring resdocs进阶篇》")
                        .param("price", "13.2")
                // 请求类型也就是 平时开发的 form表单提交
                // jquery ajax 提交的form表单
                // contentType 就是这个
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())  // 打印请求响应详细日志，可以在控制台看到详细的日志信息
                .andDo(MockMvcRestDocumentation
                               .document("fun3"));
    }

    /**
     * 增加请求参数 和 响应自定义pojo对象; 对post参数增加请求响应字段的描述
     * 对应的官网文档地址
     * https://docs.spring.io/spring-restdocs/docs/2.0.2.RELEASE/reference/html5/#documenting-your-api-request-parameters
     * @throws Exception
     */
    @Test
    public void fun3_1() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/fun3")
                        .param("name", "《spring resdocs进阶篇》")
                        .param("price", "13.2")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation
                               .document("fun3_1",
                                         // post 的 APPLICATION_FORM_URLENCODED 和 get url中的参数 都可以用该方法获取
                                         RequestDocumentation.requestParameters(
                                                 RequestDocumentation.parameterWithName("name").description("书籍名称"),
                                                 RequestDocumentation.parameterWithName("price").description("书籍价格")),
                                         PayloadDocumentation.responseFields(
                                                 PayloadDocumentation.fieldWithPath("name").description("书籍名称"),
                                                 PayloadDocumentation.fieldWithPath("price").description("书籍价格"),
                                                 // 这个api对于响应字段描述不完全的话会报错的
                                                 // 可以尝试注释掉 authors的描述看看效果
                                                 PayloadDocumentation.fieldWithPath("authors").description("书籍价格")
                                         )
                               )
                );
    }

    /**
     * 增加请求参数 和 响应自定义pojo对象; 对post参数增加请求响应字段的描述
     * 对应的官网文档地址
     * https://docs.spring.io/spring-restdocs/docs/2.0.2.RELEASE/reference/html5/#documenting-your-api-request-response-payloads
     * @throws Exception
     */
    @Test
    public void fun4() throws Exception {
        Book book = new Book();
        book.setName("《mrcode spring resdocs进阶篇》");
        book.setPrice(13.2);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/fun4")
                        .contentType(MediaType.APPLICATION_JSON)
                        // 增加 依赖  compile 'com.alibaba:fastjson:1.2.47'
                        // 对对象进行序列化成json串
                        .content(JSON.toJSONString(book))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation
                               .document("fun4",
                                         // 这里对于fun3_1中的地方来说
                                         // 只是修改成了 与 响应一样的字段api
                                         PayloadDocumentation.requestFields(
                                                 // 如果有嵌套对象的话。在文档中也说得很清楚了
                                                 // 使用 aa.b 来指定
                                                 PayloadDocumentation.fieldWithPath("name").description("书籍名称"),
                                                 PayloadDocumentation.fieldWithPath("price").description("书籍价格")),
                                         PayloadDocumentation.responseFields(
                                                 PayloadDocumentation.fieldWithPath("name").description("书籍名称"),
                                                 PayloadDocumentation.fieldWithPath("price").description("书籍价格"),
                                                 PayloadDocumentation.fieldWithPath("authors").description("书籍价格")
                                         )
                               )
                );
    }
}