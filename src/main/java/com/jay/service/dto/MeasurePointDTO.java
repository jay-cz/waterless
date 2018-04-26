package com.jay.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MeasurePoint entity.
 */
public class MeasurePointDTO implements Serializable {

    private Long id;

    private LocalDate startTm;

    private LocalDate endTm;

    private String ipAddress;

    private String value;

    private Long houseHoldId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartTm() {
        return startTm;
    }

    public void setStartTm(LocalDate startTm) {
        this.startTm = startTm;
    }

    public LocalDate getEndTm() {
        return endTm;
    }

    public void setEndTm(LocalDate endTm) {
        this.endTm = endTm;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(Long houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeasurePointDTO measurePointDTO = (MeasurePointDTO) o;
        if(measurePointDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), measurePointDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeasurePointDTO{" +
            "id=" + getId() +
            ", startTm='" + getStartTm() + "'" +
            ", endTm='" + getEndTm() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
