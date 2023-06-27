package com.godeltech.gbf.service;

import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.service.session_cache.SessionCacheService;
import com.godeltech.gbf.service.view.View;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

@Service
@RequiredArgsConstructor
public class GuiReloader {
  private final SessionCacheService sessionCacheService;
  private final View<? extends BotApiMethod<?>> view;

  public BotApiMethod<?> getReloadedMethod(Long telegramId, Long chatId){
    Session session = sessionCacheService.get(telegramId);
    if (session.isReloadNeeded()){
      session.setReloadNeeded(false);
      return view.buildView(chatId, session);
    }
    return null;
  }
}
