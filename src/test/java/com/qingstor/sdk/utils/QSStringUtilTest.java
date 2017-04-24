// +-------------------------------------------------------------------------
// | Copyright (C) 2016 Yunify, Inc.
// +-------------------------------------------------------------------------
// | Licensed under the Apache License, Version 2.0 (the "License");
// | you may not use this work except in compliance with the License.
// | You may obtain a copy of the License in the LICENSE file, or at:
// |
// | http://www.apache.org/licenses/LICENSE-2.0
// |
// | Unless required by applicable law or agreed to in writing, software
// | distributed under the License is distributed on an "AS IS" BASIS,
// | WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// | See the License for the specific language governing permissions and
// | limitations under the License.
// +-------------------------------------------------------------------------

package com.qingstor.sdk.utils;

import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class QSStringUtilTest {

    @Test
    public void testReqString() {
        String req = QSStringUtil.getParameterRequired("key", "colume");
        Assert.assertEquals(req, "key is required in colume ");
    }

    @Test
    public void testNotAllowedString() {
        String[] values = {"2", "3"};
        String req = QSStringUtil.getParameterValueNotAllowedError("colume", "key", values);
        Assert.assertEquals(req, "colume value key is not allowed, should be one of 2,3 ");
    }

    @Test
    public void testUserAgentString() {
        String req = QSStringUtil.getUserAgent();
        Assert.assertEquals(req.indexOf(QSConstant.SDK_VERSION) > 0, true);
        Assert.assertEquals(req.indexOf(QSConstant.SDK_NAME) == 0, true);
    }
    
    @Test
	public void mapJsonStringTest() {

		Map m = new HashMap();
		m.put("testString","didididi");
		m.put("testInt",100);
		m.put("testInt2","100");
		m.put("testBoolean",true);
		ParamTestModel model = new ParamTestModel();
		model.setAction("testAction");
		ParamTypeModel typeModel = new ParamTypeModel();
		typeModel.setAlarmStatus("status");
		typeModel.setInstanceClass(10);
		m.put("testObject",typeModel);

		String d = QSStringUtil.getMapToJson(m);
		System.out.println(d);
		JSONObject o = QSJSONUtil.convertJSONObject(d);
		Assert.assertNotNull(o);
		Assert.assertEquals(QSJSONUtil.toString(o,"testString"),"didididi");
		Assert.assertEquals(QSJSONUtil.toInt(o,"testInt2"),100);
	}
    
    
    @Test
    public void testChineseCharactersEncoding() {
        String req;
		try {
			req = QSStringUtil.asciiCharactersEncoding("中文编码测试/{}-==辛苦、");
			System.out.println(req);
			Assert.assertEquals(req.indexOf("{}") > 0, true);
	        Assert.assertEquals(req.indexOf("中文编码") == -1, true);
		} catch (QSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    private static int iThreadCount = 0;
    
    class PutObjectRunable implements Runnable {
    	
    	String threadObjectName;
    	
    	public PutObjectRunable(String objectName){
    		this.threadObjectName = objectName;
    	}
    	
        @Override
        public void run()  {
        	String req;
        	try {
        		for(int i = 0 ; i < 100 ; i++){
        			req = QSStringUtil.asciiCharactersEncoding(threadObjectName);
        			System.out.println("---PutObjectRunable---");
        			Assert.assertEquals(req.indexOf("{}") > 0, true);
        	        Assert.assertEquals(req.indexOf("中文编码") == -1, true);
        		}
    			
    		} catch (QSException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            iThreadCount--;
        }
    }
    
    @Test
    public void testMultiThread() throws InterruptedException {
    	iThreadCount = 100;
        for(int i = 0 ; i < 100 ; i++){
            PutObjectRunable tt = new PutObjectRunable("中文编码测试/{}-==辛苦、");
            new Thread(tt).start();
        }
        while (iThreadCount>1) {
            Thread.sleep(10000);
        }
        
    }
    
    
}
