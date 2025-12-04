package com.document.service.test;

import com.document.service.controller.DocumentController;
import com.document.service.dto.DocumentCreateRequest;
import com.document.service.dto.DocumentResponseDto;
import com.document.service.dto.TagDto;
import com.document.service.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DocumentController.class)
class DocumentControllerCreateTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    DocumentService documentService;

    @Test
    void createDoc_returnsDocumentResponseDto() throws Exception {
        // given

        DocumentCreateRequest createRequest = new DocumentCreateRequest(
                "Deep Learning Intro",
                "Deep learning models such as CNN, RNN...",
                List.of("AI", "Machine Learning")
        );
        DocumentResponseDto responseDto = new DocumentResponseDto(
                1L,
                "Deep Learning Intro",
                "Deep learning models such as CNN, RNN...",
                List.of(
                        new TagDto(10L, "AI"),
                        new TagDto(11L, "Machine Learning")
                )
        );

        Mockito.when(documentService.createDocument(any(DocumentCreateRequest.class)))
                .thenReturn(responseDto);

        // when + then
        mockMvc.perform(
                        post("/documents")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createRequest))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Deep Learning Intro"))
                .andExpect(jsonPath("$.content").value("Deep learning models such as CNN, RNN..."))
                .andExpect(jsonPath("$.tags[0].id").value(10))
                .andExpect(jsonPath("$.tags[0].name").value("AI"))
                .andExpect(jsonPath("$.tags[1].id").value(11))
                .andExpect(jsonPath("$.tags[1].name").value("Machine Learning"));
    }
}
