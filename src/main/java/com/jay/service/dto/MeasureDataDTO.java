package com.jay.service.dto;

import com.jay.domain.MeasureEnum;
import com.jay.domain.MeasurePoint;
import java.time.LocalDate;

/**
 * Holds information about measure point
 *
 * @author sojlu01
 */
public class MeasureDataDTO {

    private Long id;

    private LocalDate startTm;

    private LocalDate endTm;

    private Double latitude;

    private Double longitude;

    private MeasureEnum measureValue;

    public MeasureDataDTO(MeasurePoint point) {
        super();
        this.id = point.getId();
        //TODO - fix
        this.measureValue = MeasureEnum.WATERLESS;
        this.startTm = point.getStartTm();
        this.endTm = point.getEndTm();
        this.latitude = point.getHouseHold().getLatitude();
        this.longitude = point.getHouseHold().getLongitude();
    }

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public MeasureEnum getMeasureValue() {
        return measureValue;
    }

    public void setMeasureValue(MeasureEnum measureValue) {
        this.measureValue = measureValue;
    }
}
