package com.tb.manager.util;

import java.io.File;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.DOMException;


/**
 * @author acer11
 *  作者：郝旭东
* 创建时间：2017年4月20日 上午10:16:55  
* 项目名称：tb-web  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：readAppXml.java  
* 类说明：dom4j工具类
 */
public abstract class readAppXml {
	
	
	/**
	 * 获取APPXML文件当中的节点的url属性,当nextNode为null时，获取第一个节点的url属性
	 * @param node:子节点 名
	 * @param nextNode：子节点下的节点名 
	 * @return
	 * @throws DocumentException 
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public static String readAppXMLByNode(String node,String nextNode) throws DocumentException{
		String path = new File(readAppXml.class.getResource("/app.xml").getFile()).getPath();
		String urlValue="";
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(path));
        Element root = document.getRootElement();// 得到根节点  
        Element memberElm=root.element(node);// "member"是节点名
        if(null==nextNode){
        	 urlValue=memberElm.attributeValue("url");
        }else{
        	Element nextMemberElm =memberElm.element(nextNode);
            urlValue=nextMemberElm.attributeValue("url");	
        }      
		return urlValue;
	}

	public static void main(String[] args) {
		try {
		String nodeValue=	readAppXml.readAppXMLByNode("item", "ItemDataNotice");
		System.out.println(nodeValue);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
