package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.oneandtwosubject.OneSubject;
import com.atguigu.eduservice.entity.oneandtwosubject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-11
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {

        try {
            /**
             * 1. 文件输入流
             */
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();

        }catch (Exception e){
            System.out.println(e);
        }finally {

        }

    }


    /**
     * 获取一级目录，包含二级目录
     * @return
     */
    @Override
    public List<OneSubject> getOneTwoAllSubject() {
        /**
         *  1. 查询出所有一级目录，封装到 onesubject
         */
        QueryWrapper<EduSubject> onequeryWrapper = new QueryWrapper<>();
        onequeryWrapper.eq("parent_id", "0");
        List<EduSubject> subjectList1 = baseMapper.selectList(onequeryWrapper);

        /**
         *  2. 查询所有二级目录，封装到 twosubject
         */
        QueryWrapper<EduSubject> twoqueryWrapper = new QueryWrapper<>();
        twoqueryWrapper.ne("parent_id", "0");   // parent_id 不等于 0
        List<EduSubject> subjectList2 = baseMapper.selectList(twoqueryWrapper);

        /**
         *  3. 创建存储一级目录 二级目录的集合
         */
        List<OneSubject> oneSubjectList = new ArrayList();

        /**
         *  4. 遍历 一级，将 一级的edusubject 封装到 onesubject
         */
        for (EduSubject eduSubject : subjectList1) {
            OneSubject oneSubject = new OneSubject();
            // 通过 工具类 beanutils 的 copy 方法，将对象1的属性copy到对象2中，需要属性名一致
            BeanUtils.copyProperties(eduSubject, oneSubject);
            List<TwoSubject> twoSubjectList = new ArrayList();
            /**
             *  5. 遍历二级，将 二级的edusubject 封装到 twosubject
             */
            for (EduSubject twoeduSubject : subjectList2) {
                // 如果 二级目录的 pid 等于 一级目录的 id，说明属于当前一级目录，进行封装
                if (oneSubject.getId().equals(twoeduSubject.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoeduSubject, twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            /**
             *  6. 将 当前一级目录下的所有二级目录封装到一级目录中
             */
            oneSubject.setChildren(twoSubjectList);
            oneSubjectList.add(oneSubject);
        }
        /**
         *  7. 返回 onesubjectlist 集合
         */
        return oneSubjectList;
    }


}
