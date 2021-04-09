package cn.hust.handler;
/*
 *自动创建create_time 和update_time的handler类
 * @TableField(fill = FieldFill.INSERT)
 *   private LocalDateTime createTime;
 * @TableField(fill = FieldFill.INSERT_UPDATE)
 *   private LocalDateTime updateTime;
 * 这两个地方要配合该handler一起使用
 */

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
