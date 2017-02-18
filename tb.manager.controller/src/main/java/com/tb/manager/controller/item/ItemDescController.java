package com.tb.manager.controller.item;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tb.manager.pojo.ItemDesc;
import com.tb.manager.service.item.ItemDescService;

/**
 * @author acer11 作者： 创建时间：2017年2月17日 下午8:07:52 项目名称：tb.manager.controller
 * @author daniel
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：ItemDescController.java 类说明：商品描述
 */
@Controller
@RequestMapping("/item/desc")
public class ItemDescController {

	private static final Logger log = LoggerFactory.getLogger(ItemCatController.class);

	@Resource(name = "itemDescService")
	private ItemDescService descService;

	/**
	 * 更具商品id查询商品描述
	 * 
	 * @param itemId：商品id
	 * @return
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable("itemId") Long itemId) {

		log.debug("查询商品描述入参 id ：" + itemId);
		try {
			ItemDesc desc = this.descService.queryById(itemId);

			if (null == desc) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(desc);

		} catch (Exception e) {
			log.debug("查询商品描述错误：" + e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 500

	}

}
