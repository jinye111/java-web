package com.netease.exam.web.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exam.dao.UserDao;
import com.netease.exam.meta.Goods;
import com.netease.exam.meta.Trade;
import com.netease.exam.meta.User;
import com.netease.exam.service.GoodsService;
import com.netease.exam.service.TradeService;
import com.netease.exam.service.UserService;
import com.netease.exam.service.impl.UserServiceImpl;

@Controller
public class Usercontroller {
	 
	private byte[] image = new byte[35000];
	@Autowired
	UserService userService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	TradeService tradeService;
	
	@RequestMapping(value = "/index.do")
	public String index(ModelMap map,HttpServletRequest request,HttpSession session) throws IOException {
		List<Map> good = new ArrayList<Map>();
		Map<String,Object> user = new HashMap<String,Object>();
		List<Goods> goods = new ArrayList<Goods>();
		session = request.getSession();
		goods=goodsService.getGoods();
		if(session.getAttribute("username")!=null) {
			String type;
			user.put("id", session.getAttribute("id"));
			user.put("username", session.getAttribute("username"));
			user.put("password",  session.getAttribute("password"));
			user.put("nickname", session.getAttribute("nickName"));
			user.put("usertype", session.getAttribute("userType"));
			//System.out.println(session.getAttribute("userType"));
			map.addAttribute("user", user);
			if((Integer) session.getAttribute("userType")==0) {
				type="isBuy";
			}
		
			else
				type="isSell";
			//System.out.println(type);
			for(int i=0;i<goods.size();i++) {
				Map<String,Object> GoodInfo = new HashMap<String,Object>();
				GoodInfo.put("id", goods.get(i).getId());
				GoodInfo.put("price", goods.get(i).getPrice());
				GoodInfo.put("title", goods.get(i).getTitle());
				GoodInfo.put("abs", goods.get(i).getAbs());
				GoodInfo.put("image", goods.get(i).getIcon());
				if(tradeService.getTrx(goods.get(i).getId())>0) {
					//System.out.println("true");
					GoodInfo.put(type, true);
				}
			
				else {
					//System.out.println("false");
					GoodInfo.put(type, false);
				}
					
				//System.out.println(tradeService.getTrx(2,3));
				//System.out.println(GoodInfo);
				good.add(GoodInfo);
			}
			//System.out.println(good+"222");
			map.addAttribute("productList", good);
		}
		else {
			for(int i=0;i<goods.size();i++) {
				Map<String,Object> GoodInfo = new HashMap<String,Object>();
				goods=goodsService.getGoods();
				session = request.getSession();
				GoodInfo.put("id", goods.get(i).getId());
				GoodInfo.put("price", goods.get(i).getPrice());
				GoodInfo.put("title", goods.get(i).getTitle());
				GoodInfo.put("abs", goods.get(i).getAbs());
				GoodInfo.put("image", goods.get(i).getIcon());
				GoodInfo.put("text",goods.get(i).getText());
				//System.out.println("456");
				good.add(GoodInfo);
			}
			map.addAttribute("productList", good);
		}
		//String path = request.getServletContext().getRealPath("");
		//FileInputStream in = new FileInputStream(path+"//bb.jpg");
		//in.read(image);
		//goodsService.setGoods(image);
		//System.out.println(good);
		//System.out.println(goods);
		//in.read(buf);
		//goodsService.setGoods(buf);
		//System.out.println(goods.get(0).getAbs());
		//System.out.println(goods.get(1).getPrice());
		return "index";
	}
	
	@RequestMapping(value = "/public.do")
	public String publ(HttpSession session,ModelMap map){
		Map<String,Object> user = new HashMap<String,Object>();
		if(session.getAttribute("username")!=null) {
			user.put("id", session.getAttribute("id"));
			user.put("username", session.getAttribute("username"));
			user.put("password",  session.getAttribute("password"));
			user.put("nickname", session.getAttribute("nickName"));
			user.put("usertype", session.getAttribute("userType"));
			map.addAttribute("user",user);
		}
		return "public";
	}
	
	@RequestMapping(value = "/logout.do")
	public String logout(HttpServletRequest request,HttpSession session) throws IOException {
		session.invalidate();
		return "redirect:/index.do";
	}
	
	@RequestMapping(value = "/login.do")
	public String login(ModelMap map) throws IOException {
		return "login";
	}
	
