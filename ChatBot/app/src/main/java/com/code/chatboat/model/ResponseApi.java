package com.code.chatboat.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseApi{

	@SerializedName("ResponseApi")
	private List<ResponseApiItem> responseApi;

	public void setResponseApi(List<ResponseApiItem> responseApi){
		this.responseApi = responseApi;
	}

	public List<ResponseApiItem> getResponseApi(){
		return responseApi;
	}

	@Override
 	public String toString(){
		return 
			"ResponseApi{" + 
			"responseApi = '" + responseApi + '\'' + 
			"}";
		}
}