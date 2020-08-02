package com.carrefour.challange.chatbot.chatbotassistent.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.carrefour.challange.chatbot.chatbotassistent.domain.ItemData;

public class RequestProblemStrategy implements StrategyClassify{
	
	private List<String> fields = Arrays.asList("CPF", "Nome Completo", "Número do pedido");
		
	@Override
	public List<ItemData> execute(String data) {
		List<ItemData> itens = new ArrayList<>();
		Iterator<String> fieldsIterator = fields.iterator();
		data = data.replace("*req", "");
		List<String> values = Arrays.asList(data.trim().split(","));
		values.forEach(v ->{
			ItemData item = new ItemData(fieldsIterator.next(), v);
			itens.add(item);
		});
		
		return itens;
	}

}
