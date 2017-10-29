package com.netease.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.exam.meta.Goods;
import com.netease.exam.meta.Trade;

@Service
@Transactional
public interface TradeDao {
	
	@Select("select count(id) from trx where contentId = #{0}")
	public Integer getTrx(int con);
	
	@Select("select count(id) from trx where contentId = #{0} and personId=#{1}")
	public Integer isBuy(int con,int userId);
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "contentId", column = "contentId"),
		@Result(property = "personId", column = "personId"),
		@Result(property = "price", column = "price"),
		@Result(property = "time", column = "time")}
	)
	@Select("select * from trx where contentId = #{0}")
	public List<Trade> getTrxByCon(int con);
	
	@Insert("insert into trx (contentId,personId,price,time) values(#{0},#{1},#{2},#{3})")
	public void setTrx(int contentId,int personId,int price,long time);
	
}
