package com.khunglong.xanh.main;

public class BaseObject {
	public String  user_avatar;

	public String  user_name;
	public String  created_on;

	public String  answer_user_name;
	public String  recent_answer_date;

	public String  snapshot_img;

	public String  title;
	public String  snapshot_content;

	public int vote_ups;
	public int number_answers;
	public int number_views;

	public String  user_id;
	public String  topic_icon;
	public String  topic_name;
	public String  theme_color;
	public String  is_anonymous;
	public String  question_id;

	public BaseObject() {
		super();
	}

	public BaseObject(String user_avatar, String user_name, String created_on, String answer_user_name,
            String recent_answer_date, String snapshot_img, String title, String snapshot_content, int vote_ups,
            int number_answers, int number_views, String user_id, String topic_icon, String topic_name,
            String theme_color, String is_anonymous, String question_id) {
	    super();
	    this.user_avatar = user_avatar;
	    this.user_name = user_name;
	    this.created_on = created_on;
	    this.answer_user_name = answer_user_name;
	    this.recent_answer_date = recent_answer_date;
	    this.snapshot_img = snapshot_img;
	    this.title = title;
	    this.snapshot_content = snapshot_content;
	    this.vote_ups = vote_ups;
	    this.number_answers = number_answers;
	    this.number_views = number_views;
	    this.user_id = user_id;
	    this.topic_icon = topic_icon;
	    this.topic_name = topic_name;
	    this.theme_color = theme_color;
	    this.is_anonymous = is_anonymous;
	    this.question_id = question_id;
    }

	public String getUser_avatar() {
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar) {
		this.user_avatar = user_avatar;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public String getAnswer_user_name() {
		return answer_user_name;
	}

	public void setAnswer_user_name(String answer_user_name) {
		this.answer_user_name = answer_user_name;
	}

	public String getRecent_answer_date() {
		return recent_answer_date;
	}

	public void setRecent_answer_date(String recent_answer_date) {
		this.recent_answer_date = recent_answer_date;
	}

	public String getSnapshot_img() {
		return snapshot_img;
	}

	public void setSnapshot_img(String snapshot_img) {
		this.snapshot_img = snapshot_img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSnapshot_content() {
		return snapshot_content;
	}

	public void setSnapshot_content(String snapshot_content) {
		this.snapshot_content = snapshot_content;
	}

	public int getVote_ups() {
		return vote_ups;
	}

	public void setVote_ups(int vote_ups) {
		this.vote_ups = vote_ups;
	}

	public int getNumber_answers() {
		return number_answers;
	}

	public void setNumber_answers(int number_answers) {
		this.number_answers = number_answers;
	}

	public int getNumber_views() {
		return number_views;
	}

	public void setNumber_views(int number_views) {
		this.number_views = number_views;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTopic_icon() {
		return topic_icon;
	}

	public void setTopic_icon(String topic_icon) {
		this.topic_icon = topic_icon;
	}

	public String getTopic_name() {
		return topic_name;
	}

	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}

	public String getTheme_color() {
		return theme_color;
	}

	public void setTheme_color(String theme_color) {
		this.theme_color = theme_color;
	}

	public String getIs_anonymous() {
		return is_anonymous;
	}

	public void setIs_anonymous(String is_anonymous) {
		this.is_anonymous = is_anonymous;
	}

	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	
	

}
