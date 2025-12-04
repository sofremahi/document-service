package com.document.service.service;

import com.document.service.Repo.DocumentTagRepo;
import com.document.service.Repo.TagRepository;
import com.document.service.dto.DocumentCreateRequest;
import com.document.service.dto.DocumentResponseDto;
import com.document.service.dto.TagDto;
import com.document.service.entity.Document;
import com.document.service.constant.SearchType;
import com.document.service.Repo.DocumentRepository;
import com.document.service.entity.DocumentTag;

import com.document.service.entity.Tag;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentTagRepo documentTagRepo;
    private final TagRepository tagRepository;

    @Transactional
    public DocumentResponseDto createDocument(DocumentCreateRequest req) {

        Document document = new Document();
        document.setTitle(req.title());
        document.setContent(req.content());

        if (req.tags() != null) {
            for (String rawName : req.tags()) {
                if (rawName == null || rawName.isBlank()) continue;

                String name = rawName.trim();

                Tag tag = tagRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> {
                            Tag t = new Tag();
                            t.setName(name);
                            return tagRepository.save(t);
                        });


                DocumentTag dt = new DocumentTag();
                dt.setDocument(document);
                dt.setTag(tag);

                document.getDocumentTags().add(dt);
            }
        }
        Document saved = documentRepository.save(document);


        Document loaded = documentRepository.findByIdWithTags(saved.getId())
                .orElseThrow(() -> new RuntimeException("Document not found after save"));


        List<TagDto> tags = loaded.getDocumentTags().stream()
                .map(DocumentTag::getTag)
                .distinct()
                .map(t -> new TagDto(t.getId(), t.getName()))
                .toList();
        return new DocumentResponseDto(
                loaded.getId(),
                loaded.getTitle(),
                loaded.getContent(),
                tags
        );
    }

    @Transactional(readOnly = true)
    public DocumentResponseDto getDocument(Long id) {

    Document doc = documentRepository.findByIdWithTags(id)
            .orElseThrow(() -> new RuntimeException("Document not found: " + id));

    List<TagDto> tags = doc.getDocumentTags().stream()
            .map(DocumentTag::getTag)
            .filter(Objects::nonNull)
            .distinct()
            .map(t -> new TagDto(t.getId(), t.getName()))
            .toList();

    return new DocumentResponseDto(
            doc.getId(),
            doc.getTitle(),
            doc.getContent(),
            tags
    );
}

    @Transactional(readOnly = true)
    public List<DocumentResponseDto> adSearch(String query, SearchType mode) {

        String q = (query == null) ? "" : query.trim();

        List<Document> docs = switch (mode) {
            case TITLE -> documentRepository.findByTitleContainingIgnoreCaseFetch(q);
            case CONTENT -> documentRepository.findByContentContainingIgnoreCaseFetch(q);
            case TAG -> documentTagRepo.findDocumentsByTagNameContainingFetch(q);
            case ALL -> documentRepository.searchAllFetch(q);
        };

        return docs.stream()
                .map(this::toDto)
                .toList();
    }

    private DocumentResponseDto toDto(Document d) {
        List<TagDto> tags = d.getDocumentTags().stream()
                .map(DocumentTag::getTag)
                .filter(Objects::nonNull)
                .map(t -> new TagDto(t.getId(), t.getName()))
                .distinct()
                .toList();

        return new DocumentResponseDto(d.getId(), d.getTitle(), d.getContent(), tags);
    }

}
