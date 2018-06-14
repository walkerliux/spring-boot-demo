package com.example.demo.controller;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.Solution52;
import com.example.demo.bean.Urlfor52;
import com.example.demo.service.SolutionService;
import com.example.demo.utils.SolutionUtil;

@RestController
@RequestMapping("/solution")
public class SolutionController {
	private static ConcurrentLinkedQueue<Urlfor52> queue = new ConcurrentLinkedQueue<Urlfor52>();
	private static BlockingQueue<String> img = new ArrayBlockingQueue<String>(200);

	@Autowired
	private SolutionService solutionService;
	
	
	@RequestMapping(value = "/startMarket", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String startMakert(String url) {
		// 传入主页
		if (url != null && !"".equals(url)) {
			queue = SolutionUtil.getMarketUrl(queue, url);
		}
		Urlfor52 urlfor52 = queue.poll();
		if (urlfor52.getType() == "0011") {
			queue = SolutionUtil.getMenu2(queue, urlfor52.getPath());
			bianLiForSoluTion(queue);
		}
		
		return "ok";

	}

	private void bianLiForSoluTion(ConcurrentLinkedQueue<Urlfor52> queue) {
		Iterator<Urlfor52> iterator = queue.iterator();

		while (iterator.hasNext()) {
			Urlfor52 urlfor52 = iterator.next();
			
			iterator.remove();
			if (urlfor52.getType() == "0012") {
				// 分类下 传入首页，查看是否需要分页显示
				queue = SolutionUtil.getPageUrl(queue, urlfor52);
			}
			if (urlfor52.getType() == "0112") {
				// 分页 传入第一页已经遍历过所有分页
				queue = SolutionUtil.getListUrlForPlan(queue, urlfor52);
			}
			if (urlfor52.getType() == "0013") {
				Solution52 solution52 = SolutionUtil.getDetail(urlfor52,img);

				insert(solution52);
			}
		}
	}

	@RequestMapping(value = "/startFangAnXun", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String startFangAnXun(String url) {
		// 传入主页
		queue = SolutionUtil.getFanAnXunUrl(queue, url);
		Urlfor52 urlfor52 = queue.poll();
		if (urlfor52.getType() == "0021") {
			queue = SolutionUtil.getFenYeForMeg(queue, urlfor52.getPath());
			bianLiForSoluMsg(queue);
		}
		return "ok";
	}

	private void bianLiForSoluMsg(ConcurrentLinkedQueue<Urlfor52> queue) {
		Iterator<Urlfor52> iterator = queue.iterator();

		while (iterator.hasNext()) {
			Urlfor52 urlfor52 = iterator.next();
			iterator.remove();
			if (urlfor52.getType() == "0022") {
				// 传入分页，得到分页的列表方案讯
				queue = SolutionUtil.getListUrlForMeg(queue, urlfor52.getPath());
			}
			if (urlfor52.getType() == "0023") {
				Solution52 solution52 = SolutionUtil.getMessage(urlfor52.getPath(),img);
				insert(solution52);
			}
		}
	}

	private void insert(Solution52 solution) {
		solutionService.deleteById(solution);
		solutionService.insert(solution);
	}
	// @RequestMapping("/insert")
	// public void insertdB(Solution52 solution) {
	// Solution52 detail =
	// SolutionUtil.getDetail("http://www.52solution.com/shop/6094.html");
	// solutionService.insert(detail);
	//
	// }
}
