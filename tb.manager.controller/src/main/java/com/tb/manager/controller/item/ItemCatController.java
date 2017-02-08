package com.tb.manager.controller.item;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tb.manager.pojo.ItemCat;
import com.tb.manager.service.item.ItemCatService;

/**

* 作者：haoxd
* 创建时间：2017年1月19日 下午5:29:31  
* 类说明：商品类目相关
 */
@Controller
@RequestMapping(value="/item/cat")
public class ItemCatController {
	
	@Resource(name="itemCatService")
	private ItemCatService catService;
	private ItemCat ic ;
	
	/**
	 * 查询商品类目列表
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(
			@RequestParam(value="id",defaultValue="0") Long pid){
		
		try {
			ic = new ItemCat();
			ic.setParentId(pid);
			List<ItemCat> list = this.catService.queryListByWhere(ic);
			if (null == list || list.isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
}
