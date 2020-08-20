package com.dwp.usersbycity.service;

import com.dwp.usersbycity.config.GeoProperties;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import org.springframework.stereotype.Service;

@Service
public class GeoService {

    private final GeoProperties geoProperties;

    public GeoService(GeoProperties geoProperties) {
        this.geoProperties = geoProperties;
    }

    public double getDistanceInMilesBetweenTwoPoints(double firstLat, double firstLon,
                                                     double secondLat, double secondLon) {
        GeodesicData geodesicData = Geodesic.WGS84.
                Inverse(firstLat, firstLon, secondLat, secondLon);
        double distanceInMetres = geodesicData.s12;
        return distanceInMetres * geoProperties.getMilesPerMetre();
    }
}