	@RequestMapping(value = "/show.do")
	public String show(ModelMap map,@RequestParam("id") int i,HttpSession session){
		List<Trade> trade = new ArrayList<Trade>();
		Map<String,Object> GoodInfo=new HashMap<String,Object>();
		Goods goods;
		Map<String,Object> user = new HashMap<String,Object>();
		goods=goodsService.getGoodsById(i);
		GoodInfo.put("id", goods.getId());
		GoodInfo.put("price", goods.getPrice());
		GoodInfo.put("title", goods.getTitle());
		GoodInfo.put("summary", goods.getAbs());
		GoodInfo.put("image", goods.getIcon());
		GoodInfo.put("detail",goods.getText());
		trade=tradeService.getTrxByCon(i);
		if(session.getAttribute("username")!=null) {
			user.put("id", session.getAttribute("id"));
			user.put("username", session.getAttribute("username"));
			user.put("password",  session.getAttribute("password"));
			user.put("nickname", session.getAttribute("nickName"));
			user.put("usertype", session.getAttribute("userType"));
			if((Integer) session.getAttribute("userType")==0){
				if(trade.size()>0) {
					GoodInfo.put("buyNum",trade.size());
					GoodInfo.put("buyPrice", trade.get(i-1).getPrice());
					GoodInfo.put("isBuy", true);
				}
				else 
					GoodInfo.put("isBuy", false);
			}
			else{
				if(trade.size()>0) {
					GoodInfo.put("saleNum",trade.size());
					GoodInfo.put("buyPrice", trade.get(0).getPrice());
					GoodInfo.put("isSale", true);
				}
				else
					GoodInfo.put("isSale", false);
			}
			map.addAttribute("user",user);
		}
		
		map.addAttribute("product",GoodInfo);
		return "show";
	}
	
	@RequestMapping(value="/account.do")
	public String account(ModelMap map,HttpSession session) {
		Map<String,Object> user = new HashMap<String,Object>();
		List<Goods> goods = new ArrayList<Goods>();
		List<Trade> trade = new ArrayList<Trade>();
		List<Map> buyList=new ArrayList<Map>();
		int total =0;
		goods=goodsService.getGoods();
		if(session.getAttribute("username")!=null) {
			user.put("id", session.getAttribute("id"));
			user.put("username", session.getAttribute("username"));
			user.put("password",  session.getAttribute("password"));
			user.put("nickname", session.getAttribute("nickName"));
			user.put("usertype", session.getAttribute("userType"));
			//System.out.println(session.getAttribute("userType"));
			map.addAttribute("user", user);
		}
		for(int i=0;i<goods.size();i++) {
			Map<String,Object> GoodInfo=new HashMap<String,Object>();
			trade=tradeService.getTrxByCon(i+1);
			if(trade.size()>0) {
				GoodInfo.put("id", goods.get(i).getId());
				GoodInfo.put("title", goods.get(i).getTitle());
				GoodInfo.put("image", goods.get(i).getIcon());
				GoodInfo.put("buyPrice", trade.get(0).getPrice()*trade.size());
				GoodInfo.put("buyNum", trade.size());
				GoodInfo.put("buyTime", trade.get(0).getTime());
				total=total+trade.size()*trade.get(0).getPrice();
				//System.out.println(trade.get(0).getTime());
				buyList.add(GoodInfo);
			}
		}
		
		//map.addAttribute("total", total);
		map.addAttribute("buyList", buyList);
		return "account";
	}
	
	@RequestMapping(value="/settleAccount.do")
	public String settleAccount(ModelMap map,HttpSession session){
		Map<String,Object> user = new HashMap<String,Object>();
		
		if(session.getAttribute("username")!=null) {
			user.put("id", session.getAttribute("id"));
			user.put("username", session.getAttribute("username"));
			user.put("password",  session.getAttribute("password"));
			user.put("nickname", session.getAttribute("nickName"));
			user.put("usertype", session.getAttribute("userType"));
			//System.out.println(session.getAttribute("userType"));
			map.addAttribute("user", user);
		}
		return "settleAccount";
	}
	
	@RequestMapping(value="/publicSubmit.do")
	public String publicSubmit(ModelMap map,HttpSession session,@RequestParam("title") String title,@RequestParam("image") String image,@RequestParam("summary") String summary,@RequestParam("detail") String detail,@RequestParam("price") int price){
		Map<String,Object> user = new HashMap<String,Object>();
		Map<String,Object> product = new HashMap<String,Object>();
		if(session.getAttribute("username")!=null) {
			user.put("id", session.getAttribute("id"));
			user.put("username", session.getAttribute("username"));
			user.put("password",  session.getAttribute("password"));
			user.put("nickname", session.getAttribute("nickName"));
			user.put("usertype", session.getAttribute("userType"));
			goodsService.setgoods(price, title, image, summary,detail);
			product.put("id", goodsService.getLastId());
			product.put("price", price);
			product.put("title", title);
			product.put("image", image);
			product.put("summary", price);
			product.put("detail", detail);
			map.addAttribute("user", user);
			map.addAttribute("product", product);
		}
		return "publicSubmit";
	}
	
