import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { ViewChild } from '@angular/core';
import {} from '@types/googlemaps';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]
})
export class HomeComponent implements OnInit {

    account: Account;
    modalRef: NgbModalRef;

    //Map data
    @ViewChild('gmap') gmapElement: any;
    map: google.maps.Map;
    heatMap : google.maps.visualization.HeatmapLayer;

    //Calendar
    csLocale : any;

    //Selected timerange
    timeStart : Date;
    timeEnd : Date = new Date();

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.defineHeatMap();
        this.defineCalendarLocale();
    }

    defineHeatMap(){
        let mapProp = {
            center: new google.maps.LatLng(50.0566567,14.216043899999931),
            zoom: 17,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        this.map = new google.maps.Map(this.gmapElement.nativeElement, mapProp);

        //Add map to heatmap layer
        let options : google.maps.visualization.HeatmapLayerOptions = {
            data : this.getPoints()
        }
        options.opacity = 0.7;
        options.radius = 18;
        options.gradient = this.getGradient();

        this.heatMap = new google.maps.visualization.HeatmapLayer(options as google.maps.visualization.HeatmapLayerOptions);
        this.heatMap.setMap(this.map);
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    // Heatmap data: 500 Points
    getPoints() {
        return [
            new google.maps.LatLng(50.0566567,14.216043899999931),
            new google.maps.LatLng(50.0568599,14.215684600000031),
            new google.maps.LatLng(50.0562654,14.216780200000017),
            new google.maps.LatLng(50.057429,14.214701)
        ];
    }

    /**
     * Generates gradient for the map we show
     * @returns {string[]}
     */
    getGradient(){
        let gradient = [
            "rgba(102, 255, 0, 0)",
            "rgba(102, 255, 0, 1)",
            "rgba(147, 255, 0, 1)",
            "rgba(193, 255, 0, 1)",
            "rgba(238, 255, 0, 1)",
            "rgba(244, 227, 0, 1)",
            "rgba(249, 198, 0, 1)",
            "rgba(255, 170, 0, 1)",
            "rgba(255, 113, 0, 1)",
            "rgba(255, 57, 0, 1)",
            "rgba(255, 0, 0, 1)"
        ]

        return gradient;
    }

    private defineCalendarLocale() {
        this.csLocale = {
            firstDayOfWeek: 0,
            dayNames: ["Neděle", "Pondělí", "Úterý", "Středa", "Čtvrtek", "Pátek", "Sobota"],
            dayNamesShort: ["Ne", "Po", "Út", "St", "Čt", "Pá", "So"],
            dayNamesMin: ["Ne", "Po", "Út", "St", "Čt", "Pá", "So"],
            monthNames: [ "January","February","March","April","May","June","July","August","September","October","November","December" ],
            monthNamesShort: [ "Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ],
            today: 'Today',
            clear: 'Clear'
        };
    }
}
