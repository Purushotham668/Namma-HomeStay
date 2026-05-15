package com.nammahomestay.app.ui.components

object MapConstants {
    fun getMapHtml(latitude: Double, longitude: Double, showSearch: Boolean = true): String {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
                <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
                <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
                <style>
                    html, body { height: 100%; margin: 0; padding: 0; background: #f8f9fa; overflow: hidden; }
                    #map { height: 100%; width: 100%; z-index: 1; }
                    
                    /* Search Bar Styling - Narrowed to avoid corner buttons */
                    .search-container {
                        position: absolute;
                        top: 12px;
                        left: 65px;
                        right: 65px;
                        z-index: 2000;
                        display: ${if (showSearch) "block" else "none"};
                    }
                    .search-input {
                        width: 100%;
                        padding: 12px 15px;
                        border-radius: 10px;
                        border: 1px solid #ddd;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                        font-size: 14px;
                        font-family: sans-serif;
                        outline: none;
                        background: white;
                    }
                    .suggestions-list {
                        margin-top: 4px;
                        background: white;
                        border-radius: 10px;
                        box-shadow: 0 6px 15px rgba(0,0,0,0.15);
                        overflow: hidden;
                        display: none;
                        border: 1px solid #eee;
                    }
                    .suggestion-item {
                        padding: 10px 15px;
                        border-bottom: 1px solid #eee;
                        font-size: 13px;
                    }

                    /* Layer Switcher Styling */
                    .layers-btn {
                        position: absolute;
                        bottom: 150px;
                        right: 15px;
                        width: 42px;
                        height: 42px;
                        background: white;
                        border-radius: 50%;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        box-shadow: 0 4px 10px rgba(0,0,0,0.2);
                        z-index: 2000;
                        cursor: pointer;
                        display: ${if (showSearch) "flex" else "none"};
                    }
                    .layer-menu {
                        position: absolute;
                        bottom: 150px;
                        right: 65px;
                        background: white;
                        border-radius: 12px;
                        padding: 8px;
                        box-shadow: 0 4px 15px rgba(0,0,0,0.2);
                        display: none;
                        z-index: 2000;
                        flex-direction: column;
                        gap: 8px;
                    }
                    .layer-option {
                        padding: 8px 16px;
                        border-radius: 8px;
                        font-size: 13px;
                        font-weight: 600;
                        cursor: pointer;
                        white-space: nowrap;
                    }
                    .layer-option:active { background: #f0f0f0; }
                    .layer-option.active { background: #004D40; color: white; }

                    /* GPS Button Styling */
                    .gps-btn {
                        position: absolute;
                        bottom: 100px;
                        right: 15px;
                        width: 42px;
                        height: 42px;
                        background: white;
                        border-radius: 50%;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        box-shadow: 0 4px 10px rgba(0,0,0,0.2);
                        z-index: 2000;
                        display: ${if (showSearch) "flex" else "none"};
                    }

                    /* Uber-style Pin Styling */
                    .center-pin-container {
                        position: absolute;
                        top: 50%;
                        left: 50%;
                        transform: translate(-50%, -100%);
                        z-index: 1000;
                        pointer-events: none;
                        transition: transform 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
                    }
                    .center-pin-container.moving {
                        transform: translate(-50%, -140%);
                    }
                    .pin-head {
                        width: 30px;
                        height: 30px;
                        background: #000;
                        border: 2px solid #fff; /* High contrast for satellite */
                        border-radius: 50%;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        box-shadow: 0 4px 10px rgba(0,0,0,0.5);
                    }
                    .pin-head-inner {
                        width: 8px;
                        height: 8px;
                        background: #fff;
                        border-radius: 50%;
                    }
                    .pin-stem {
                        width: 3px;
                        height: 12px;
                        background: #000;
                        border-left: 1px solid #fff; /* Contrast edge */
                        border-right: 1px solid #fff; /* Contrast edge */
                        margin: 0 auto;
                    }
                    .ground-dot {
                        position: absolute;
                        bottom: -2px;
                        left: 50%;
                        transform: translateX(-50%);
                        width: 5px;
                        height: 5px;
                        background: #000;
                        border-radius: 50%;
                    }

                    /* Save Button Styling */
                    .save-btn {
                        position: absolute;
                        bottom: 24px;
                        left: 50%;
                        transform: translateX(-50%);
                        background: #004D40; 
                        color: white;
                        border: none;
                        padding: 12px 24px;
                        border-radius: 30px;
                        font-family: sans-serif;
                        font-weight: 700;
                        box-shadow: 0 5px 15px rgba(0,0,0,0.3);
                        z-index: 2000;
                        display: flex;
                        align-items: center;
                        gap: 8px;
                        font-size: 14px;
                    }
                    .save-btn.confirmed { background: #455A64; }
                    .leaflet-control-attribution { font-size: 7px !important; }
                </style>
            </head>
            <body>
                <div id="map"></div>
                <div class="search-container">
                    <input type="text" id="search-input" class="search-input" placeholder="Search for places..." autocomplete="off">
                    <div id="suggestions" class="suggestions-list"></div>
                </div>
                <div class="layers-btn" onclick="toggleLayerMenu()">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#000" stroke-width="2"><path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5"></path></svg>
                </div>
                <div id="layer-menu" class="layer-menu">
                    <div class="layer-option active" id="opt-satellite" onclick="setLayer('satellite')">Satellite View</div>
                    <div class="layer-option" id="opt-street" onclick="setLayer('street')">Street View</div>
                </div>

                <div class="gps-btn" onclick="locateMe()">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#000" stroke-width="2"><polygon points="3 11 22 2 13 21 11 13 3 11"></polygon></svg>
                </div>
                <div id="pin" class="center-pin-container">
                    <div class="pin-head"><div class="pin-head-inner"></div></div>
                    <div class="pin-stem"></div>
                    <div class="ground-dot"></div>
                </div>
                <button id="save-btn" class="save-btn" onclick="saveLocation()">
                    <span id="btn-icon"><svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3"><polyline points="20 6 9 17 4 12"></polyline></svg></span>
                    <span id="btn-text">Confirm Location</span>
                </button>
                <script>
                    var map;
                    var streetLayer, satelliteLayer, labelLayer;
                    var pinElement = document.getElementById('pin');
                    var saveBtn = document.getElementById('save-btn');
                    var btnText = document.getElementById('btn-text');
                    var btnIcon = document.getElementById('btn-icon');
                    var searchInput = document.getElementById('search-input');
                    var suggestionsBox = document.getElementById('suggestions');
                    var layerMenu = document.getElementById('layer-menu');
                    var isConfirmed = false;

                    function initMap() {
                        var lat = $latitude;
                        var lng = $longitude;
                        if (lat === 0 && lng === 0) { lat = 12.9716; lng = 77.5946; }
                        else { setConfirmed(true); }

                        map = L.map('map', { zoomControl: false }).setView([lat, lng], 17);
                        
                        satelliteLayer = L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}');
                        labelLayer = L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager_only_labels/{z}/{x}/{y}{r}.png');
                        streetLayer = L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png');

                        satelliteLayer.addTo(map);
                        labelLayer.addTo(map);

                        map.on('movestart', function() { 
                            pinElement.classList.add('moving'); 
                            if (isConfirmed) setConfirmed(false); 
                        });
                        map.on('moveend', function() { 
                            pinElement.classList.remove('moving'); 
                        });

                        setTimeout(function() { map.invalidateSize(); }, 500);

                        searchInput.oninput = function() {
                            var query = searchInput.value;
                            if (query.length < 3) { suggestionsBox.style.display = 'none'; return; }
                            fetch('https://photon.komoot.io/api/?q=' + encodeURIComponent(query) + '&limit=5')
                                .then(r => r.json())
                                .then(data => {
                                    suggestionsBox.innerHTML = '';
                                    if (data.features && data.features.length > 0) {
                                        data.features.forEach(f => {
                                            var div = document.createElement('div');
                                            div.className = 'suggestion-item';
                                            div.innerText = f.properties.name + (f.properties.city ? ', ' + f.properties.city : '');
                                            div.onclick = function() {
                                                map.setView([f.geometry.coordinates[1], f.geometry.coordinates[0]], 16);
                                                suggestionsBox.style.display = 'none';
                                                searchInput.value = div.innerText;
                                            };
                                            suggestionsBox.appendChild(div);
                                        });
                                        suggestionsBox.style.display = 'block';
                                    }
                                });
                        };
                    }

                    function toggleLayerMenu() {
                        layerMenu.style.display = layerMenu.style.display === 'flex' ? 'none' : 'flex';
                    }

                    function setLayer(type) {
                        if (type === 'satellite') {
                            streetLayer.remove();
                            satelliteLayer.addTo(map);
                            labelLayer.addTo(map);
                            document.getElementById('opt-satellite').classList.add('active');
                            document.getElementById('opt-street').classList.remove('active');
                        } else {
                            satelliteLayer.remove();
                            labelLayer.remove();
                            streetLayer.addTo(map);
                            document.getElementById('opt-street').classList.add('active');
                            document.getElementById('opt-satellite').classList.remove('active');
                        }
                        layerMenu.style.display = 'none';
                    }

                    function locateMe() { if(map) map.locate({setView: true, maxZoom: 16}); }
                    function setConfirmed(confirmed) {
                        isConfirmed = confirmed;
                        if (!saveBtn) return;
                        if (confirmed) {
                            saveBtn.classList.add('confirmed'); btnText.innerText = "Edit Location";
                            btnIcon.innerHTML = '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4L18.5 2.5z"></path></svg>';
                        } else {
                            saveBtn.classList.remove('confirmed'); btnText.innerText = "Confirm Location";
                            btnIcon.innerHTML = '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>';
                        }
                    }
                    function saveLocation() {
                        if (isConfirmed || !map) return;
                        var center = map.getCenter();
                        if(typeof Android !== 'undefined') { Android.onMapClick(center.lat, center.lng); }
                        setConfirmed(true);
                    }
                    function updateMarker(lat, lng) { if (map) { map.panTo([lat, lng]); } }
                    initMap();
                </script>
            </body>
            </html>
        """.trimIndent()
    }
}
