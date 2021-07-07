package com.ubivashka.vk.events;

import com.vk.api.sdk.objects.callback.LikeAddRemove;
import net.md_5.bungee.api.plugin.Event;

public class VKLikeAddEvent extends Event {
  private LikeAddRemove likeAdd;
  
  public VKLikeAddEvent(LikeAddRemove likeAdd) {
    setLikeAdd(likeAdd);
  }
  
  public LikeAddRemove getLikeAdd() {
    return this.likeAdd;
  }
  
  public void setLikeAdd(LikeAddRemove likeAdd) {
    this.likeAdd = likeAdd;
  }
}
