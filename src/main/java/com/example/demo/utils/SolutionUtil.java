package com.example.demo.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.demo.bean.Solution52;
import com.example.demo.bean.Urlfor52;

public class SolutionUtil {
	private static String s="src=\"((ht|f)tps?)://.*?/";
//	public static void main(String[] args) {
//		ConcurrentLinkedQueue<Urlfor52> queue = new ConcurrentLinkedQueue<Urlfor52>();
//		// getMarketUrl("http://www.52solution.com/");
//		// getMenu2(queue, "http://www.52solution.com/facs/");
//		// getDetail("http://www.52solution.com/index.php/Home/OneBuy/zicanDetail/id/5604.html");
//		// getDetail("http://www.52solution.com/shop/6150.html");
//
//		// getPageUrl("http://www.52solution.com/index.php/Home/OneBuy/bankaList/kuai/43.html");
//		// getMessage("http://www.52solution.com/news/80029435.html");
//		// getFenYeForMeg("http://www.52solution.com/news/");
//		// getListUrlForMeg("http://www.52solution.com/news/");
//		// getListUrlForPlan("http://www.52solution.com/facs/xpjfa/");
//
//	}

	/**
	 * 传入主页url 得到方案超市url
	 */
	public static ConcurrentLinkedQueue<Urlfor52> getMarketUrl(ConcurrentLinkedQueue<Urlfor52> queue, String url) {
		Document document = JsoupUtil.getDocument(url);
		Elements select = document.select("ul.new-menu-list > li");
		String chaoshi = select.get(4).select("a[href]").attr("abs:href");
		queue.add(new Urlfor52(chaoshi, "0011"));
		return queue;
	}

	/**
	 * 传入主页url 得到方案讯url
	 */
	public static ConcurrentLinkedQueue<Urlfor52> getFanAnXunUrl(ConcurrentLinkedQueue<Urlfor52> queue, String url) {
		Document document = JsoupUtil.getDocument(url);
		Elements select = document.select("ul.new-menu-list > li");
		String fananxun = select.get(9).select("a[href]").attr("abs:href");

		queue.add(new Urlfor52(fananxun, "0021"));
		return queue;
	}

	public static ConcurrentLinkedQueue<Urlfor52> getMenu2(ConcurrentLinkedQueue<Urlfor52> queue, String url) {
		Document document = JsoupUtil.getDocument(url);
		Element class1 = document.getElementsByClass("main").select("div.fa-menu").first();
		for (int i = 0; i < 4; i++) {

			String parent = class1.getElementsByTag("h2").get(i).text();
			String attr = class1.getElementsByTag("li").get(i).select("a[href]").attr("abs:href");
			Document doc = JsoupUtil.getDocument(attr);
			Element box = doc.getElementsByClass("main").select("div.fenglei-list").select("div.fl-box").last();
			Elements li = box.select("ul.list > li");
			Elements ahref = li.select("a[href]");
			for (Element e : ahref) {

				String urlziying = e.attr("abs:href");
				queue.add(new Urlfor52(parent, urlziying, "0012"));
			}
		}
		return queue;

	}

	/**
	 * 传入方案超市url 得到超市方案分类url(过期未使用)
	 */
	public static ConcurrentLinkedQueue<Urlfor52> getMenu(ConcurrentLinkedQueue<Urlfor52> queue, String url) {
		Document document = JsoupUtil.getDocument(url);
		Element class1 = document.getElementsByClass("main").select("div.fa-menu").first();

		Elements select2 = class1.getElementsByTag("li").select("p");
		Elements select = select2.select("a[href]");
		for (Element element : select) {
			// 分类类名
			// String text = element.text();
			// 分类类名下的url
			String attr = element.attr("abs:href");
			if (attr.contains("OneBuy/bankaList/kuai/43.html")) {
				Document doc = JsoupUtil.getDocument(attr);
				Element box = doc.getElementsByClass("main").select("div.fenglei-list").select("div.fl-box").last();
				Elements li = box.select("ul.list > li");
				Elements ahref = li.select("a[href]");
				for (Element e : ahref) {
					// 分类类名
					// String text = e.text();
					// 分类类名下的url
					String urlziying = e.attr("abs:href");
					queue.add(new Urlfor52(urlziying, "0012"));

				}
			} else {

				queue.add(new Urlfor52(attr, "0012"));
			}

		}
		return queue;
	}

