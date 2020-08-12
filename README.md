# GraphView
[![](https://jitpack.io/v/NsAveek/GraphView.svg)](https://jitpack.io/#NsAveek/GraphView)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Aesthetic%20GraphView-green.svg?style=flat )]( https://android-arsenal.com/details/1/8138 )
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## A simple graph library unlike other available libraries in the graph drawing arena. 

![Sample Graph Image](https://github.com/NsAveek/GraphView/blob/master/app/src/main/res/drawable/sample_graph.png)

## What's different?
* Take the full control over drawing the path. 
* Change the gradient color 
  * Start Color
  * End Color
* Change the circle color
* Change the circle radius

* Change the path color
* Change the line thickness
* On/Off Gridlines
  * Change the grid line color
* On/Off Graduations
  * Change the graduation text color
* Draw graph with different starting point
  * Draw graph from the left border (X0 - coordinate)
  * Draw graph with exact coordinates given
  * Draw graph from left border and stretch until the end of the screen

###### Supported on OS - JellyBean 4.1 and above 

# Installation

## Step 1. 
Add the jitpack repository to build.gradle in Project Level at the end of repositories
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
## Step 2. 
Add the dependency to build.gradle in app level

```
dependencies {
	 implementation 'com.github.NsAveek:GraphView:0.1.0'
}
```
# Usage

###### Declare coordinates to set. Since our base is along with X co-ordinates, assuming the X coordinates are sorted.
```
private val coordinates = arrayListOf<Pair<Float,Float>>()

```
###### Initialize coordinate data. Initiate GraphView from XML
```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCoordinates()

        val graphView = findViewById<com.aveek.aesthetic_graphview.GraphView>(R.id.graphView)
        graphView.setCoordinatePoints(coordinates)
        
}

private fun initCoordinates() {
        coordinates.add(Pair(0f,0f))
        coordinates.add(Pair(1f,2f))
        coordinates.add(Pair(3f,4f))
        coordinates.add(Pair(4f,3f))
        coordinates.add(Pair(5f,5f))
        coordinates.add(Pair(6f,4f))
        coordinates.add(Pair(7f,2f))
}

```
###### Sample XML 
```
<com.aveek.aesthetic_graphview.GraphView
        android:id="@+id/graphView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:circleColor="#000000"
        app:circleRadius="3"
        app:drawGraduations="true"
        app:drawGrids="true"
        app:gradientEndColor="@color/endColor"
        app:gradientStartColor="#fef08c"
        app:graduationColor="@color/colorPrimaryDark"
        app:graphType="START_AT_LEFT"
        app:gridColor="#fef08c"
        app:lineColor="#f8cb7a"
        app:pathWidth="2"
/>
```

## Change the gradient color [Default color is already set]
```
app:gradientStartColor="#fef08c"
app:gradientEndColor="@color/endColor"

```

## Change the circle color [Default color is already set]
```
app:circleColor="#000000"
```
## Change the circle radius [Default radius is already set]
```
app:circleRadius="3"
```

## Change the path color [Default color is already set]
```
app:lineColor="#f8cb7a"
```

## Change the line thickness [Default thickness is already set]
```
app:pathWidth="2"
```

## Change the Grid line drawing or not [Default is already set]
```
app:drawGrids="true"
```
## Change the Grid line color [Default color is already set]
```
app:gridColor="#fef08c"
```
## Change the Graduation text drawing or not [Default is already set]
```
app:drawGraduations="true"
```

## Change the Graduation text color [Default color is already set]
```
app:graduationColor="@color/colorPrimaryDark"
```
## Draw graph from the left border (X0 - coordinate)

```
app:graphType="START_AT_LEFT"
```
## Draw graph from the left border to end border (X0 - XN coordinate)

```
app:graphType="TOUCH_END"
```

## Draw graph to the exact coordinate (X0,Y0 coordinate)

```
app:graphType="EXACT"
```

# Future Task
* Make it scrollable
  * When there are a lots of data and user wants to see in a scrollview
  * When the TOUCH_END parameters called and user want to see in a scrollview
* Allow Multiple graphs to be drawn one top of another based on user's choice
* Add the graduation parameters (i.e: User may want to see Monthly data, so the graduation will be Jan, Feb, Mar etc. The graph will fit into the monthly block accordingly. 	 Currently user can see only X co-ordinates value) 

# LICENSE
```
Copyright 2020 Aveek

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
