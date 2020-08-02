package com.carrefour.challange.chatbot.chatbotassistent.strategies;

import java.util.List;

import com.carrefour.challange.chatbot.chatbotassistent.domain.ItemData;

public interface StrategyClassify {

	List<ItemData> execute(String data);
}