	/**
	 * 传入超市方案分类url 若方案分类下方案数目超过20 得到分页Url
	 * 
	 */
	public static ConcurrentLinkedQueue<Urlfor52> getPageUrl(ConcurrentLinkedQueue<Urlfor52> queue, Urlfor52 url52) {
		Document document = JsoupUtil.getDocument(url52.getPath());
		Element class1 = document.getElementsByClass("main").select("div.facs-left").first();
		String num = class1.select("p.xfws > font").text();// 该类目下的方案数量
		int number = Integer.parseInt(num);
		int page = 0;// 分多少页显示
		if (number % 20 == 0) {
			page = number / 20;
		} else {
			page = number / 20 + 1;
		}
		System.out.println(num + "  " + page);

		// 当数量超过20需要分页显示时

		if (number > 20) {
			Element select = document.select("div.new-fenye > a").first();
			String attr = select.attr("abs:href");
			String subUrl = attr.substring(0, attr.lastIndexOf("/"));

			for (int i = 1; i <= page; i++) {
				StringBuilder sb = new StringBuilder(subUrl);
				sb.append("/" + i + ".html");
				// System.out.println(sb.toString());
				queue.add(new Urlfor52(url52.getParent(), sb.toString(), "0112"));
				// return queue;
			}
		} else {
			queue.add(new Urlfor52(url52.getParent(), url52.getPath(), "0112"));
		}
		return queue;

	}

	/**
	 * 得到列表下指向某个方案详情的URL
	 * 
	 */
	public static ConcurrentLinkedQueue<Urlfor52> getListUrlForPlan(ConcurrentLinkedQueue<Urlfor52> queue,
			Urlfor52 url) {
		Document document = JsoupUtil.getDocument(url.getPath());
		Element class1 = document.getElementsByClass("main").select("div.facs-left").first();
		// 获取列表页指向方案详情的url
		Elements select = class1.select("div.jyfa-list > ul").select("h3").select("a[href]");
		if (select != null) {

			for (Element element : select) {
				String attr = element.attr("abs:href");
				queue.add(new Urlfor52(url.getParent(), attr, "0013"));
			}
		}
		// System.out.println(select.size());
		return queue;
	}

