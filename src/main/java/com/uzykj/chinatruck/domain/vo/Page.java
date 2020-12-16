package com.uzykj.chinatruck.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ghostxbh
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    // 页码
    private Integer page_no;
    // 条数
    private Integer page_size;
    // 总条数
    private Long total;
    // 总页数
    private Integer pages;
    // 数据集
    private List<T> records;

    public Page(Integer page_num, Integer page_size) {
        this.page_no = page_num;
        this.page_size = page_size;
    }

    public Page<T> get(HttpServletRequest request, HttpServletResponse response) {
        Integer page_no = (Integer) request.getAttribute("page_no");
        Integer page_size = (Integer) request.getAttribute("page_size");
        return new Page<T>(page_no, page_size);
    }

    public void formatTotal(Long total, List<T> records) {
        this.total = total;
        this.records = records;
        this.pages = Math.toIntExact((total % this.page_size > 0) ? total / this.page_size + 1 : total / this.page_size);
    }
}
