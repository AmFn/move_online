package com.cyf.job;

import com.cyf.modules.app.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
 
@Slf4j
public class TaskQueue {
	
	private static TaskQueue instance;
	
	private static BlockingQueue<OrderEntity> queue = null;
	
	private TaskQueue(){
		queue = new LinkedBlockingDeque<OrderEntity>();
	}
	
	public static TaskQueue getInstance(){
		if(instance == null){
			instance = new TaskQueue();
		}
		return instance;
	}
	
	/**
	 * 获取队列大小
	 * @return
	 */
	public static int getSize(){
		return queue.size();
	}
	
	/**
	 * 放入队列
	 */
	public void put(OrderEntity obj){
		if(obj != null){
			try {
				queue.put(obj);
				log.info("put invoke , queue size : {}",queue.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 放入队列中
	 */
	public void putAll(Collection<OrderEntity> objs){
		if(objs != null){
			queue.addAll(objs);
			log.info("putAll invoke  , queue size : {}",queue.size());
		}
	}
	
	/**
	 * 取一个
	 * @return
	 */
	public OrderEntity take(){
		try {
			OrderEntity obj = queue.take();
			log.info("take invoke  , queue size : {}",queue.size());
			return obj;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
 
}