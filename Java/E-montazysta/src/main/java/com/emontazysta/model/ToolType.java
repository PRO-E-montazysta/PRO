package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE tool_type SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ToolType {

    public ToolType(Long id, String name, int criticalNumber, List<Attachment> attachments,
                    List<ToolsPlannedNumber> listOfToolsPlannedNumber, List<Tool> tools, Company company) {
        this.id = id;
        this.name = name;
        this.criticalNumber = criticalNumber;
        this.attachments = attachments;
        this.listOfToolsPlannedNumber = listOfToolsPlannedNumber;
        this.tools = tools;
        this.company = company;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int criticalNumber;

    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "toolType")
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "toolType")
    private List<ToolsPlannedNumber> listOfToolsPlannedNumber;

    @OneToMany(mappedBy = "toolType")
    private List<Tool> tools;

    @ManyToOne
    private Company company;
}
