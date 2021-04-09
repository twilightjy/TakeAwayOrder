package cn.hust.vo;

import lombok.Data;

/**
 * <p>
 *  View Of Object
 *  根据前端data的格式，转化封装为Java对象
 * </p>
 *
 * @author zz
 * @since 2021-04-08
 */

@Data
public class ResultVo<T> {

    /*状态码*/
    private Integer code ;

    /*状态信息*/
    private String msg ;

    /*data的具体内容不完全一样，所以用泛型T代替 */
    private T data;

}


/*假数据格式:
 * {
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "name": "早餐",
            "type": 1,
            "foods": [
                {
                    "id": "2",
                    "name": "豆浆",
                    "price": 3.5,
                    "description": "很好喝",
                    "icon": "http://5b0988e595225.cdn.sohucs.com/images/20181101/225d20df2bc14cefb363686d90d47004.jpeg",
                    "quantity": 0,
                    "stock": 200
                }
            ]
        },
        {
            "name": "午餐",
            "type": 2,
            "foods": [
                {
                    "id": "1",
                    "name": "红烧肉",
                    "price": 33.50,
                    "description": "很好吃",
                    "icon": "http://n.sinaimg.cn/sinacn10/320/w640h480/20180827/5682-hifuvph4790369.jpg",
                    "quantity": 0,
                    "stock": 200
                }
            ]
        }
    ]
}
 */
