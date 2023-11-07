package br.senai.aula_25_10;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository { //Repositorio - Faz a persistencia de dados - É onde pede para fazer a persistencia de dados - Persistencia = salvar, atualizar, deletar, etc
	private static List<Task> records = new ArrayList<Task>();
	private static Integer nextCode = 1;
	
	static {
		records.add(new Task (nextCode++, "Task 01"));
		records.add(new Task (nextCode++, "Task 02"));
		records.add(new Task (nextCode++, "Task 03"));
	}
	
	public Task findByCode(Integer code) { //Programação funcional //Select where
		return records.stream()
				.filter((current) -> current.getCode().equals(code))
				.findFirst()
				.orElse(null);
		
		//Task t = null //Programação imperativa
		//for(int i = 0; records.size; i++){
		//	if(records.get(i).getCode().equals(code)){
		//		t = records.get(i);
		//		break;
		//	}
		//}
		//return t;
	}
	
	public List<Task> findAll(){ //Select * sem where
		return records; 
	}
	
	public void insert(Task task) {
		task.setCode(nextCode++);
		records.add(task);
	}
	
	public void update(Task task) {
		records = records.stream()
				.map((current) -> {
					return current.getCode().equals(task.getCode()) ? task : current;
				})
				.toList();
	}
	
	public void delete(Integer code) {
		records = records.stream()
				.filter((current) -> !current.getCode().equals(code))
				.toList();
	}
}
