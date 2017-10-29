package com.netease.exam.service;

import java.util.List;

import com.netease.exam.meta.Goods;
import com.netease.exam.meta.Trade;

public interface TradeService {
	public Integer getTrx(int con);
	public List<Trade> getTrxByCon(int con);
	public Integer isBuy(int con,int userId);
	void setTrx(int contentId,int personId,int price,long time);
}
