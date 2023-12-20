package com.dicoding.bottomnavigationbar.ui.daftarRS

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.ars.DataRs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private val dataRs = DataRs.rs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return view
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

        for (rs in dataRs) {
            val location = LatLng(rs.lat.toDouble(), rs.lan.toDouble())
            googleMap.addMarker(MarkerOptions().position(location).title(rs.nama))
        }

        if (dataRs.isNotEmpty()) {
            // Move camera to the first location in the list
            val firstLocation = LatLng(dataRs[0].lat.toDouble(), dataRs[0].lan.toDouble())
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 15f))
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
