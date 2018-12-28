package com.signalway.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class JsonUtils {
	
	/**
	 * 将对象序列化成出字符串
	 * @param object
	 * @return
	 */
	public static String ConvertToString(Object object)
	{
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			//通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
			//Include.Include.ALWAYS 默认
			//Include.NON_DEFAULT 属性为默认值不序列化
			//Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
			//Include.NON_NULL 属性为NULL 不序列化
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")) ;
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
	
	public static <T> T ConvertToObject (String json,Class<T> classT)
	{
		ObjectMapper mapper = new ObjectMapper();
        T object = null;
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略不需要字段
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")) ;
			object = (T)mapper.readValue(json, classT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return object;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T ConvertToObject (String json,TypeReference valueTypeRef)
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略不需要字段
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        T object = null;
		try {
			object = (T)mapper.readValue(json, valueTypeRef);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return object;
	}
	
	
}
