package edu.northwestu.intc3283.datasourcestarter.tasks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northwestu.intc3283.datasourcestarter.tasks.entity.Task;
import edu.northwestu.intc3283.datasourcestarter.tasks.entity.TaskRequest;
import edu.northwestu.intc3283.datasourcestarter.tasks.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;



@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class TasksApicontrollerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private TasksRepository taskRepository;


    @Test
    public void getTaskProvides200OkWithaValidId() throws Exception {
        Task mockedTaskResponse = new Task();
        mockedTaskResponse.setId(1L);
        mockedTaskResponse.setTitle("title");
        mockedTaskResponse.setDescription("description");
        mockedTaskResponse.setStatus("PENDING");
        mockedTaskResponse.setCreatedAt(Instant.now());
        when(this.taskRepository.findById(1L)).thenReturn(Optional.of(mockedTaskResponse));
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/tasks/1")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        resultActions.andDo(document("tasks/get-one-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));

    }

    @Test
    public void getTaskList200Ok() throws Exception {
        Task mockedTaskResponse = new Task();
        mockedTaskResponse.setId(1L);
        mockedTaskResponse.setTitle("title");
        mockedTaskResponse.setDescription("description");
        mockedTaskResponse.setStatus("PENDING");
        mockedTaskResponse.setCreatedAt(Instant.now());

        Page<Task> expectedResponse = new PageImpl<>(List.of(mockedTaskResponse));
        when(this.taskRepository.findAll(any(Pageable.class))).thenReturn(expectedResponse);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/tasks?page=0&size=1")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        resultActions.andDo(document("tasks/get-list-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }

    @Test
    public void createANewTask200Ok() throws Exception {
        Task taskRequest = new Task();
        taskRequest.setTitle("title");
        taskRequest.setDescription("description");

        ObjectMapper objectMapper = new ObjectMapper();
        String taskRequestJson = objectMapper.writeValueAsString(taskRequest);

        Task taskResponse = new Task();
        taskResponse.setId(1L);
        taskResponse.setTitle(taskRequest.getTitle());
        taskResponse.setDescription(taskRequest.getDescription());
        taskResponse.setStatus("PENDING");
        taskResponse.setCreatedAt(Instant.now());

        when(this.taskRepository.save(any(Task.class))).thenReturn(taskResponse);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/tasks")
                .contentType("application/json")
                        .content(taskRequestJson)
                        .accept("application/json"))
                        .andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(document("tasks/create-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }
    @Test
    public void createANewTask400OkWhenTitleIsTooShort() throws Exception {
        Task taskRequest = new Task();
        taskRequest.setTitle("smol");
        taskRequest.setDescription("description");

        ObjectMapper objectMapper = new ObjectMapper();
        String taskRequestJson = objectMapper.writeValueAsString(taskRequest);

        Task taskResponse = new Task();
        taskResponse.setId(1L);
        taskResponse.setTitle(taskRequest.getTitle());
        taskResponse.setDescription(taskRequest.getDescription());
        taskResponse.setStatus("PENDING");
        taskResponse.setCreatedAt(Instant.now());

        when(this.taskRepository.save(any(Task.class))).thenReturn(taskResponse);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/tasks")
                        .contentType("application/json")
                        .content(taskRequestJson)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultActions.andDo(document("tasks/create-400",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }


    @Test
    public void putTask200Ok() throws Exception {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("title");
        taskRequest.setDescription("description");

        ObjectMapper objectMapper = new ObjectMapper();
        String taskRequestJson = objectMapper.writeValueAsString(taskRequest);

        Task taskResponse = new Task();
        taskResponse.setId(1L);
        taskResponse.setTitle("title2");
        taskResponse.setDescription(taskRequest.getDescription());
        taskResponse.setStatus("PENDING");
        taskResponse.setCreatedAt(Instant.now());
        when(this.taskRepository.findById(1L)).thenReturn(Optional.of(taskResponse));

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.put("/tasks/1")
                        .contentType("application/json")
                        .content(taskRequestJson)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(document("tasks/put-one-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }

    @Test
    public void putTask400OkWhenTitleIsTooShort() throws Exception {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("smol");
        taskRequest.setDescription("description");

        ObjectMapper objectMapper = new ObjectMapper();
        String taskRequestJson = objectMapper.writeValueAsString(taskRequest);

        Task taskResponse = new Task();
        taskResponse.setId(1L);
        taskResponse.setTitle("title2");
        taskResponse.setDescription(taskRequest.getDescription());
        taskResponse.setStatus("PENDING");
        taskResponse.setCreatedAt(Instant.now());
        when(this.taskRepository.findById(1L)).thenReturn(Optional.of(taskResponse));

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.put("/tasks/1")
                        .contentType("application/json")
                        .content(taskRequestJson)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultActions.andDo(document("tasks/put-one-400",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }
    @Test
    public void deleteATask204Ok() throws Exception {
        Task mockedTaskResponse = new Task();
        mockedTaskResponse.setId(1L);
        mockedTaskResponse.setTitle("title");
        mockedTaskResponse.setDescription("description");
        mockedTaskResponse.setStatus("PENDING");
        mockedTaskResponse.setCreatedAt(Instant.now());

        this.taskRepository.save(mockedTaskResponse);
        this.taskRepository.deleteById(1L);
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.delete("/tasks/1")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        resultActions.andDo(document("tasks/delete-one-204",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }

    @Test
    public void getTaskProvides404NotFoundWhenRepositoryReturnsEmptyOptional() throws Exception {
        when(this.taskRepository.findById(1L)).thenReturn(Optional.empty());
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/tasks/1")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        resultActions.andDo(document("tasks/get-one-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));

    }


}