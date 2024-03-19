package com.code.chatboat.model;

import com.google.gson.annotations.SerializedName;

public class ResponseApiItem{

	@SerializedName("Answer")
	private String answer;

	@SerializedName("Question")
	private String question;

	public void setAnswer(String answer){
		this.answer = answer;
	}

	public String getAnswer(){
		return answer;
	}

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return question;
	}

	@Override
 	public String toString(){
		return 
			"ResponseApiItem{" + 
			"answer = '" + answer + '\'' + 
			",question = '" + question + '\'' + 
			"}";
		}
}