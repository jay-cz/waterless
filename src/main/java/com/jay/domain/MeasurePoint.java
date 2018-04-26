package com.jay.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MeasurePoint.
 */
@Entity
@Table(name = "measure_point")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeasurePoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_tm")
    private LocalDate startTm;

    @Column(name = "end_tm")
    private LocalDate endTm;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private HouseHold houseHold;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartTm() {
        return startTm;
    }

    public MeasurePoint startTm(LocalDate startTm) {
        this.startTm = startTm;
        return this;
    }

    public void setStartTm(LocalDate startTm) {
        this.startTm = startTm;
    }

    public LocalDate getEndTm() {
        return endTm;
    }

    public MeasurePoint endTm(LocalDate endTm) {
        this.endTm = endTm;
        return this;
    }

    public void setEndTm(LocalDate endTm) {
        this.endTm = endTm;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public MeasurePoint ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getValue() {
        return value;
    }

    public MeasurePoint value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HouseHold getHouseHold() {
        return houseHold;
    }

    public MeasurePoint houseHold(HouseHold houseHold) {
        this.houseHold = houseHold;
        return this;
    }

    public void setHouseHold(HouseHold houseHold) {
        this.houseHold = houseHold;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MeasurePoint measurePoint = (MeasurePoint) o;
        if (measurePoint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), measurePoint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeasurePoint{" +
            "id=" + getId() +
            ", startTm='" + getStartTm() + "'" +
            ", endTm='" + getEndTm() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
