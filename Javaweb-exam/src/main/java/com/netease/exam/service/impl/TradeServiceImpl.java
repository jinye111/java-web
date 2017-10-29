package com.netease.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.exam.dao.GoodsDao;
import com.netease.exam.dao.TradeDao;
import com.netease.exam.meta.Trade;
import com.netease.exam.service.TradeService;

@Service
@Transactional
public class TradeServiceImpl implements TradeService{

	@Autowired
	TradeDao tradedao;
	
	@Override
	public Integer getTrx(int con) {
		return tradedao.getTrx(con);
	}

	@Override
	public List<Trade> getTrxByCon(int con) {
		// TODO Auto-generated method stub
		return tradedao.getTrxByCon(con);
	}

	@Override
	public Integer isBuy(int con, int userId) {
		// TODO Auto-generated method stub
		return tradedao.isBuy(con,userId);
	}

	@Override
	public void setTrx(int contentId, int personId, int price, long time) {
		// TODO Auto-generated method stub
		tradedao.setTrx(contentId,personId,price,time);
	}

}
