package com.document.service.entity;

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
public class Tag extends BaseEntity{
    private String name;

    @OneToMany(mappedBy = "tag" , fetch = FetchType.LAZY)
    private List<DocumentTag> documentTags = new ArrayList<>();
}
