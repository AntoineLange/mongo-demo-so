/*
 *=============================================================================
 *                      THIS FILE AND ITS CONTENTS ARE THE
 *                    EXCLUSIVE AND CONFIDENTIAL PROPERTY OF
 *
 *                          EXPRETIO TECHNOLOGIES, INC.
 *
 * Any unauthorized use of this file or any of its parts, including, but not
 * limited to, viewing, editing, copying, compiling, and distributing, is
 * strictly prohibited.
 *
 * Copyright ExPretio Technologies, Inc., 2024. All rights reserved.
 *=============================================================================
 */
package com.example.demomongo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

@DataMongoTest
@Import(DocumentService.class)
class DocumentServiceTest
{
    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    public void getAggregatedDocumentsTest()
    {
        DocumentEntity parent = createDocumentEntity("doc_123", 123, true, "A");
        DocumentEntity child1 = createDocumentEntity("doc_123_1", 123, true, null);
        DocumentEntity child2 = createDocumentEntity("doc_123_2", 123, true, null);

        documentRepository.saveAll(List.of(parent, child1, child2));
        System.out.println("All docs = " + documentRepository.findAll());

        var resultWithoutParent = documentService.getAggregatedDocuments(true, "A", true);
        System.out.println("Result without parent = " + resultWithoutParent);

        var resultWithParent = documentService.getAggregatedDocuments(true, "A", false);
        System.out.println("Result with parent = " + resultWithParent);
    }

    private static DocumentEntity createDocumentEntity(String id, Integer parentId, boolean active, String group)
    {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setId(id);
        documentEntity.setParent_id(parentId);
        documentEntity.setActive(active);
        documentEntity.setGroup(group);

        return documentEntity;
    }
}
