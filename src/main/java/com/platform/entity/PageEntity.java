package com.platform.entity;

import com.platform.entity.dto.ProjectDTO;
import lombok.Data;

@Data
public class PageEntity<T> {
    private int page;//当前页面
    private int pageSize;//每页显示的数据量
    private T entity; //返回的实体对象
    private int total;//返回查询记录总数


    public PageEntity(int page, int pageSize, T entity, int total) {
        this.page = page;
        this.pageSize = pageSize;
        this.entity = entity;
        this.total = total;
    }

    /**
     * 计算总页数
     */
    public int getTotalPage(){
        /**
         * totalPage:表示总共有几页！
         *
         *   　　　　总页数          totalRecord[总记录数]    pageSize
         *                5       　　　　   10                     2
         *                5         　　　　  9                     2
         *                4         　　　　  8                     2
         */
        if(total % pageSize == 0){
            return  total/pageSize;
        }
        return (total/pageSize)+1;
    }

    /**
     * 计算当前索引值
     */
    public int getIndex(){
        /**
         * index表示的是当前索引值，是计算得到的！
         *             当前索引值       每页显示几条数据       当前页是第几页
         *                0                 3                 1
         *                3                 3                 2
         *
         index  = (pageNumber -1)*pageSize;
         */
        return (getPage()-1)*pageSize;
    }
}
