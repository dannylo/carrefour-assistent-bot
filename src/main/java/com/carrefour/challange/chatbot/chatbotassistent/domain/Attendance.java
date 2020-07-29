package com.carrefour.challange.chatbot.chatbotassistent.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.carrefour.challange.chatbot.chatbotassistent.enums.CategoryRequest;
import com.carrefour.challange.chatbot.chatbotassistent.enums.TypeProblem;

@Entity
@Table(name = "attendance")
public class Attendance {
	@Id
	private long id;
	private String protocol;
	private LocalDateTime created;
	private TypeProblem typeProblem;
	private CategoryRequest category;
	private String generalDescription;
	@OneToMany(cascade ={ CascadeType.PERSIST, CascadeType.REMOVE })
	private List<ItemData> datas;
	
	public Attendance() {
		this.created = LocalDateTime.now();
		this.generateProtocol();
	}
	
	private void generateProtocol() {
		Random random = new Random();
		StringBuilder build = new StringBuilder("");
		this.protocol = build.append(this.created.getYear())
			 .append(created.getDayOfMonth())
			 .append(id)
			 .append(random.nextInt(10))
			 .append(random.nextInt(10))
			 .append(random.nextInt(10))
			 .toString();
	}

	public long getId() {
		return id;
	}
	
	public void addData(ItemData data) {
		this.datas.add(data);
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public TypeProblem getTypeProblem() {
		return typeProblem;
	}

	public void setTypeProblem(TypeProblem typeProblem) {
		this.typeProblem = typeProblem;
	}

	public CategoryRequest getCategory() {
		return category;
	}

	public void setCategory(CategoryRequest category) {
		this.category = category;
	}

	public List<ItemData> getDatas() {
		return datas;
	}

	public String getGeneralDescription() {
		return generalDescription;
	}

	public void setGeneralDescription(String generalDescription) {
		this.generalDescription = generalDescription;
	}

	public String getProtocol() {
		return protocol;
	}
	
}
