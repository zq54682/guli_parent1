package com.atguigu.eduservice.entity.chapternadvideo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 表现 大章节 和 小章节 二级联动的 实例
 */
@Data
public class OneChapter {
    // 章节 id
    private String id;

    // 章节名
    private String title;

    // 章节中包含小节集合
    private List<TwoVideo> children = new ArrayList<>();


}
