package com.tb.manager.controller.item;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tb.manager.pojo.ItemCat;
import com.tb.manager.service.item.ItemCatService;
import com.tb.manager.service.item.ItemService;

/**

* 作者：haoxd
* 创建时间：2017年1月19日 下午5:29:31  
* 类说明：商品类目相关
 */
@Controller
@RequestMapping(value="/item/cat")
public class ItemCatController {

	private static final Logger  log = LoggerFactory.getLogger(ItemCatController.class); 
	
	/**
	 * 商品类目服务
	 * */
	
	@Resource(name="itemCatService")
	private ItemCatService catService;
	
	/**
	 * 商品服务
	 * */
	@Resource(name="itemService")
	private ItemService itemService;
	private ItemCat ic ;
	
	/**
	 * 查询商品类目列表
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(
			@RequestParam(value="id",defaultValue="0") Long pid){
		
		try {
			if(log.isDebugEnabled()){
				log.debug("查询商品类目列表入参：pid="+pid);				
			}
			ic = new ItemCat();
			ic.setParentId(pid);
			List<ItemCat> list = this.catService.queryListByWhere(ic);
			if (null == list || list.isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			log.error("查询商品类目列表错误："+e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 更具商品类目id获取商品类目名称
	 * @param cid:商品类目id
	 * 
	 * @return
	 */
	@RequestMapping(value="/queryItemNameByCid",method=RequestMethod.GET)
	public ResponseEntity<ItemCat> queryItemNameByCid(@RequestParam("id") Long id){
		log.debug("更具商品类目id获取商品类目名称入参 id："+id);
		try {
			
			Long itemCatId = this.itemService.queryCidById(id);
			
			ItemCat icate= this.catService.queryItemNameByCid(itemCatId);
			return ResponseEntity.ok(icate);
		} catch (Exception e) {
			log.error("更具商品类目id获取商品类目名称："+e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
	}
	
	
}
