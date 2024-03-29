package com.emontazysta.model;

import com.emontazysta.enums.ToolStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tool SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tool {

    public Tool(Long id, String name, LocalDateTime createdAt, String code, List<ToolRelease> toolReleases,
                Warehouse warehouse, List<ToolEvent> toolEvents, ToolType toolType) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.code = code;
        this.toolReleases = toolReleases;
        this.warehouse = warehouse;
        this.toolEvents = toolEvents;
        this.toolType = toolType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private String code;

    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "tool")
    private List<ToolRelease> toolReleases;

    @ManyToOne
    private Warehouse warehouse;

    @OneToMany(mappedBy = "tool")
    private List<ToolEvent> toolEvents;

    @ManyToOne
    private ToolType toolType;


    public ToolStatus getStatus() {
        if(deleted) {
            return ToolStatus.DELETED;
        }
        if(toolReleases.size() > 0) {
            if(toolReleases.get(toolReleases.size() - 1).getReturnTime() == null)
                return ToolStatus.RELEASED;
        }
        if(toolEvents.size() > 0) {
            if(toolEvents.get(toolEvents.size() - 1).getCompletionDate() == null)
                return ToolStatus.IN_REPAIR;
        }
        return ToolStatus.AVAILABLE;
    }
}
