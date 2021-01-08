package com.example.ProjectService.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ProjectService.model.ProjectEntity;
import com.example.ProjectService.model.employee.ProjectModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ProjectController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired 
	WebClient.Builder webClientBuilder;

	@Autowired 
	private Environment env;
	
	/*
	 * @Value("${employee.service}") private String value;
	 * 
	 * @Value("${project.post.value}") private String insertProjectUrl;
	 */

	@GetMapping("/getEmployees/{projectId}")
	public ProjectEntity getprojectEmployees(@PathVariable("projectId") String projectId) {
		ProjectEntity aa = restTemplate.getForObject(env.getProperty("employee.service") + projectId, ProjectEntity.class);
		return aa;
	}

	@GetMapping("/getEmployeeonWeb/{projectId}")
	public ProjectEntity getEmployeeonWeb(@PathVariable("projectId") String projectId) {
		ProjectEntity aa = webClientBuilder.build().get().uri(env.getProperty("employee.service") + projectId)
				.retrieve().bodyToMono(ProjectEntity.class).block();
		return aa;
	}

	@PostMapping("/insertProject")
	public String createProducts(@RequestBody Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectModel project = mapper.convertValue(obj,ProjectModel.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ProjectModel> entity = new HttpEntity<ProjectModel>(project,headers);

		return restTemplate.exchange(env.getProperty("project.post.value"), HttpMethod.POST, entity, String.class).getBody();
	}

	
}
