/**
 *  Copyright (c) 2014 http://oil.klcar.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.klcarwl.common.utils.jackson;

import java.io.IOException;
import java.sql.Clob;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.klcarwl.common.utils.io.ClobUtil;
/**
 * 自定义Jackson Clob类型转换.
 * @author honey.zhao@aliyun.com  
 * @date 2014-4-1 下午2:49:07 
 *
 */
public class ClobSerializer extends JsonSerializer<Clob> {

		@Override
		public void serialize(Clob arg0, JsonGenerator arg1,
				SerializerProvider arg2) throws IOException,
				JsonProcessingException {
			String json = ClobUtil.getString(arg0);
			arg1.writeString(json);
		}
}