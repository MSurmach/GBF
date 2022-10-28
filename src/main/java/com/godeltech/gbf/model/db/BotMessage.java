package com.godeltech.gbf.model.db;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;


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
    private LocalDate createdAt;
}
