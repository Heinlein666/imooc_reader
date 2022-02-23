package com.imooc.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Evaluation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface EvaluationMapper extends BaseMapper<Evaluation> {
    void changeState(@Param("evaluationId") Long evaluationId, @Param("state") String state, @Param("disableReason") String disableReason, @Param("disableTime") Date disableTime);
}
