package com.example.demo.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "file_records")
public class FileRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> records;

    public FileRecords() {
    }

    public FileRecords(List<String> records) {
        this.records = records;
    }

    public Long getId() {
        return id;
    }

    public List<String> getRecords() {
        return records;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecords(List<String> records) {
        this.records = records;
    }
}