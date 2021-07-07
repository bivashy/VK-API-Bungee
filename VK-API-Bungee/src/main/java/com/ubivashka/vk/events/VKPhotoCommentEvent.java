package com.ubivashka.vk.events;

import com.vk.api.sdk.objects.callback.PhotoComment;
import net.md_5.bungee.api.plugin.Event;

public class VKPhotoCommentEvent extends Event {
  private PhotoComment photoComment;
  
  public VKPhotoCommentEvent(PhotoComment photoComment) {
    setPhotoComment(photoComment);
  }
  
  public PhotoComment getPhotoComment() {
    return this.photoComment;
  }
  
  public void setPhotoComment(PhotoComment photoComment) {
    this.photoComment = photoComment;
  }
}
