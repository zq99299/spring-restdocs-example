package cn.mrcode.example.spring.restdocs.springrestdocsexample;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/7/20 9:10
 * @date 2018/7/20 9:10
 * @since 1.0
 */
public class Book {
    private String name;
    private double price;
    private String[] authors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }
}
