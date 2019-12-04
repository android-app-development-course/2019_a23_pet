package com.example.gohome.utils;

import java.io.Serializable;

/**
 * 返回结果体
 */
public class BaseResponse<T> implements Serializable {

	private static final long serialVersionUID = 1083303955294383541L;
    private int flag;
    private String message;
    private T datas;
    private int total;
    
    public void setFlag(int flag) {
		this.flag = flag;
		this.message = BaseFlagMessage.FLAG_MSG_MAP.get(flag);
	}
    
    public void setMessage(int flag, String msg) {
        this.flag = flag;
        this.message = msg;
    }

    public int getFlag() {
        return flag;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

	public String getMessage() {
		return message;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	/*public String getSignature() {//每个请求返回签名
		return RSAUtil.getRsaSign();
	}*/
	
}
