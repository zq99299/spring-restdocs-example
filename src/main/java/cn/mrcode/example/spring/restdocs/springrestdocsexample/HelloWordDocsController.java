package cn.mrcode.example.spring.restdocs.springrestdocsexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * docs入门演示
 * @author zhuqiang
 * @version 1.0.1 2018/7/20 9:07
 * @date 2018/7/20 9:07
 * @since 1.0
 */
@RestController
public class HelloWordDocsController {
    @GetMapping("/fun1")
    public List<Integer> fun1() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
    }

    @GetMapping("/fun2")
    public String fun2() {
        return "hello word";
    }

    /**
     * 接收一个对象，填充部分数据后 再返还该对象
     * @param book
     * @return
     */
    @PostMapping("/fun3")
    public Book fun3(Book book) {
        book.setAuthors(new String[]{"张三丰", "张4丰", "张5丰"});
        return book;
    }

    /**
     * 请求和响应都是json串的示例
     * @param book
     * @return
     */
    @PostMapping("/fun4")
    public Book fun4(@RequestBody Book book) {
        book.setAuthors(new String[]{"mrcode", "张4丰", "张5丰"});
        return book;
    }
}
