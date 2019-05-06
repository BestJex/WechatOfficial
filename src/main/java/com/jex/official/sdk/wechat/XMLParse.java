package com.jex.official.sdk.wechat;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 创建时间：2016年11月3日 上午11:40:17
 * 
 * @author andy
 * @version 2.2
 */

public class XMLParse {

    private static final Logger logger = LoggerFactory.getLogger(XMLParse.class);
	private static final String PREFIX_XML = "<xml>";

	private static final String SUFFIX_XML = "</xml>";

	private static final String PREFIX_CDATA = "<![CDATA[";

	private static final String SUFFIX_CDATA = "]]>";

	/**
	 * 转化成xml, 单层无嵌套
	 * 
	 * @param isAddCDATA
	 * @return
	 */
	public static String xmlFormat(Map<String, String> parm, boolean isAddCDATA) {

		StringBuffer strBuff = new StringBuffer(PREFIX_XML);
		if (!CollectionUtils.isEmpty(parm)) {
            if (isAddCDATA) {
                for (Entry<String, String> entry : parm.entrySet()){
                    strBuff.append("<").append(entry.getKey()).append(">");
                    strBuff.append(PREFIX_CDATA);
                    if (StringUtils.hasText(entry.getValue())) {
                        strBuff.append(entry.getValue());
                    }
                    strBuff.append(SUFFIX_CDATA);
                    strBuff.append("</").append(entry.getKey()).append(">");
                }
            }else{
                for (Entry<String, String> entry : parm.entrySet()){
                    strBuff.append("<").append(entry.getKey()).append(">");
                    if (StringUtils.hasText(entry.getValue())) {
                        strBuff.append(entry.getValue());
                    }
                    strBuff.append("</").append(entry.getKey()).append(">");
                }
            }
		}
		return strBuff.append(SUFFIX_XML).toString();
	}
	/**
	 * 解析xml
	 *
	 * @param xml
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> xmlParse(String xml) {
		Map retMap = new HashMap();
		//1.获取SAXReader对象
		SAXReader reader = new SAXReader();
		try {
			reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
			reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		}catch (Exception ex){
            logger.error(ex.getMessage(), ex);
		}
		//2.读取要解析的xml
		Document document = null;
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes("utf-8"));
			document = reader.read(stream);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		//3.获取根节点
		Element rootElement = document.getRootElement();
		List<Element> childElements = rootElement.elements();//获取当前元素下的全部子元素

		for (Element child : childElements) {//循环输出全部book的相关信息
			String name = child.getName();//获取当前元素名
			String text = child.getText();//获取当前元素值
			retMap.put(name,text);
		}
		return retMap;
	}

	/**
	 * 生成xml消息
	 * @param encrypt 加密后的消息密文
	 * @param signature 安全签名
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @return 生成的xml字符串
	 */
	public static String generate(String encrypt, String signature, String timestamp, String nonce) {
		String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
				+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
				+ "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
		return String.format(format, encrypt, signature, timestamp, nonce);
	}
}
