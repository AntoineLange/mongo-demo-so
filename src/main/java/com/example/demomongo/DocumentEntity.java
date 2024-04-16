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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "collection")
@Getter
@Setter
@ToString
public class DocumentEntity {

    @Id
    private String id;
    private Integer parent_id;
    private boolean active;
    private String group;
}
