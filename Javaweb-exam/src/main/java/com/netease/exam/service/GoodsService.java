package com.netease.exam.service;

import java.util.List;

import com.netease.exam.meta.Goods;

public interface GoodsService {
	List<Goods> getGoods();
	void setGoods(byte[] image);
	void setgoods(int price,String title,String image,String summary,String detail);
	int getLastId();
	Goods getGoodsById(int id);
	void updategood(int id,int price,String title,String image,String summary,String detail);
	void deletegood(int id);
}
