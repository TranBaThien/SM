/*
 * @(#)IBaseLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/11/22
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

/**
 * @author PVHung3
 *
 */
public interface IBaseLogic<T> {
	
	public boolean save(T obj) throws Exception;
	
	public boolean delete(T obj);

}
