package com.godeltech.gbf.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BotMessage {
    @Id
    private Integer messageId;
    @Column
    private Long userId;
    @Column
    private Long chatId;
    @Column
    private Timestamp createdAt;
}
