
package com.khunglong.xanh.json;

import java.util.List;
import com.khunglong.xanh.myfacebook.object.FbComments;
import com.khunglong.xanh.myfacebook.object.FbLikes;

public class PageData{
   	private List<Actions> actions;
   	private FbComments comments;
   	private FbLikes likes;
   	private From from;
   	private String icon;
   	private String id;
   	private String name;
   	private String source;
   	private String sourceQuality;
   	private String created_time;
   	private String link;
   	private String message;
   	private String object_id;
   	private String picture;
   	private Privacy privacy;
   	private String status_type;
   	private String type;
   	private String updated_time;
   	
   	
   	

 	
	public FbLikes getLikes() {
		return likes;
	}
	public void setLikes(FbLikes likes) {
		this.likes = likes;
	}
	
	
	public PageData(String id, String name, String source, String created_time) {
	    super();
	    this.id = id;
	    this.name = name;
	    this.source = source;
	    this.created_time = created_time;
	    likes = new FbLikes();
    }
	
	public PageData(String id, String name, String source, String created_time, String highSource) {
	    super();
	    this.id = id;
	    this.name = name;
	    this.source = source;
	    this.created_time = created_time;
	    this.sourceQuality = highSource;
	    likes = new FbLikes();
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public PageData() {
	    super();
	    // TODO Auto-generated constructor stub
    }
	public List<Actions> getActions(){
		return this.actions;
	}
	public void setActions(List<Actions> actions){
		this.actions = actions;
	}
 	public FbComments getComments(){
		return this.comments;
	}
	public void setComments(FbComments comments){
		this.comments = comments;
	}
 	public String getCreated_time(){
		return this.created_time;
	}
	public void setCreated_time(String created_time){
		this.created_time = created_time;
	}
 	public From getFrom(){
		return this.from;
	}
	public void setFrom(From from){
		this.from = from;
	}
 	public String getIcon(){
		return this.icon;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getLink(){
		return this.link;
	}
	public void setLink(String link){
		this.link = link;
	}
 	public String getMessage(){
		return this.message;
	}
	public void setMessage(String message){
		this.message = message;
	}
 	public String getObject_id(){
		return this.object_id;
	}
	public void setObject_id(String object_id){
		this.object_id = object_id;
	}
 	public String getPicture(){
		return this.picture;
	}
	public void setPicture(String picture){
		this.picture = picture;
	}
 	public Privacy getPrivacy(){
		return this.privacy;
	}
	public void setPrivacy(Privacy privacy){
		this.privacy = privacy;
	}
 	public String getStatus_type(){
		return this.status_type;
	}
	public void setStatus_type(String status_type){
		this.status_type = status_type;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
 	public String getUpdated_time(){
		return this.updated_time;
	}
	public void setUpdated_time(String updated_time){
		this.updated_time = updated_time;
	}
	public String getSourceQuality() {
		return sourceQuality;
	}
	public void setSourceQuality(String sourceQuality) {
		this.sourceQuality = sourceQuality;
	}
	
	
}
