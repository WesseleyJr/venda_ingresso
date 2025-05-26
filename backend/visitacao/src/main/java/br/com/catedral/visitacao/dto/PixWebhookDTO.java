package br.com.catedral.visitacao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class PixWebhookDTO {

	 private String action;
	    private String type;
	    private Data data;

	    public String getAction() {
	        return action;
	    }

	    public void setAction(String action) {
	        this.action = action;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public Data getData() {
	        return data;
	    }

	    public void setData(Data data) {
	        this.data = data;
	    }

	    @JsonIgnoreProperties(ignoreUnknown = true)
	    public static class Data {
	        private String id;

	        public String getId() {
	            return id;
	        }

	        public void setId(String id) {
	            this.id = id;
	        }
	    }
}
