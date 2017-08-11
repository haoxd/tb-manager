package com.tb.manager.service.item;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.ParseException;
import org.dom4j.DocumentException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tb.common.bean.EasyUIResult;
import com.tb.common.service.cache.RedisComusterService;
import com.tb.manager.dao.item.ItemDao;
import com.tb.manager.pojo.Item;
import com.tb.manager.pojo.ItemDesc;
import com.tb.manager.pojo.ItemParamItem;
import com.tb.manager.service.base.BaseService;
import com.tb.manager.system.constant.ItemConstant;
import com.tb.manager.system.constant.MQConstant;
import com.tb.manager.system.constant.MQConstant.MQ_MESSAGE_DATA;
import com.tb.manager.system.constant.MQConstant.MQ_MESSAGE_TYPE;
import com.tb.manager.system.constant.MQConstant.MQ_SEND_TYPE;
import com.tb.manager.system.http.ws.intf.HttpClientApiServerTools;
import com.tb.manager.util.readAppXml;
@Service("itemService")
public class ItemService extends BaseService<Item> {
	
	
	/**
	 * 商品描述服务
	 */
	@Resource(name="itemDescService")
	private ItemDescService itemDescService;
	
	/**
	 * 商品dao
	 */
	@Autowired
	private ItemDao dao;
	
	/**
	 * 商品规格数据服务
	 */
	@Resource(name="itemParamItemService")
	private ItemParamItemService ipiService;
	
	@Resource(name="apiServer")
	private HttpClientApiServerTools http;
	
	/**
	 * 注入mq模板
	 */
	@Autowired
	private RabbitTemplate rabbitMq;
	
	@Resource(name="redisComuster")
	private RedisComusterService redisc;
	
	private static final ObjectMapper oMapper = new ObjectMapper();
	


	/**
	 * 增加商品信息
	 * @param item 商品信息
	 * @param desc 商品描述
	 * @param itemParams :商品规格参数
	 * @return
	 */
	public boolean addItem(Item item, String desc, String itemParams) {
		
		item.setStatus(1);//设置默认状态	
		item.setId(null);//处于安全考虑，强制设置id为null，通过数据库自增长添加得到，防止客户端攻击
		Integer resultiItem =  super.add(item);
		ItemDesc iDesc = new ItemDesc();
		//处理商品信息
		iDesc = new ItemDesc();
		iDesc.setItemDesc(desc);
		iDesc.setItemId(item.getId());//获取商品id
		Integer resultDesc= this.itemDescService.add(iDesc);
		//商品规格参数
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(itemParams);
		Integer resultItemParam=this.ipiService.add(itemParamItem);
		boolean addResult =resultiItem.intValue() == 1 && resultDesc.intValue() == 1 && resultItemParam.intValue() == 1;
		//增加成功 mq发送消息
		if(addResult){
			this.mqSendMessage(item.getId(), MQConstant.MQ_SEND_TYPE.MQ_SEND_ADD);
		}
		
		return addResult;
	}

	/**
	 * 查询商品列表
	 * @param page:分页页面号
	 * @param rows：页面数量
	 * @return
	 */
	public EasyUIResult queryItemList(Integer page, Integer rows) {
		//设置分页参数
		PageHelper.startPage(page, rows);
		
		//按照创建时间排序
		Example example = new Example(Item.class);
		
		example.setOrderByClause("create_time DESC");
		List<Item> itemList=this.dao.selectByExample(example);
		
		PageInfo<Item> pageInfo = new PageInfo<Item>(itemList);
			
		return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
	}

	/**
	 * 更具商品id查询商品类目id
	 * @param id:商品id
	 * @return
	 */
	public Long queryCidById(Long id) {
		Item item = new Item();
		item.setId(id);
		return this.dao.selectOne(item).getCid();
	}

	/**更新商品信息描述
	 * @param itemParams :商品规格信息
	 * @param item：商品主体信息
	 * @param desc:商品描述
	 * @return
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public Boolean updateItem(Item item, String desc, String itemParams){
		item.setStatus(null);//强制设置状态不可修改
		Integer resultItem = super.updateSelective(item);
		ItemDesc iDesc = new ItemDesc();
		iDesc.setItemId(item.getId());
		iDesc.setItemDesc(desc);
			
		Integer resultDesc =this.itemDescService.updateSelective(iDesc);
		
		
		Integer resultItemParam = this.ipiService.updateItemParamItem(item.getId(),itemParams);
		
		boolean updateResult =resultItem.intValue() == 1 && resultDesc.intValue() == 1 && resultItemParam.intValue()== 1;
		//如果商品更新成功通知前台系统数据同步
		if(updateResult){
			// 方案一：接口同步
			/*try {
				http.sendPost(readAppXml.readAppXMLByNode("item", "ItemDataNotice")+item.getId());
			} catch (Exception e) {
				e.printStackTrace();
			} */
			
			//方案二：rabbitmq
			//发送消息到mq交换机，通知到其他系统
			this.mqSendMessage(item.getId(), MQConstant.MQ_SEND_TYPE.MQ_SEND_UPDATE);
		}	
		return updateResult;
	}

	/**
	 * 删除商品信息（物理删除）
	 * @param listIds：删除条件
	 * @return
	 */
	public boolean delItemInfos(List<Object> listIds) {
		
		//删除商品信息
		Integer itemResult =super.delByIds(listIds, Item.class, ItemConstant.ItemAttribute.ITEM_ID);
		//删除商品描述信息
		Integer itemDescResult = this.itemDescService.delByIds(listIds, ItemDesc.class, ItemConstant.ItemDescAttribute.ITEM_DESC_ID);
		//删除商品规格信息
		Integer itemParamItemResult = this.ipiService.delByIds(listIds, ItemParamItem.class, ItemConstant.ItemParamItemAttribute.ITEM_PARAM_ITEM_ID);
	
		int count = itemResult.intValue()+itemDescResult.intValue()+itemParamItemResult.intValue();
		boolean  delResult =count>0 ? true :false;
		if(delResult){
			for (Object obj : listIds) {
				this.mqSendMessage((Long)obj, MQConstant.MQ_SEND_TYPE.MQ_SEND_DEL);
			}		
		}
		return delResult;
		
	}
	
	/**
	 * MQ 发送消息
	 */
	private void mqSendMessage(Long itemId,String type){
		try {
			//方案二：rabbitmq
			//发送消息到mq交换机，通知到其他系统
			Map<String,Object> mqMessage = new HashMap<String,Object>();
			mqMessage.put(MQ_MESSAGE_DATA.MQ_MESSAGE_ID,itemId);
			mqMessage.put(MQ_MESSAGE_DATA.MQ_MESSAGE_TYPE, type);
			mqMessage.put(MQ_MESSAGE_DATA.MQ_MESSAGE_DATE, System.currentTimeMillis());
			
				this.rabbitMq.convertAndSend(MQ_MESSAGE_TYPE.MQ_MESSAGE+type, oMapper.writeValueAsString(mqMessage));
			} catch (AmqpException | JsonProcessingException e) {
				e.printStackTrace();
			}
	}

	public void redisTest() {
		this.redisc.del("123");
		this.redisc.set("abc", "23");
		this.redisc.expire("abc",10);
		System.out.println(this.redisc.get("abc"));
	}

	

}
