package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.dao.QueryTimeoutException;

/**
 * 操作 excel 的监听器
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    public SubjectExcelListener() {
    }
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 读取表格的方法，一行一行的读，不读表头
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        // 判断表数据是否有内容
        if (subjectData == null){
            System.out.println("表格中没有内容");
            return;
        }
        // 一行一行读，每次读取两个值，第一个值是一级分类，第二个值是二级分类
        // 添加一级目录
        EduSubject oneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (oneSubject == null){ //说明没有相同的一级目录，那就进行添加
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(oneSubject);
        }
        // 添加二级目录
        // 获取二级目录的一级分类id值
        String pid = oneSubject.getId();
        EduSubject twoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (twoSubject == null){ //说明没有相同的二级目录，那就进行添加
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(twoSubject);
        }
    }

    // 判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService, String title){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", 0);
        EduSubject eduSubject = subjectService.getOne(queryWrapper);
        return eduSubject;
    }

    // 判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService, String title, String parentId){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        EduSubject eduSubject = subjectService.getOne(queryWrapper);
        return eduSubject;
    }

    /**
     * 读取完成之后执行的方法
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
