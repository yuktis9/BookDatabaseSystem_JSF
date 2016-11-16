package com.bookdata.converter;

import java.util.Iterator;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Page;
import com.restfb.types.User;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBAH5Ls8LZA3sZCiZCFMazTKxBYefcyPtHUK6nKU13i3E0PWuOrqizF9lTUEZA4x9KLqjpPjoGs3MzVYfZBWXZBQaECZCRznmZBKI0pN99Oi20ZBqhvsba2BKdhhxdIK3hJMZC7sftuySMyAQTeEeWmBLNIhQIEBy1w0p4KANUdphlRfb8CFE2D7rURmFzCppb4ZB09Sg14VAb8vm");
		
		User user = facebookClient.fetchObject("me", User.class);
		Page page = facebookClient.fetchObject("cocacola", Page.class);
		
		Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);
		
		//System.out.println("F name: "+myFriends.toString());
		
		System.out.println(""+myFriends.getTotalCount());
		Iterator<List<User>> d = myFriends.iterator();
			
		for (List<User> list : myFriends) {
			
			for (User t : list) {
				System.out.println("F name: "+t.getName());
			}
		}
			
		}
	

}
