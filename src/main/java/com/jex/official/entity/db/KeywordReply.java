package com.jex.official.entity.db;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "keyword_reply")
@EntityListeners(AuditingEntityListener.class)
public class KeywordReply {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "official_id", insertable = false, updatable = false)
    private int officialId;
    @ManyToOne
    @JoinColumn(name = "official_id", referencedColumnName="id")
    private Official official;
    @Column(name = "keyword")
    private String keyword;
    @Column(name = "content")
    private String content;
    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public int getId() {
        return id;
    }

    public int getOfficialId() {
        return officialId;
    }

    public Official getOfficial() {
        return official;
    }

    public void setOfficial(Official official) {
        this.official = official;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

}
