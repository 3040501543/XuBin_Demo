package com.mybatisPlusUtil.demo.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ResultCommon
 * @Author: LinF
 * @Date: 2021/4/23 11:19
 * @Description:
 */
@Getter
@Setter
@ApiModel(description = "查询结果基本继承对象")
public class ResultCommon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    public Map<String, Object> map = new HashMap<>();

    public void putMap(String s, Object o) {
        map.put(s, o);
    }
}
