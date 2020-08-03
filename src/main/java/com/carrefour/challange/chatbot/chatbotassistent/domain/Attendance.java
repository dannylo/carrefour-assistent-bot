package com.carrefour.challange.chatbot.chatbotassistent.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.http.protocol.RequestDate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.jca.cci.core.InteractionCallback;

import com.carrefour.challange.chatbot.chatbotassistent.enums.AttendanceStatus;
import com.carrefour.challange.chatbot.chatbotassistent.enums.CategoryRequest;
import com.carrefour.challange.chatbot.chatbotassistent.enums.DataRequest;
import com.carrefour.challange.chatbot.chatbotassistent.enums.DataType;
import com.carrefour.challange.chatbot.chatbotassistent.enums.TypeProblem;
import com.carrefour.challange.chatbot.chatbotassistent.strategies.StrategyClassify;

@Entity
@Table(name = "attendance")
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String protocol;
	private LocalDateTime created;
	private TypeProblem typeProblem;
	private CategoryRequest category;
	private String generalDescription;
	@OneToMany(cascade ={ CascadeType.PERSIST, CascadeType.REMOVE })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ItemData> datas;
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private Evaluation evaluation;
	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;
	
	public Attendance() {
		this.status = AttendanceStatus.PENDENT;
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
	
	public void registerData(String data, StrategyClassify strategy) {
		this.datas = strategy.execute(data);
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

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public AttendanceStatus getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}
	
	
	
	
}
