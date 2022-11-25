package com.godeltech.gbf.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "telegram_user")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser {
    @Id
    private Long id;

    @Column
    private String userName;

    @Column
    private String language;
}
