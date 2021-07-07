package com.ubivashka.vk.events;

import com.vk.api.sdk.objects.callback.VideoComment;
import net.md_5.bungee.api.plugin.Event;

public class VKVideoCommentRestoreEvent extends Event {
  private VideoComment videoComment;
  
  public VKVideoCommentRestoreEvent(VideoComment videoComment) {
    setVideoComment(videoComment);
  }
  
  public VideoComment getVideoComment() {
    return this.videoComment;
  }
  
  public void setVideoComment(VideoComment videoComment) {
    this.videoComment = videoComment;
  }
}
