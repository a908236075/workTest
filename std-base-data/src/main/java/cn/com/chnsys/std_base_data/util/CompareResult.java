package cn.com.chnsys.std_base_data.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>比较结果</p>
 *
 * 
 *
 * @author zoukang
 * @version 1.0
 * 
 * 2016-8-29 下午8:05:24
 */
public class CompareResult<E> {

    /**
     * 被修改的对象集合
     */
    private List<E> updates;
    
    /**
     * 新增的集合
     */
    private List<E> news;
    /**
     * 被删除集合
     */
    private List<E> deletes;
    /**
     * 默认构造函数
     */
    public CompareResult(){
        updates = new ArrayList<E>();
        news = new ArrayList<E>();
        deletes = new ArrayList<E>();
    }
    
    public List<E> getDeletes() {
		return deletes;
	}

	public void setDeletes(List<E> deletes) {
		this.deletes = deletes;
	}

	/**
     * 添加删除的对象
     * @param e
     */
    public void addDelete(E e){
        deletes.add(e);
    }
    
    /**
     * 添加修改的对象
     * @param e
     */
    public void addUpdate(E e){
    	updates.add(e);
    }
    
    /**
     * 添加新增的对象
     * @param e
     */
    public void addNew(E e){
        news.add(e);
    }
    
    /**
     * news getter
     * @return the news value
     */
    public List<E> getNews() {
        return news;
    }

    /**
     * updates setter
     * @param updates a new value 
     */
    public void setUpdates(List<E> updates) {
        this.updates = updates;
    }

    /**
     * news setter
     * @param news a new value 
     */
    public void setNews(List<E> newsList) {
    	news = newsList;
    }
    /**
     * updates getter
     * @return the updates value
     */
    public List<E> getUpdates() {
        return updates;
    }
}
