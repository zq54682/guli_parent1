package com.atguigu.eduservice.entity.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * excel 表格类，此表格就两列属性
 * 用于添加分类目录
 */
@Data
public class SubjectData {

    /**
     * 一级分类名称
     */
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    /**
     * 二级分类名称
     */
    @ExcelProperty(index = 1)
    private String twoSubjectName;



}
