package com.document.service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Document extends BaseEntity{
    private String name;
    private String title;
    private String content;
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.LAZY)
    private List<DocumentTag> documentTags = new ArrayList<>();
}
