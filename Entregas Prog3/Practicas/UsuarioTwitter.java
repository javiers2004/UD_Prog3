package Practica05;

import java.util.ArrayList;
import java.util.Objects;

public class UsuarioTwitter implements Comparable<UsuarioTwitter>{

	protected String id;
	protected String screenName;
	protected ArrayList<String> tags;
	protected String avatar;
	protected Long followersCount;
	protected Long friendsCount;
	protected String lang;
	protected Long lastSeen;
	protected String tweetld;
	protected ArrayList<String> friends;
	protected int amigosenelsistema;
	
	protected int getAmigosenelsistema() {
		return amigosenelsistema;
	}
	protected void setAmigosenelsistema(int amigosenelsistema) {
		this.amigosenelsistema = amigosenelsistema;
	}

	protected String getId() {
		return id;
	}
	
	protected void setId(String id) {
		this.id = id;
	}
	protected String getScreenName() {
		return screenName;
	}
	protected void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	protected ArrayList<String> getTags() {
		return tags;
	}
	protected void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	protected String getAvatar() {
		return avatar;
	}
	protected void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	protected Long getFollowersCount() {
		return followersCount;
	}
	protected void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}
	protected Long getFriendsCount() {
		return friendsCount;
	}
	protected void setFriendsCount(Long friendsCount) {
		this.friendsCount = friendsCount;
	}
	protected String getLang() {
		return lang;
	}
	protected void setLang(String lang) {
		this.lang = lang;
	}
	protected Long getLastSeen() {
		return lastSeen;
	}
	protected void setLastSeen(Long lastSeen) {
		this.lastSeen = lastSeen;
	}
	protected String getTweetld() {
		return tweetld;
	}
	protected void setTweetld(String tweetld) {
		this.tweetld = tweetld;
	}
	protected ArrayList<String> getFriends() {
		return friends;
	}
	protected void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}
	public UsuarioTwitter(String id, String screenName, ArrayList<String> tags, String avatar, Long followersCount,
			Long friendsCount, String lang, Long lastSeen, String tweetld, ArrayList<String> friends) {
		super();
		this.id = id;
		this.screenName = screenName;
		this.tags = tags;
		this.avatar = avatar;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.lang = lang;
		this.lastSeen = lastSeen;
		this.tweetld = tweetld;
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "UsuarioTwitter [screenName=" + screenName + ", tags=" + tags + ", avatar=" + avatar
				+ ", followersCount=" + followersCount + ", friendsCount=" + friendsCount + ", lang=" + lang
				+ ", lastSeen=" + lastSeen + ", tweetld=" + tweetld + ", friends=" + friends + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, screenName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioTwitter other = (UsuarioTwitter) obj;
		return Objects.equals(id, other.id) && Objects.equals(screenName, other.screenName);
	}
	@Override
	//PRIMERO COMPARA EL NÚMERO DE AMIGOS EN EL SISTEMA Y EN EL CASO DE QUE SEA DIFERENTE, YA SE FIJARÍA EN EL NICK 
	public int compareTo(UsuarioTwitter o) {
		// TODO Auto-generated method stub
		if (this.getAmigosenelsistema() != o.getAmigosenelsistema()) {
			return Integer.compare( o.getAmigosenelsistema(), this.getAmigosenelsistema());
		}
		return this.screenName.compareTo(o.screenName);
	}
	
	
	
	
}
