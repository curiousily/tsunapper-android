package com.divi.tsunapper.publisher;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnPublishListener;
import com.sromku.simple.fb.entities.Feed;

public class FacebookPublisher {

	public interface PulishListener {
		void onPublished();
	}

	private SimpleFacebook simpleFacebook;

	public FacebookPublisher(SimpleFacebook simpleFacebook) {
		this.simpleFacebook = simpleFacebook;
	}

	public void publish(String message, String name, String caption,
			String description, String link, String picture,
			final PulishListener listener) {

		Feed feed = new Feed.Builder().setMessage(message).setName(name)
				.setCaption(caption).setDescription(description)
				.setPicture(picture).setLink(link).build();

		simpleFacebook.publish(feed, new OnPublishListener() {

			@Override
			public void onFail(String reason) {

			}

			@Override
			public void onException(Throwable throwable) {

			}

			@Override
			public void onThinking() {

			}

			@Override
			public void onComplete(String id) {
				listener.onPublished();
			}
		});

	}

}
