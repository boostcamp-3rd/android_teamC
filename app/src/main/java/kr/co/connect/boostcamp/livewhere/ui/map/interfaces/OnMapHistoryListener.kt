package kr.co.connect.boostcamp.livewhere.ui.map.interfaces

import android.view.View
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.model.Place

interface OnMapHistoryListener {
    fun onClickMarkerPlace(place: Place)
    fun onClickMarkerHouse(house: MarkerInfo)
    fun onSaveFilterMarker(markerList: MutableList<Marker>)
    fun onRemoveFilterMarker()
    fun onSaveCircleOverlay(circleOverlay: CircleOverlay)
    fun onRemoveCircleOverlay()
    fun onRemoveInfoWindow()
    fun onSaveInfoWindow(infoWindow: InfoWindow)
    fun onLoadBuildingList(anyList: List<Any>, view: View)
    fun onMoveCameraPosition(latLng: LatLng, zoom: Double)
    fun onSearchHouseWithAddress(notCompletedAddress: String)
    fun onInitActivityStatus()
}
