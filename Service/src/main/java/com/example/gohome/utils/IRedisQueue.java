package com.example.gohome.utils;

import redis.clients.jedis.Transaction;

/** 
* 队列接口
* @author chencaihui 
* @datetime 创建时间：2017年3月22日 下午8:42:20 
*/
public interface IRedisQueue {

	/**获取队列名 * 
	 * @return 
	 * */
    String getName();

    /**往队列中添加任务 
     * @param task 
     * */
    void push(String task);
    
    /**事务中往队列中添加任务 
     * @param task 
     * */
    void push(Transaction transaction, String task) throws Exception;

    /**从队列中取出一个任务 ,如果没有任务自动阻塞
     * @return 
     * */
    String take();
    
    /**从队列右侧取出一个任务 
     * @return 
     * */
    String rtake();
}
