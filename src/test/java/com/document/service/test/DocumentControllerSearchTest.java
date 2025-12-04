package com.document.service.test;

import com.document.service.controller.DocumentController;
import com.document.service.dto.DocumentResponseDto;
import com.document.service.dto.TagDto;
import com.document.service.constant.SearchType;
import com.document.service.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DocumentController.class)
class DocumentControllerSearchTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    DocumentService documentService;

    @Test
    void searchDocuments_returnsList() throws Exception {
        // given
        List<DocumentResponseDto> mocked = List.of(
                new DocumentResponseDto(
                        1L,
                        "Deep Learning Intro",
                        "Deep learning...",
                        List.of(new TagDto(10L, "AI"))
                )
        );

        Mockito.when(documentService.adSearch(eq("Deep"), eq(SearchType.ALL)))
                .thenReturn(mocked);

        // when + then
        mockMvc.perform(
                        get("/documents/search")
                                .param("query", "Deep")
                                .param("mode", "all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Deep Learning Intro"))
                .andExpect(jsonPath("$[0].content").value("Deep learning..."))
                .andExpect(jsonPath("$[0].tags[0].id").value(10))
                .andExpect(jsonPath("$[0].tags[0].name").value("AI"));
    }
}
