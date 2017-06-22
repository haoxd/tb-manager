package com.tb.manager.system.constant;

public class MQConstant {

	/**
	 * 类说明：mq 交换机消息类型
	 */
	public interface MQ_MESSAGE_TYPE {

		// 更新
		String MQ_MESSAGE = "ITEM.";

	}

	/**
	 * 类说明：mq 交换机消息发送类型
	 */
	public interface MQ_SEND_TYPE {

		// 更新
		String MQ_SEND_UPDATE = "UPDATE";
		// 删除
		String MQ_SEND_DEL = "DEL";
		// 增加
		String MQ_SEND_ADD = "ADD";

	}

	/**
	 * 类说明：mq 交换机消息数据
	 */
	public interface MQ_MESSAGE_DATA {

		String MQ_MESSAGE_ID = "itemId";
		String MQ_MESSAGE_TYPE = "type";
		String MQ_MESSAGE_DATE = "DATE";

	}

}
