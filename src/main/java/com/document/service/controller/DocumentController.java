package com.document.service.controller;

import com.document.service.dto.DocumentCreateRequest;
import com.document.service.dto.DocumentResponseDto;
import com.document.service.constant.SearchType;
import com.document.service.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
@AllArgsConstructor
public class DocumentController {
    DocumentService documentService;
    @PostMapping
    public DocumentResponseDto createDoc(@RequestBody DocumentCreateRequest req) {
        return documentService.createDocument(req);
    }
    @GetMapping("/{id}")
    public DocumentResponseDto getDocumentById(@PathVariable Long id) {
        return documentService.getDocument(id);
    }

    @GetMapping("/search")
    public List<DocumentResponseDto> searchDocuments(
            @RequestParam String query,
            @RequestParam(defaultValue = "all") String mode
    ) {
        SearchType searchType = SearchType.from(mode);
        return documentService.adSearch(query, searchType);
    }

}