	/**
	 * 传入具体某个方案url 得到该方案具体信息
	 * 
	 */
	public static Solution52 getDetail(Urlfor52 url52, BlockingQueue<String> imgQueue) {
		String path = url52.getPath();
		Document document = JsoupUtil.getDocument(url52.getPath());
		Element select = document.select("div.facs-left").first();

		Element select2 = select.select("div.facs-info").first();
		// 通用
		String img = select2.select("img.info-img").select("img[src]").attr("abs:src");
		String name = select2.select("h2").text();
		Solution52 fanAnProduct = new Solution52();

		int i = path.lastIndexOf("/");
		int j = path.lastIndexOf(".");
		fanAnProduct.setId(path.substring(i + 1, j));

		if (img.endsWith(".png") || img.endsWith(".jpg")) {
			imgQueue.add(img);
			img = img.replace("http://www.52solution.com/", "https://fixed.ic360.cn/sol/");
			fanAnProduct.setImg(img);
		}
		fanAnProduct.setName(name);
		fanAnProduct.setParenttype(url52.getParent());
		// 快包自营-特殊处理
		// System.out.println(img);
		// System.out.println(name);
		if (path.contains("OneBuy/zicanDetail/id")) {

			Elements ppp = select2.select("p");
			String[] type = ppp.get(0).text().split("：");
			String[] supplier = ppp.get(1).text().split("：");

			String area = ppp.get(2).text();
			String price = ppp.get(3).text();
			fanAnProduct.setArea(area);
			fanAnProduct.setPrice(price);
			fanAnProduct.setSupplier(supplier[1]);
			fanAnProduct.setType(type[1]);
		} else {
			Elements pinfo = select2.select("p.info");
			for (Element e : pinfo) {
				String text = e.text();
				String[] split = text.split("：");
				int length = split.length;
				if (length == 3) {
					String area = split[1].replace(" ", "");
					area = area.substring(0, area.length() - 4);
					String type = split[2];
					fanAnProduct.setArea(area);
					fanAnProduct.setType(type);
					// System.out.println(area);
					// System.out.println(type);
				}
				if (length == 4) {
					String area = split[1].replace(" ", "");
					area = area.substring(0, area.length() - 4);
					String type = split[2].replace(" ", "");
					type = type.substring(0, type.length() - 4);
					String chip = split[3];
					fanAnProduct.setArea(area);
					fanAnProduct.setType(type);
					fanAnProduct.setChip(chip);
					// System.out.println(area);
					// System.out.println(type);
					// System.out.println(chip);
				}
				if (length == 5) {
					String area = split[1].replace(" ", "");
					area = area.substring(0, area.length() - 4);
					String type = split[2].replace(" ", "");
					type = type.substring(0, type.length() - 4);
					String chip = split[3].replace(" ", "");
					chip = chip.substring(0, chip.length() - 4);

					String price = split[4];
					fanAnProduct.setArea(area);
					fanAnProduct.setType(type);
					fanAnProduct.setChip(chip);
					fanAnProduct.setPrice(price);
				}
			}
		}

		Element facscon = select.select("div.facs-con").first();
		String summary = facscon.toString().replaceAll(s, "src=\"https://fixed.ic360.cn/sol/");
		fanAnProduct.setSummary(summary);
		// 提取summary里的img
		if (summary.contains("img src")) {
			Elements htmlImgs = facscon.select("img[src]");
			for (Element im : htmlImgs) {
				imgQueue.add(im.attr("abs:src"));
			}
		}
		new Thread(new Runnable() {
			// 下载img
			@Override
			public void run() {
				try {
					boolean flag = true;
					while (flag) {
						String urlStr = imgQueue.poll(5, TimeUnit.MINUTES);
						if (urlStr != null) {
							int begin = DownFileUtil.getCharacterPosition(urlStr, "/", 3);
							int end = urlStr.indexOf("?");
							if (end == -1) {
								end = urlStr.length();
							}
							String savePath = urlStr.substring(begin, end);
							DownFileUtil.downLoad(urlStr, savePath);
						} else {
							flag = false;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

		return fanAnProduct;

	}

	/**
	 * 获取方案讯的分页展示url
	 * 
	 */
	public static ConcurrentLinkedQueue<Urlfor52> getFenYeForMeg(ConcurrentLinkedQueue<Urlfor52> queue, String url) {
		Document document = JsoupUtil.getDocument(url);
		Elements left = document.select("div.zx-detail-left");
		String lastPage = left.select("div.new-fenye > a").last().attr("abs:href");
		String subUrl = lastPage.substring(0, lastPage.lastIndexOf("/"));
		String substring = lastPage.substring(lastPage.lastIndexOf("/") + 1, lastPage.length() - 5);
		// 总页数
		int nums = Integer.parseInt(substring);
		for (int i = 1; i <= nums; i++) {
			StringBuilder sb = new StringBuilder(subUrl);
			sb.append("/" + i + ".html");
			queue.add(new Urlfor52(sb.toString(), "0022"));
		}
		return queue;

	}

	/**
	 * 获取方案讯列表中指向方案xun内容页的url
	 * 
	 */
	public static ConcurrentLinkedQueue<Urlfor52> getListUrlForMeg(ConcurrentLinkedQueue<Urlfor52> queue, String url) {
		Document document = JsoupUtil.getDocument(url);
		Elements left = document.select("div.zx-detail-left");
		Element first = left.select("ul.zx-list-box").first();
		Elements select = first.select("li");
		// System.out.println(select.size());
		for (Element ele : select) {
			String attr = ele.select("a[href]").first().attr("abs:href");
			queue.add(new Urlfor52(attr, "0023"));
		}
		return queue;
	}

	/**
	 * 获取方案讯内容
	 * 
	 */
	public static Solution52 getMessage(String url, BlockingQueue<String> imgQueue) {
		Document document = JsoupUtil.getDocument(url);
		Elements left = document.select("div.zx-detail-left");
		Elements select = left.select("div.zx-detail-con");
		// 资讯名
		String name = select.select("h2").first().text();
		// 资讯分类
		String type = select.select("div.des > p").first().text();
		// 资讯内容
		Element context = select.select("div.text").first();
		Solution52 fanAnProduct = new Solution52();
		int i = url.lastIndexOf("/");
		int j = url.lastIndexOf(".");
		fanAnProduct.setId(url.substring(i + 1, j));
		fanAnProduct.setName(name);
		fanAnProduct.setType(type);
		String summary = context.toString().replaceAll(s, "src=\"https://fixed.ic360.cn/sol/");
		fanAnProduct.setSummary(summary);
		// 提取summary里的img
		if (summary.contains("img src")) {
			Elements htmlImgs = context.select("img[src]");
			for (Element im : htmlImgs) {
				imgQueue.add(im.attr("abs:src"));
			}
		}
		new Thread(new Runnable() {
			// 下载img
			@Override
			public void run() {
				String urlStr = null;
				try {
					boolean flag = true;
					while (flag) {
						urlStr = imgQueue.poll(5, TimeUnit.MINUTES);
						if (urlStr != null) {
							int begin = DownFileUtil.getCharacterPosition(urlStr, "/", 3);
							int end = urlStr.indexOf("?");
							if (end == -1) {
								end = urlStr.length();
							}
							String savePath = urlStr.substring(begin, end);
							DownFileUtil.downLoad(urlStr, savePath);
						} else {
							flag = false;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (StringIndexOutOfBoundsException e) {
					e.printStackTrace();
					System.out.println("错误路径" + urlStr);
				}catch (IOException e) {
					e.printStackTrace();
					System.out.println("错误路径" + urlStr);
				}
			}
		}).start();
		return fanAnProduct;

	}
}