	@RequestMapping(value="/edit.do")
	public String publicSubmit(ModelMap map,HttpSession session,@RequestParam("id") int id) {
		Map<String,Object> user = new HashMap<String,Object>();
		Map<String,Object> product = new HashMap<String,Object>();
		Goods good;
		good = goodsService.getGoodsById(id);
		if(session.getAttribute("username")!=null) {
			user.put("id", session.getAttribute("id"));
			user.put("username", session.getAttribute("username"));
			user.put("password",  session.getAttribute("password"));
			user.put("nickname", session.getAttribute("nickName"));
			user.put("usertype", session.getAttribute("userType"));
			map.addAttribute("user", user);
			product.put("id", id);
			product.put("title", good.getTitle());
			product.put("summary", good.getAbs());
			product.put("detail", good.getText());
			product.put("image", good.getIcon());
			product.put("price", good.getPrice());
			//System.out.println(product);
			map.put("product", product);
			
		}
		return "edit";
	}
	
	@RequestMapping(value="/editSubmit.do")
	public String publicSubmit(ModelMap map,HttpSession session,@RequestParam("id") int id,@RequestParam("title") String title,@RequestParam("image") String image,@RequestParam("summary") String summary,@RequestParam("detail") String detail,@RequestParam("price") int price) {
		Map<String,Object> user = new HashMap<String,Object>();
		Map<String,Object> product = new HashMap<String,Object>();
		Goods good;
		good = goodsService.getGoodsById(id);
		if(session.getAttribute("username")!=null) {
			user.put("id", session.getAttribute("id"));
			user.put("username", session.getAttribute("username"));
			user.put("password",  session.getAttribute("password"));
			user.put("nickname", session.getAttribute("nickName"));
			user.put("usertype", session.getAttribute("userType"));
			map.addAttribute("user", user);
			goodsService.updategood(id, price, title, image, summary, detail);
			product.put("id", id);
			product.put("price", price);
			product.put("title", title);
			product.put("image", image);
			product.put("summary", price);
			product.put("detail", detail);
			map.addAttribute("user", user);
			map.put("product", product);
			
		}
		return "editSubmit";
	}
	
	@RequestMapping(value="/api/login.do",method=RequestMethod.POST)
	@ResponseBody
	public Map Userlogin(@RequestParam("userName") String userName,@RequestParam("password") String password,User user,ModelMap map,HttpSession session,HttpServletResponse response) throws IOException {
		user=userService.login(userName, password);
		Map<String,String> res = new HashMap<String,String>();
		if (user != null) {
			session.setAttribute("id",user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("nickName", user.getNickName());
            session.setAttribute("userType", user.getUserType());
            res.put("code", "200");
            res.put("message","密码正确");
            res.put("result", "true");
            //System.out.println("123");
            return res;
        }
		return res;
	}
	
	@RequestMapping(value="/api/delete.do",method=RequestMethod.POST)
	@ResponseBody
	public Map Userlogin(@RequestParam("id") int id,HttpSession session,HttpServletResponse response) throws IOException {
			Map<String,String> res = new HashMap<String,String>();
			goodsService.deletegood(id);
            res.put("code", "200");
            res.put("message","密码正确");
            res.put("result", "true");
            //System.out.println("123");
            return res;
	}
	
	@RequestMapping(value="/api/buy.do",method=RequestMethod.POST)
	@ResponseBody
	public Map Userlogin(HttpSession session,HttpServletRequest request) throws IOException {
		Map<String,String> res = new HashMap<String,String>();
			/*for (Cookie cookie : cookies) {
				System.out.println(cookie.getName()+"12334");
			}
			Map<String,String> res = new HashMap<String,String>(); 
			//System.out.println(request.getParameterNames()+"jjj");
			Goods good;
			/*for(int i=0;i<buylist.size();i++) {
				good=goodsService.getGoodsById((int)buylist.get(i).get("id"));
				for (int j =0;j<(int)buylist.get(i).get("number");j++) {
					tradeService.setTrx(good.getId(), 1, good.getPrice(),20171029);
				}
			}*/
			//System.out.println(data);
			//goodsService.getGoodsById(id)(id);
            res.put("code", "200");
            res.put("message","密码正确");
            res.put("result", "true");
            return res;
	}
	
	@RequestMapping(value="/api/upload.do",method=RequestMethod.POST)
	@ResponseBody
	public Map upload(@RequestParam("File") byte[] id,HttpSession session,HttpServletRequest request) throws IOException {
		Map<String,String> res = new HashMap<String,String>();
			System.out.println(id);
            res.put("code", "200");
            res.put("message","密码正确");
            res.put("result", "true");
            return res;
	}
	
	public void CreateIma(byte[] image,String path){
		try {
			FileOutputStream out = new FileOutputStream(path+"//image//ppp.png");
			out.write(image);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
