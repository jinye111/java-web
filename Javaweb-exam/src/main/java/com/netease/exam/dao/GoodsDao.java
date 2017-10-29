package com.netease.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.exam.meta.Goods;
import com.netease.exam.meta.Trade;
import com.netease.exam.meta.User;

@Service
@Transactional
public interface GoodsDao {

	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "price", column = "price"),
		@Result(property = "title", column = "title"),
		@Result(property = "icon", column = "icon"),
		@Result(property = "abs", column = "abstract"),
		@Result(property = "text", column = "text")}
	)
	@Select("select * from content")
	public List<Goods> getGoods();
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "price", column = "price"),
		@Result(property = "title", column = "title"),
		@Result(property = "icon", column = "icon"),
		@Result(property = "abs", column = "abstract"),
		@Result(property = "text", column = "text")}
	)
	@Select("select * from content where id = #{0}")
	public Goods getGoodsById(int id);
	
	@Insert("insert into content (icon) values(#{image,jdbcType=BLOB})")
	public void setGoods(@Param("image")byte[] image);
	
	@Insert("insert into content (price,title,icon,abstract,text) values(#{0},#{1},#{2},#{3},#{4})")
	public void setgood(int price,String title,String image,String summary,String detail);
	
	@Select("select last_insert_id()")
	public int getLastId(); 
	
	@Update("update content set price=#{1},title=#{2},icon=#{3},abstract=#{4},text=#{5} where id=#{0}")
	public void updategood(int id,int price,String title,String image,String summary,String detail);
	
	@Delete("delete from content where id = #{0}")
	public void deletegood(int id);
}
