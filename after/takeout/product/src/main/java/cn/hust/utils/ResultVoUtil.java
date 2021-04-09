package cn.hust.utils;

import cn.hust.vo.ResultVo;

public class ResultVoUtil {
    public static ResultVo success(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo fail(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(-1);
        resultVo.setMsg("失败");
        resultVo.setData(null);
        return resultVo;
    }

}
