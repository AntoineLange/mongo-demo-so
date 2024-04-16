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

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocumentService
{
    private MongoTemplate mongoTemplate;

    public List<DocumentEntity> getAggregatedDocuments(boolean active, String group, boolean excludeParent)
    {
        List<AggregationOperation> stages = new ArrayList<>();
        stages.add(Aggregation.match(Criteria.where("active").is(active).and("group").is(group)));
        stages.add(Aggregation.lookup("collection", "parent_id", "parent_id", "children"));
        stages.add(Aggregation.unwind("$children"));
        stages.add(Aggregation.replaceRoot("$children"));

        if (excludeParent)
        {
            stages.add(Aggregation.match(Criteria.where("group").exists(false)));
        }

        return mongoTemplate
            .aggregate(Aggregation.newAggregation(stages), "collection", DocumentEntity.class)
            .getMappedResults();
    }
}
