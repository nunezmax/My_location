MyLocation app.
A simple android map program written in java.
Based on university assigment that I had to complete while learning mobile development, but modified after completion to include more functionality.
It relies on Osmdroid library and gets geographic data from OpenStreetMap Foundation.

Functionality:
Showing an accurate map.
Ability to zoom in and out using buttons on the bottom of interface.
Searching cities, places and coordinates (with limitations)
Places are marked after search and on tap display coordinates.
Can show your location on the map if you have GPS enabled and have given the program the permission to access it.

Limitations:
Requires initial internet connection to load map data, but saves the zones that have been loaded beforehand in cache so they can be accessed offline. The limitation is that offline part is inconsistent and should not be relied upon.
Search functionality may not work correctly on some devices.
On some devices it takes a very long time to pick the phone location and show it on the map.
No auto completion when using search.
No auto correction when using search. If there is a small typo in search query it will not search the place.
No routes.
All previous searches leave markers that disappear only when you restart the program. Also after restart you can not see which places you searched.
