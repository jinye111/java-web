package com.netease.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.exam.dao.GoodsDao;
import com.netease.exam.meta.Goods;
import com.netease.exam.service.GoodsService;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	GoodsDao goodsdao;

	@Override
	public List<Goods> getGoods() {
		// TODO Auto-generated method stub
		return goodsdao.getGoods();
	}

	@Override
	public void setGoods(byte[] image) {
		// TODO Auto-generated method stub
		goodsdao.setGoods(image);
	}

	@Override
	public void setgoods(int price,String title,String image,String summary,String detail) {
		// TODO Auto-generated method stub
		goodsdao.setgood(price, title, image, summary,detail);
	}
	
	public int getLastId() {
		return goodsdao.getLastId();
	}

	@Override
	public Goods getGoodsById(int id) {
		// TODO Auto-generated method stub
		return goodsdao.getGoodsById(id);
	}

	@Override
	public void updategood(int id, int price, String title, String image, String summary, String detail) {
		// TODO Auto-generated method stub
		goodsdao.updategood(id, price, title, image, summary, detail);
	}

	@Override
	public void deletegood(int id) {
		// TODO Auto-generated method stub
		goodsdao.deletegood(id);
	}
	

}
