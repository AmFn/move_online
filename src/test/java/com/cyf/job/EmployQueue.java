package com.cyf.job;

import com.cyf.modules.app.entity.EmployeeEntity;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class EmployQueue {
	
	private static EmployQueue instance = null;
	private static BlockingQueue<EmployeeEntity> queue = null;
	
	private EmployQueue(){
		queue =  new LinkedBlockingDeque<EmployeeEntity>();
	}
	
	public static EmployQueue getInstance(){
		if(instance == null){
			instance = new EmployQueue();
		}
		return instance;
	}
 
	/**
	 * 取下一个员工
	 * @return
	 */
	public EmployeeEntity takeNext(){
		EmployeeEntity employEntity = null;
		try {
			//取到繁忙员工
			employEntity = queue.take();

			this.put(employEntity);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return employEntity;
	}
	
	/**
	 * 将新员工放入队列中未尾
	 */
	public void put(EmployeeEntity EmployEntity){
		if(queue.contains(EmployEntity)){
			return;
		}else{
			try {
				queue.put(EmployEntity);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void putAll(Collection<EmployeeEntity> EmployEntitys){
		for(EmployeeEntity EmployEntity : EmployEntitys){
			this.put(EmployEntity);
		}
	}
	
	/**
	 * 将已不存在的员工移除
	 */
	public void remove(EmployeeEntity EmployEntity){
		if(queue.contains(EmployEntity)){
			queue.remove(EmployEntity);
		}
	}
	
	/**
	 * 获取目前队列中所有的EmployEntity
	 * @return
	 */
	public Object[] getAllEmployEntitys(){
		Object[] obj = queue.toArray();
		return obj;
	}


	public  int getSize(){
		return queue.size();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EmployeeEntity EmployEntity1 = new EmployeeEntity();
		EmployEntity1.setId(1L);
		EmployEntity1.setPhone("11");

		EmployeeEntity EmployEntity2 = new EmployeeEntity();
		EmployEntity2.setId(1L);
		EmployEntity2.setPhone("11");

		EmployeeEntity EmployEntity3 = new EmployeeEntity();
		EmployEntity3.setId(3L);
		EmployEntity3.setPhone("11");
		
		EmployeeEntity EmployEntity4 = new EmployeeEntity();
		EmployEntity4.setId(4L);
		EmployEntity4.setPhone("444");
		
		EmployeeEntity EmployEntity5 = new EmployeeEntity();
		EmployEntity5.setId(5L);
		EmployEntity5.setPhone("555");

		EmployQueue.getInstance().put(EmployEntity1);
		EmployQueue.getInstance().put(EmployEntity2);
		EmployQueue.getInstance().put(EmployEntity3);
		EmployQueue.getInstance().put(EmployEntity4);
		EmployQueue.getInstance().put(EmployEntity5);
		
		for(int i = 0 ; i < 20; i++){
			System.out.println(EmployQueue.getInstance().takeNext().getId());
			if(i == 5){
				EmployQueue.getInstance().remove(EmployEntity2);
			}
		}
		
		Object[] EmployEntitys = EmployQueue.getInstance().getAllEmployEntitys();
		System.out.println("=======");
		for(Object object:EmployEntitys){
			System.out.println(((EmployeeEntity)object).getId());
		}
	}
 
}